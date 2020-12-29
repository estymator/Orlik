package com.example.orlik.Network;

import com.example.orlik.data.model.Friends;
import com.example.orlik.data.model.Game;
import com.example.orlik.data.model.Pitch;
import com.example.orlik.data.model.User;
import com.example.orlik.data.model.dto.GameDTO;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ServerAPI {

    @POST("/login")
    Call<User> loginUser(
            @Query("login") String login,
            @Query("password") String password
    );


    @POST("/register")
    Call<User> registerUser(
            @Query("login") String login,
            @Query("password") String password,
            @Query("name") String name,
            @Query("surname") String surname,
            @Query("userRole") int userRole
    );


    @GET("/logout")
    Call<Object> logoutUser();

    /**
     * function returns currenytly logged in user
     * @return
     */
    @GET("/home")
    Call<User> getLoggedInUser();

    /**
     * Return all pitchs in given range from given localization
     * @param lon longitude
     * @param lat latitude
     * @param range range of searching
     * @return
     */
    @GET("/pitch")
    Call<ArrayList<Pitch>> getPitch(
            @Query("lat") double lat,
            @Query("lon") double lon,
            @Query("range") int range
    );


    @POST("/pitch")
    Call<Pitch> addPitch(
            @Query("type") String type,
            @Query("location") String location,
            @Query("adress") String adress
    );

    /**
     *
     * @param maxPlayersNumber
     * @param minPlayersNumber
     * @param pitchId
     * @param organiserLogin
     * @param visibility
     * @param schedule hh:mm yyyy-mm-dd
     * @param description
     * @param duration
     * @param isOrganiserPlaying whether organiser want to play in this match or not
     * @return added Game
     */
    @POST("/game/add")
    Call<Game> addGame(
            @Query("maxPlayersNumber") int maxPlayersNumber,
            @Query("minPlayersNumber") int minPlayersNumber,
            @Query("pitchId") int pitchId,
            @Query("organiserLogin") String organiserLogin,
            @Query("visibility") int visibility,
            @Query("schedule") String schedule,
            @Query("description") String description,
            @Query("duration") Integer duration,
            @Query("isOrganiserPlaying") Boolean isOrganiserPlaying
    );

    /**
     * return all games played on pitchType pitches in given range from given destination
     * @param lat latitude
     * @param lon longitude
     * @param range range
     * @param pitchType type- Dowolny, Orlik, Hala, Trawiaste, Płyta Gumowa
     * @return
     */
    @GET("/game/range")
    Call<ArrayList<GameDTO>> getGames(
            @Query("lat") double lat,
            @Query("lon") double lon,
            @Query("range") int range,
            @Query("pitchType") String pitchType
    );

    /**
     * return list of games organized by user with given login
     * @param organiserLogin
     * @return
     */
    @GET("/game/login")
    Call<ArrayList<GameDTO>> getGamesByOrganiserLogin(
            @Query("organiserLogin") String organiserLogin
    );

    /**
     * return list of games in which play user with given login
     * @param playerLogin
     * @return
     */
    @GET("/game/login")
    Call<ArrayList<GameDTO>> getGamesByPlayerLogin(
            @Query("playerLogin") String playerLogin
    );


    /**
     * ONLY FOR ADMIN return list of all users
     * @return
     */
    @GET("admin/user/all")
    Call<List<User>> getAllUsers();

    /**
     * Return list of given user friends
     * @param login
     * @return
     */
    @GET("/user/friends/all")
    Call<ArrayList<User>> getFriends(
            @Query("userLogin") String login
    );

    /**
     * return list of users which name or surname match to given query
     * @param query
     * @return
     */
    @GET("/user/find")
    Call<ArrayList<User>> findUsers(
        @Query("query") String query
    );

    /**
     * Add friends connection beetwen two users
     * @param firstLogin
     * @param secondLogin
     * @return
     */
    @POST("/user/friends")
    Call<Friends> addFriends(
            @Query("firstLogin") String firstLogin,
            @Query("secondLogin") String secondLogin
    );

    /**
     * Delete friends beetwen two users
     * @param firstLogin
     * @param secondLogin
     * @return false/true
     */
    @DELETE("/user/friends")
    Call<Boolean> deleteFriends(
            @Query("firstLogin") String firstLogin,
            @Query("secondLogin") String secondLogin
    );

//    TODO delete friends | make friends | getgamesWithUser | getGamesOrganizesByUser |
}
