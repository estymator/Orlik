package com.example.orlik.ui.match;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.orlik.Network.GameRequests;
import com.example.orlik.Network.PlayersGameRequests;
import com.example.orlik.data.model.PlayersGame;
import com.example.orlik.data.model.User;
import com.example.orlik.data.model.dto.GameDTO;
import java.util.ArrayList;

public class MatchViewModel extends ViewModel {
    private final static String TAG = "MatchViewModelTAG";

    private GameDTO game;
    private User loggedInUser;
    private PlayersGameRequests playersGameRequests = new PlayersGameRequests();
    private GameRequests gameRequests = new GameRequests();
    private ArrayList<String> playersName= new ArrayList<>();
    private MutableLiveData<ArrayList<String>> getPlayersOfGameResult = new MutableLiveData<>();
    private MutableLiveData<Boolean> optOutPlayerResult = new MutableLiveData<>();
    private MutableLiveData<PlayersGame> signUpPlayerResult = new MutableLiveData<>();
    private MutableLiveData<Boolean> deleteGameResult = new MutableLiveData<>();
    private int team1Size=0, team2Size=0;
    private MutableLiveData<Boolean> userSignedUp = new MutableLiveData<>(false);
    private MutableLiveData<String> errorFlag = new MutableLiveData<>();
    private MutableLiveData<String> fragmentNavigator = new MutableLiveData<>("");
    private MutableLiveData<GameDTO> addResult = new MutableLiveData<>();

    public GameDTO getGame() {
        return game;
    }

    public MutableLiveData<ArrayList<String>> getGetPlayersOfGameResult() {
        return getPlayersOfGameResult;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public MutableLiveData<String> getErrorFlag() {
        return errorFlag;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public int getTeam1Size() {
        return team1Size;
    }

    public void setTeam1Size(int team1Size) {
        this.team1Size = team1Size;
    }

    public int getTeam2Size() {
        return team2Size;
    }

    public MutableLiveData<Boolean> getUserSignedUp() {
        return userSignedUp;
    }

    public MutableLiveData<Boolean> getOptOutPlayerResult() {
        return optOutPlayerResult;
    }

    public MutableLiveData<PlayersGame> getSignUpPlayerResult() {
        return signUpPlayerResult;
    }

    public void setTeam2Size(int team2Size) {
        this.team2Size = team2Size;
    }

    public void setGame(GameDTO game) {
        this.game = game;
    }

    public void getPlayers(){
        playersGameRequests.loadPlayers(game.getId(), getPlayersOfGameResult);
    }

    public void signUpPlayer(int team){
        if(team==1){
            if(team1Size<(game.getMaxPlayersNumber()/2)){
                this.game.setPlayers(game.getPlayers()+1);
                playersGameRequests.sigUpPlayer(game.getId(), loggedInUser.getLogin(), team, signUpPlayerResult);
            }else{
                this.errorFlag.setValue("Brak miejsca w zespole");
            }
        }else if(team==2) {
            if(team2Size<(game.getMaxPlayersNumber()/2)){
                this.game.setPlayers(game.getPlayers()+1);
                playersGameRequests.sigUpPlayer(game.getId(), loggedInUser.getLogin(), team, signUpPlayerResult);
            }else{
                this.errorFlag.setValue("Brak miejsca w zespole");
            }
        }
    }

    public void optOut(){
        this.game.setPlayers(game.getPlayers()-1);
        playersGameRequests.optOutPlayers(game.getId(), loggedInUser.getLogin(), optOutPlayerResult);
    }

    public MutableLiveData<Boolean> getDeleteGameResult() {
        return deleteGameResult;
    }

    public void deleteGame(){
        gameRequests.deleteGame(game.getId(), getLoggedInUser().getLogin(), deleteGameResult);
    }

    public MutableLiveData<String> getFragmentNavigator() {
        return fragmentNavigator;
    }

    public void addResultFragment(){
        this.fragmentNavigator.setValue("result");
    }

    public void addStatsFragment(){
        this.fragmentNavigator.setValue("stats");
    }

    public MutableLiveData<GameDTO> getAddResult() {
        return addResult;
    }

    public void sendResult(String result){
        gameRequests.addResultToGame(game.getId(), result, addResult);
    }
}
