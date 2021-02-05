package com.example.orlik.ui.pitch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orlik.R;
import com.example.orlik.data.model.Pitch;
import com.example.orlik.data.model.PitchConfirmations;
import com.example.orlik.ui.Basic.BasicActivity;
import com.example.orlik.ui.login.LoginActivity;
import com.example.orlik.ui.match.MatchActivity;
import com.example.orlik.ui.organizeGames.OrganizeActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.compass.CompassOverlay;

import java.util.ArrayList;
import java.util.Map;

public class PitchActivity extends BasicActivity {
    private static final String TAG="PitchActivityTAG";
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE=1;
    private PitchViewModel pitchViewModel;
    private BottomNavigationView bottomNavigationView;
    private Button pitchButton, pitchAdminButton, pitchRemoveAdminButton;
    private TextView typeTextView, addressTextView, rateTextView;
    private MapView pitchMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pitch);
        pitchViewModel = new ViewModelProvider(this, new PitchViewModelFactory()).get(PitchViewModel.class);
        pitchViewModel.setSession(this);
        //for mapView set user Agent
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));

        if(pitchViewModel.getPitch()==null){
            Pitch pitch = (Pitch) getIntent().getSerializableExtra("pitch");
            Log.v(TAG, pitch.toString());
            pitchViewModel.setPitch(pitch);
        }

        bottomNavigationView = findViewById(R.id.main_toolbar);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);// get handlers from parent app
        bottomNavigationView.setOnNavigationItemReselectedListener(this);
        checkAdmin(bottomNavigationView);

        pitchButton=(Button) findViewById(R.id.pitch_button);
        if(!pitchViewModel.getPitch().isValid()){
            pitchButton.setText("Potwierdz Lokalizacje");
            pitchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pitchViewModel.confirmPitch(getApplicationContext());
                }
            });
        }else{
            pitchButton.setText("Zorganizuj mecz");
            pitchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), OrganizeActivity.class);
                    intent.putExtra("pitch",pitchViewModel.getPitch());
                    view.getContext().startActivity(intent);
                }
            });
        }

        typeTextView = (TextView) findViewById(R.id.pitch_type_textView);
        typeTextView.setText(pitchViewModel.getPitch().getType());

        rateTextView  =(TextView) findViewById(R.id.pitch_rate_textView);
        rateTextView.setText("Ocena: "+pitchViewModel.getPitch().getRating());

        addressTextView = (TextView) findViewById(R.id.pitch_address_textView);
        addressTextView.setText(pitchViewModel.getPitch().getAdress());

        pitchAdminButton = (Button) findViewById(R.id.pitch_admin_button);
        pitchRemoveAdminButton = (Button) findViewById(R.id.pitch_adminRemove_button);
        if(pitchViewModel.isAdmin()){
            pitchRemoveAdminButton.setVisibility(View.VISIBLE);

            pitchRemoveAdminButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pitchViewModel.deletePitch();
                }
            });
            if(!pitchViewModel.getPitch().isValid()){
                pitchAdminButton.setVisibility(View.VISIBLE);
                pitchAdminButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        pitchViewModel.confirmPitchAsAdmin();
                    }
                });
            }else{
                pitchAdminButton.setVisibility(View.GONE);
            }
        }else{
            pitchRemoveAdminButton.setVisibility(View.GONE);
            pitchAdminButton.setVisibility(View.GONE);
        }

        //TODO widok meczu wyrównanie widoku jesli w team 2 jest wiecej graczy niz w team 1
        pitchMap = (MapView) findViewById(R.id.pitch_mapView);
        setMap(pitchMap);

        pitchViewModel.getConfirmPitchResult().observe(this, new Observer<PitchConfirmations>() {
            @Override
            public void onChanged(PitchConfirmations pitchConfirmations) {
                if(pitchConfirmations!=null){
                    if(pitchConfirmations.getPitchId().equals(pitchViewModel.getPitch().getPitchId())){
                        Toast ToastMessage = Toast.makeText(getApplicationContext(),"Potwierdzono boisko",Toast.LENGTH_SHORT);
                        View toastView = ToastMessage.getView();
                        toastView.setBackgroundColor(getResources().getColor(R.color.toastBackground));
                        ToastMessage.show();
                        Intent intent = new Intent(getApplicationContext(), OrganizeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }
            }
        });

        pitchViewModel.getDeletePitchResult().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    Toast ToastMessage = Toast.makeText(getApplicationContext(),"Boisko Usuniete",Toast.LENGTH_SHORT);
                    View toastView = ToastMessage.getView();
                    toastView.setBackgroundColor(getResources().getColor(R.color.toastBackground));
                    ToastMessage.show();
                    Intent intent = new Intent(getApplicationContext(), OrganizeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        });

        pitchViewModel.getValidPitchResult().observe(this, new Observer<Pitch>() {
            @Override
            public void onChanged(Pitch pitch) {
                if(pitch.isValid()){
                    Toast ToastMessage = Toast.makeText(getApplicationContext(),"Boisko zatwierdzone przez Administratora",Toast.LENGTH_SHORT);
                    View toastView = ToastMessage.getView();
                    toastView.setBackgroundColor(getResources().getColor(R.color.toastBackground));
                    ToastMessage.show();
                    Intent intent = new Intent(getApplicationContext(), OrganizeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        });
    }

    private void setMap(MapView mapView){

        final Context context = this;
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.getController().setZoom(18.0);
        requestPermissionsIfNecessary(new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.INTERNET
        });
        mapView.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.ALWAYS);
        mapView.setMultiTouchControls(true);

        CompassOverlay compassOverlay = new CompassOverlay(context, mapView);
        compassOverlay.enableCompass();
        mapView.getOverlays().add(compassOverlay);

        final DisplayMetrics dm = context.getResources().getDisplayMetrics();

        ScaleBarOverlay mScaleBarOverlay = new ScaleBarOverlay(mapView);
        mScaleBarOverlay.setCentred(true);
        mScaleBarOverlay.setScaleBarOffset(dm.widthPixels / 2, 10);
        mapView.getOverlays().add(mScaleBarOverlay);
        String location = pitchViewModel.getPitch().getLocation();
        String[] coordinates = location.split(":");
        final GeoPoint point = new GeoPoint(Double.valueOf(coordinates[0]), Double.valueOf(coordinates[1]));
        final Marker startMarker = new Marker(mapView);
        startMarker.setPosition(point);
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER);
        mapView.getOverlays().add(startMarker);

        mapView.getController().setCenter(point);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (int i = 0; i < grantResults.length; i++) {
            permissionsToRequest.add(permissions[i]);
        }
        if (permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(
                    this,
                    permissionsToRequest.toArray(new String[0]),
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    private void requestPermissionsIfNecessary(String[] permissions) {
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                permissionsToRequest.add(permission);
            }
        }
        if (permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(
                    this,
                    permissionsToRequest.toArray(new String[0]),
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }
}