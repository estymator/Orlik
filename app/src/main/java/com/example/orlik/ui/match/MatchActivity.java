package com.example.orlik.ui.match;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentContainerView;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orlik.R;
import com.example.orlik.data.model.Pitch;
import com.example.orlik.data.model.PlayerStatistics;
import com.example.orlik.data.model.PlayersGame;
import com.example.orlik.data.model.Session;
import com.example.orlik.data.model.dto.GameDTO;
import com.example.orlik.ui.Basic.BasicActivity;
import com.example.orlik.ui.match.dialogs.SignUpDialog;
import com.example.orlik.ui.match.fragments.MatchResultFragment;
import com.example.orlik.ui.match.fragments.MatchStatsFragment;
import com.example.orlik.ui.organizeGames.fragments.OrganizeAddGameFragment;
import com.example.orlik.ui.organizeGames.fragments.OrganizeAddPitchFragment;
import com.example.orlik.ui.organizeGames.fragments.OrganizeInvalidPitchListFragment;
import com.example.orlik.ui.organizeGames.fragments.OrganizePitchListFragment;
import com.example.orlik.ui.pitch.PitchActivity;
import com.example.orlik.ui.pitch.PitchViewModel;
import com.example.orlik.ui.pitch.PitchViewModelFactory;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.compass.CompassOverlay;

import java.util.ArrayList;
import java.util.List;

public class MatchActivity extends BasicActivity {
    private static final String TAG= "MatchActivityTAG";
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE=1;
    private MatchViewModel matchViewModel;
    private MapView mapView;
    private Button signUpButton, organiserDeleteButton, organiserAddResultButton, playerAddStatsButton;
    private TextView addressTextView, descriptionTextView, visibilityTextView, pitchRateTextView, membersTextView, dateTextView, minPlayersTextView, durationTextView,
            organizerTextView, statusTextView, pitchTypeTextView, resultTextView, team1TextView, team2TextView, goalsTextView, assistsTextView;

    private FragmentContainerView fragmentContainerView;
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        matchViewModel = new ViewModelProvider(this, new MatchViewModelProvider()).get(MatchViewModel.class);
        //for mapView set user Agent
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        if(matchViewModel.getGame()==null){
            GameDTO game = (GameDTO) getIntent().getSerializableExtra("match");
            Log.v(TAG, game.toString());
            matchViewModel.setGame(game);
            Session sesja = new Session(this);
            matchViewModel.setLoggedInUser(sesja.getUser());
        }

        /**
         * function fill in list view when gets list of players from server
         */
        matchViewModel.getGetPlayersOfGameResult().observe(this, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                ArrayList<String> parsedValuesTeam1 = new ArrayList<>(), parsedValuesTeam2 = new ArrayList<>();
                parsedValuesTeam1.add("Zespół 1");
                parsedValuesTeam2.add("Zespół 2");
                int licznik1=0;
                int licznik2=0;
                for(int i=0;i<strings.size();i++){
                    String bufor = strings.get(i);
                    String[] array = bufor.split(":");
                    if(matchViewModel.getLoggedInUser().getLogin().equals(array[0])){
                        matchViewModel.getUserSignedUp().setValue(true);
                    }
                    int team = Integer.valueOf(array[3]);
                    if(team==1){
                        licznik1++;
                        Log.v(TAG,"Dodaje do teamu 1");
                        parsedValuesTeam1.add(licznik1+"-"+array[1]+" "+array[2]); // TODO make it more safe
                    }else if(team==2){
                        licznik2++;
                        Log.v(TAG,"Dodaje do teamu 2");
                        parsedValuesTeam2.add(licznik2+"-"+array[1]+" "+array[2]); // TODO make it more safe
                    }
                    matchViewModel.setTeam1Size(licznik1);
                    matchViewModel.setTeam2Size(licznik2);
                }
                String team1String = "";
                for(int i=0;i<parsedValuesTeam1.size();i++)
                {
                    team1String+=("\n "+parsedValuesTeam1.get(i));
                }

                String team2String = "";
                for(int i=0;i<parsedValuesTeam2.size();i++)
                {
                    team2String+=("\n "+parsedValuesTeam2.get(i));
                }

                team1TextView.setText(team1String);
                team2TextView.setText(team2String);

            }
        });

        bottomNavigationView = findViewById(R.id.main_toolbar);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);// get handlers from parent app
        bottomNavigationView.setOnNavigationItemReselectedListener(this);
        checkAdmin(bottomNavigationView);

        addressTextView = (TextView) findViewById(R.id.match_address_textView);

        descriptionTextView  = (TextView) findViewById(R.id.match_description_textView);

        visibilityTextView = (TextView) findViewById(R.id.match_visibility_textView);

        pitchRateTextView = (TextView) findViewById(R.id.match_pitchRate_textView);

        membersTextView = (TextView)  findViewById(R.id.match_members_TextView);

        dateTextView = (TextView) findViewById(R.id.match_date_textView);

        minPlayersTextView = (TextView) findViewById(R.id.match_minPlayers_TextView);

        durationTextView = (TextView) findViewById(R.id.match_duration_textView);

        organizerTextView = (TextView) findViewById(R.id.match_organizer_textView);

        statusTextView = (TextView) findViewById(R.id.match_status_textView);

        pitchTypeTextView = (TextView) findViewById(R.id.match_pitchType_textView);

        team1TextView = findViewById(R.id.match_team1_textView);
        team2TextView = findViewById(R.id.match_team2_textView);

        organiserDeleteButton = (Button) findViewById(R.id.match_organiserDelete_button);


        organiserAddResultButton = (Button) findViewById(R.id.match_organiser_addResult_button);

        playerAddStatsButton = (Button) findViewById(R.id.match_player_addStats_button);

        resultTextView = (TextView) findViewById(R.id.match_result_textView);
        signUpButton = (Button) findViewById(R.id.match_signUp_button);

        resultTextView = findViewById(R.id.match_result_textView);
        assistsTextView=findViewById(R.id.match_assists_textView);
        goalsTextView = findViewById(R.id.match_goals_textView);

        matchViewModel.getStatistics().observe(this, new Observer<PlayerStatistics>() {
            @Override
            public void onChanged(PlayerStatistics playerStatistics) {
                if(playerStatistics!=null){
                    if(playerStatistics.getAssists()!=null&&playerStatistics.getGoals()!=null){
                        assistsTextView.setText("Asysty: "+playerStatistics.getAssists());
                        assistsTextView.setVisibility(View.VISIBLE);
                        goalsTextView.setText("Gole: "+playerStatistics.getGoals());
                        goalsTextView.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        fragmentContainerView = findViewById(R.id.match_fragmentCointainerView);

        loadValues();
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(matchViewModel.getUserSignedUp().getValue()){
                    matchViewModel.optOut();
                }else{
                    SignUpDialog signUpDialog = new SignUpDialog();
                    signUpDialog.show(getSupportFragmentManager(),"signUpDialog");
                }
            }
        });

        organiserDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                matchViewModel.deleteGame();
            }
        });

        organiserAddResultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                matchViewModel.addResultFragment();
                organiserAddResultButton.setEnabled(false);
            }
        });

        playerAddStatsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                matchViewModel.addStatsFragment();
            }
        });

        mapView = findViewById(R.id.match_mapView);
        setMap(mapView);

        matchViewModel.getUserSignedUp().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    signUpButton.setText("Wypisz się");
                }else{
                    signUpButton.setText("Zapisz się");
                }
            }
        });

        matchViewModel.getOptOutPlayerResult().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean!=null){
                    if(aBoolean){
                        Toast ToastMessage = Toast.makeText(getApplicationContext(),"Wypisałeś się",Toast.LENGTH_SHORT);
                        View toastView = ToastMessage.getView();
                        toastView.setBackgroundColor(getResources().getColor(R.color.toastBackground));
                        ToastMessage.show();
                        matchViewModel.getUserSignedUp().setValue(false);
                        loadValues();
                    }else{
                        Toast ToastMessage = Toast.makeText(getApplicationContext(),"Błąd",Toast.LENGTH_SHORT);
                        View toastView = ToastMessage.getView();
                        toastView.setBackgroundColor(getResources().getColor(R.color.toastFailedBackground));
                        ToastMessage.show();
                    }
                }
            }
        });

        matchViewModel.getSignUpPlayerResult().observe(this, new Observer<PlayersGame>() {
            @Override
            public void onChanged(PlayersGame playersGame) {
                if(playersGame!=null){
                    if(playersGame.getGameId()==matchViewModel.getGame().getId()){
                        Toast ToastMessage = Toast.makeText(getApplicationContext(),"Zapisałeś się",Toast.LENGTH_SHORT);
                        View toastView = ToastMessage.getView();
                        toastView.setBackgroundColor(getResources().getColor(R.color.toastBackground));
                        ToastMessage.show();
                        matchViewModel.getUserSignedUp().setValue(true);
                        loadValues();
                    }else{
                        Toast ToastMessage = Toast.makeText(getApplicationContext(),"Błąd",Toast.LENGTH_SHORT);
                        View toastView = ToastMessage.getView();
                        toastView.setBackgroundColor(getResources().getColor(R.color.toastFailedBackground));
                        ToastMessage.show();
                    }
                }
            }
        });

        matchViewModel.getErrorFlag().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s!=null){
                    Toast ToastMessage = Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT);
                    View toastView = ToastMessage.getView();
                    toastView.setBackgroundColor(getResources().getColor(R.color.toastFailedBackground));
                    ToastMessage.show();
                }
            }
        });

        matchViewModel.getDeleteGameResult().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean!=null){
                    if(aBoolean){
                        Toast ToastMessage = Toast.makeText(getApplicationContext(),"Mecz usunięty",Toast.LENGTH_SHORT);
                        View toastView = ToastMessage.getView();
                        toastView.setBackgroundColor(getResources().getColor(R.color.toastBackground));
                        ToastMessage.show();
                        finish();
                    }else{
                        Toast ToastMessage = Toast.makeText(getApplicationContext(),"Błąd Danych",Toast.LENGTH_SHORT);
                        View toastView = ToastMessage.getView();
                        toastView.setBackgroundColor(getResources().getColor(R.color.toastFailedBackground));
                        ToastMessage.show();
                    }
                }
            }
        });

        matchViewModel.getFragmentNavigator().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                switch (s){
                    case "result":
                        getSupportFragmentManager().beginTransaction()
                                .setReorderingAllowed(true)
                                .addToBackStack(null)
                                .replace(R.id.match_fragmentCointainerView, MatchResultFragment.class , null)
                                .commit();
                        break;
                    case "stats":
                        getSupportFragmentManager().beginTransaction()
                                .setReorderingAllowed(true)
                                .addToBackStack(null)
                                .replace(R.id.match_fragmentCointainerView, MatchStatsFragment.class , null)
                                .commit();
                        break;
                }
            }
        });

        matchViewModel.getAddResult().observe(this, new Observer<GameDTO>() {
            @Override
            public void onChanged(GameDTO gameDTO) {
                if(gameDTO!=null){
                    if(gameDTO.getStatus().equals("checked"))
                    {
                        Toast ToastMessage = Toast.makeText(getApplicationContext(),"Wynik dodany",Toast.LENGTH_SHORT);
                        View toastView = ToastMessage.getView();
                        toastView.setBackgroundColor(getResources().getColor(R.color.toastBackground));
                        ToastMessage.show();
                        matchViewModel.getGame().setStatus("checked");
                        matchViewModel.getGame().setResult(gameDTO.getResult());
                        loadValues();
                    }
                }
            }
        });

        matchViewModel.getAddStatsResult().observe(this, new Observer<PlayerStatistics>() {
            @Override
            public void onChanged(PlayerStatistics playerStatistics) {
                if(playerStatistics!=null){
                    Intent intent = new Intent(getApplicationContext(), MatchActivity.class);
                    intent.putExtra("match", matchViewModel.getGame());
                    intent.putExtra("stats",playerStatistics);
                    startActivity(intent);
                }
            }
        });

        matchViewModel.getGetStatsResult().observe(this, new Observer<PlayerStatistics>() {
            @Override
            public void onChanged(PlayerStatistics playerStatistics) {
                if(playerStatistics!=null){
                    goalsTextView.setText("Gole Strzelone: "+playerStatistics.getGoals());
                    goalsTextView.setVisibility(View.VISIBLE);
                    assistsTextView.setText("Asysty: "+playerStatistics.getAssists());
                    assistsTextView.setVisibility(View.VISIBLE);
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
        String location = matchViewModel.getGame().getLocation();
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

    private void loadValues(){
        matchViewModel.getPlayers();

        organizerTextView.setText("Organizator: "+matchViewModel.getGame().getOrganiserName());
        pitchTypeTextView.setText(matchViewModel.getGame().getPitchType());
        String status="";
        if(matchViewModel.getGame().getStatus().equals("checked")){
            status="Zakończony";
        }else if(matchViewModel.getGame().getStatus().equals("finished")){
            status="Rozegrany, Brak wyniku";
        }else if(matchViewModel.getGame().getStatus().equals("await")){
            status="Zbieranie graczy";
        }else if(matchViewModel.getGame().getStatus().equals("canceled")){
            status="odwołany";
        }else{
            status="Nieokreślony";
        }
        statusTextView.setText("Status: "+status);
        durationTextView.setText("Czas gry: "+matchViewModel.getGame().getDuration());
        minPlayersTextView.setText("Przedział graczy: "+matchViewModel.getGame().getMinPlayersNumber()+"-"+matchViewModel.getGame().getMaxPlayersNumber());
        dateTextView.setText(matchViewModel.getGame().getSchedule());
        String members=matchViewModel.getGame().getPlayers()+"/"+matchViewModel.getGame().getMaxPlayersNumber();
        membersTextView.setText("Zapisani "+members);
        pitchRateTextView.setText("Ocena boiska: "+matchViewModel.getGame().getPitchRating());
        String visibility = (matchViewModel.getGame().getVisibility()==1) ? "publiczny" : "prywatny";
        visibilityTextView.setText("Mecz "+visibility);
        addressTextView.setText(matchViewModel.getGame().getAddress());
        descriptionTextView.setText(matchViewModel.getGame().getDescription());

        if(matchViewModel.getLoggedInUser().getLogin().equals(matchViewModel.getGame().getOrganizerLogin())){
            organiserDeleteButton.setVisibility(View.VISIBLE);
            if(matchViewModel.getGame().getStatus().equals("finished")){
                organiserDeleteButton.setVisibility(View.GONE);
                organiserAddResultButton.setVisibility(View.VISIBLE);
                signUpButton.setVisibility(View.GONE);
            }else{
                organiserAddResultButton.setVisibility(View.GONE);
            }
        }else{
            organiserDeleteButton.setVisibility(View.GONE);
        }
        //TODO dodanie logiki do zapisu wyników, wygrane mecze użytkownika i wszystkie mecze uzytkownika
        //TODO dodanie komunikatu o braku meczy do zatwierdzenia w statystykach
        if(matchViewModel.getGame().getStatus().equals("checked")){
            playerAddStatsButton.setVisibility(View.VISIBLE);
            resultTextView.setText("Wynik: "+matchViewModel.getGame().getResult());
            resultTextView.setVisibility(View.VISIBLE);
            signUpButton.setVisibility(View.GONE);
            organiserDeleteButton.setVisibility(View.GONE);
        }else {
            playerAddStatsButton.setVisibility(View.GONE);
        }

        if(matchViewModel.getGame().getStatus().equals("completed")){
            resultTextView.setText("Wynik: "+matchViewModel.getGame().getResult());
            resultTextView.setVisibility(View.VISIBLE);
            signUpButton.setVisibility(View.GONE);
            organiserDeleteButton.setVisibility(View.GONE);
            matchViewModel.getStats();
        }
    }
}