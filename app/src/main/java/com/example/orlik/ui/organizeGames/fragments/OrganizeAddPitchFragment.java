package com.example.orlik.ui.organizeGames.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orlik.R;
import com.example.orlik.data.model.Pitch;
import com.example.orlik.ui.organizeGames.OrganizeViewModel;
import com.example.orlik.ui.organizeGames.OrganizeViewModelFactory;

import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.compass.CompassOverlay;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrganizeAddPitchFragment extends Fragment {
    private final static String TAG = "OrganizeAddPitchTAG";
    private OrganizeViewModel organizeViewModel;
    private Spinner pitchTypeSpinner;
    private TextView addressTextView;
    private Button addPitchButton;
    private MapView mapView;
    private final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;

    public OrganizeAddPitchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        organizeViewModel = new ViewModelProvider(requireActivity(), new OrganizeViewModelFactory())
                .get(OrganizeViewModel.class);
        final Context context = requireContext();
        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context));
        organizeViewModel.getAddPitchResult().setValue(null); //prevent to automatically launch observer after add new pitch and open this fragment again
        organizeViewModel.getAddPitchResult().observe(this, new Observer<Pitch>() {
            @Override
            public void onChanged(Pitch pitch) {
                if(pitch!=null){
                    Toast.makeText(context,"Dodano Boisko", Toast.LENGTH_LONG).show();
                   getParentFragmentManager().popBackStack();
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_organize_add_pitch, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        pitchTypeSpinner=(Spinner) view.findViewById(R.id.organize_addPitch_type_spinner);
        organizeViewModel.setOrganizeSpinner(pitchTypeSpinner,R.array.pitch_choose_array);
        pitchTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
              organizeViewModel.setPitchType(adapterView.getItemAtPosition(i).toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        addressTextView = (TextView) view.findViewById(R.id.organize_addPitch_address_TextView);
        organizeViewModel.setPitchAddressAndLocalization(organizeViewModel.getLocation());
        addressTextView.setText("Adres: "+organizeViewModel.getPitchAddress());

        mapView = (MapView) view.findViewById(R.id.organize_pitch_mapView);
        setMapView(mapView);

        addPitchButton = (Button) view.findViewById(R.id.organize_addPitch_addPitchButton);
        addPitchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                organizeViewModel.addPitch();
            }
        });

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (int i = 0; i < grantResults.length; i++) {
            permissionsToRequest.add(permissions[i]);
        }
        if (permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(
                    requireActivity(),
                    permissionsToRequest.toArray(new String[0]),
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    private void requestPermissionsIfNecessary(String[] permissions) {
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(requireContext(), permission)
                    != PackageManager.PERMISSION_GRANTED) {
                permissionsToRequest.add(permission);
            }
        }
        if (permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(
                    requireActivity(),
                    permissionsToRequest.toArray(new String[0]),
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }


    private void setMapView(final MapView mapView){
        final Context context = requireContext();
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

        final GeoPoint point = new GeoPoint(organizeViewModel.getLatitude(), organizeViewModel.getLongitude());
        final Marker startMarker = new Marker(mapView);
        startMarker.setPosition(point);
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER);
        mapView.getOverlays().add(startMarker);

        mapView.getController().setCenter(point);

        final MapEventsReceiver mReceive = new MapEventsReceiver(){
            Marker bufor = new Marker(mapView);
            String address;
            @Override
            public boolean singleTapConfirmedHelper(GeoPoint p) {
                address=organizeViewModel.getAddress(p.getLatitude(), p.getLongitude());
                addressTextView.setText("Adres: "+address);
                organizeViewModel.setPitchAddressAndLocalization(address, p.getLatitude(), p.getLongitude());
                mapView.getOverlays().clear();
                bufor.setPosition(p);
                bufor.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                bufor.setTitle("Wybrana lokalizacja");
                mapView.getOverlays().add(bufor);
                mapView.invalidate();
                mapView.getOverlays().add(new MapEventsOverlay(this));
                return false;
            }
            @Override
            public boolean longPressHelper(GeoPoint p) {
                return false;
            }
        };
        mapView.getOverlays().add(new MapEventsOverlay(mReceive));
    }
}