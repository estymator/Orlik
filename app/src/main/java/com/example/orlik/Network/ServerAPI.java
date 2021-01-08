package com.example.orlik.Network;

import com.example.orlik.data.model.Friends;
import com.example.orlik.data.model.Game;
import com.example.orlik.data.model.Notification;
import com.example.orlik.data.model.Pitch;
import com.example.orlik.data.model.PitchConfirmations;
import com.example.orlik.data.model.PlayersGame;
import com.example.orlik.data.model.User;
import com.example.orlik.data.model.dto.GameDTO;
import com.example.orlik.data.model.dto.NotificationDTO;
import com.example.orlik.data.model.dto.UserDTO;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface ServerAPI {

    @POST("/login")
    Call<UserDTO> loginUser(
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
     * @param valid
     * @param login
     * @return
     */
    @GET("/pitch")
    Call<ArrayList<Pitch>> getPitch(
            @Query("lat") double lat,
            @Query("lon") double lon,
            @Query("range") int range,
            @Query("valid") boolean valid,
            @Query("login") String login
    );

    /**
     * Get all pitch where valid={valid}
     * @param valid
     * @return
     */

    @GET("/pitch/all")
    Call<ArrayList<Pitch>> getAllPitch(
            @Query("valid") boolean valid
    );


    /**
     * Confirm pitch Localization
     * @param pitchId
     * @param login
     * @return
     */
    @POST("/pitch/verify")
    Call<PitchConfirmations> confirmPitch(
            @Query("pitchId") int pitchId,
            @Query("login") String login
    );

    /**
     * find pitch with address matching to given regex query
     * @param query
     * @return
     */
    @GET("/pitch/find")
    Call<ArrayList<Pitch>> findPitch(
            @Query("query") String query
    );

    /**
     * Add new pitch
     * @param type
     * @param location
     * @param address
     * @return new Pitch
     */

    @POST("/pitch")
    Call<Pitch> addPitch(
            @Query("type") String type,
            @Query("location") String location,
            @Query("address") String address
    );

    /**
     *
     * @param maxPlayersNumber
     * @param minPlayersNumber
     * @param pitchId
     * @param organiserLogin
     * @param visibility 1-publiczny, 0 prywatny
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

    @GET("/game/organiser/finished")
    Call<ArrayList<GameDTO>> getfinischedGames(
            @Query("login") String login
    );

    @GET("/game/checked")
    Call<ArrayList<GameDTO>> getGamesChecked(
            @Query("login") String login
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

    @DELETE("/game")
    Call<Boolean> deleteGame(
            @Query("login") String login,
            @Query("gameId") Integer gameId
    );

    @DELETE("game/remove")
    Call<Boolean> optOutPlayer(
            @Query("gameId") Integer gameId,
            @Query("login") String login
    );

    /**
     * return info about players from given game in format login:name:surname
     * @param gameId
     * @return
     */
    @GET("/game/players")
    Call<ArrayList<String>> getPlayersOfGame(
            @Query("gameId") Integer gameId
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

    @POST("/game/addplayer")
    Call<PlayersGame> addPlayerToGame(
        @Query("gameId") Integer gameId,
        @Query("login") String login,
        @Query("team") Integer team
    );

    @POST("/game/result")
    Call<GameDTO> addResult(
        @Query("gameId") Integer gameId,
        @Query("result") String result
    );


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

    /**
     * gets all notifications for one user
     * @param login
     * @return
     */
    @GET("/notification")
    Call<ArrayList<NotificationDTO>> getNotifications(
        @Query("login") String login
    );


    @GET("/notification/find")
    Call<Notification> findNotification(
            @Query("sourceLogin") String sourceLogin,
            @Query("destinationLogin") String destinationLogin,
            @Query("type") Integer type
    );


    @DELETE("/notification")
    Call<Boolean> deleteNotification(
        @Query("id") Integer id
    );

    @POST("/notification")
    Call<Notification> addNotification(
        @Query("sourceLogin") String sourceLogin,
        @Query("destinationLogin") String destinationLogin,
        @Query("type") Integer type
    );

    /**
     * Delete given pitch, only for admins
     * @param pitchId
     * @return
     */
    @DELETE("/admin/pitch")
    Call<Boolean> deletePitch(
        @Query("pitchId") Integer pitchId
    );

    /**
     * set given pitch as Valid, only for admins
     * @param pitchId
     * @return
     */
    @PUT("/admin/pitch/verify")
    Call<Pitch> verifyPitchAsAdmin(
        @Query("pitchId") Integer pitchId
    );

    /**
     * Delete given user, only for admins
     * @param login
     * @return
     */
    @DELETE("/admin/user")
    Call<Boolean> deleteUser(
        @Query("login") String login
    );

    /**
     * block given user, only for admins
     * @param login
     * @return
     */
    @POST("/admin/user/block")
    Call<User> blockUser(
        @Query("login") String login
    );

    @POST("admin/user/unban")
    Call<User> unbanUser(
        @Query("login") String login
    );

    /**
     * ONLY FOR ADMIN return list of all users
     * @return
     */
    @GET("admin/user/all")
    Call<List<User>> getAllUsers();
//    TODO delete friends | make friends | getgamesWithUser | getGamesOrganizesByUser |
}
