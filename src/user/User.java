package user;

import fileio.UserInputData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class User {
    /**
     * User's username
     */
    private final String username;
    /**
     * Subscription Type
     */
    private final String subscriptionType;
    /**
     * The history of the movies seen
     */
    private final Map<String, Integer> history;
    /**
     * Movies added to favorites
     */
    private final ArrayList<String> favoriteMovies;
    /**
     * Ratings added for shows
     */
    private final Map<String, Integer> ratedShows;

    public User(final UserInputData userInput) {
        username = userInput.getUsername();
        subscriptionType = userInput.getSubscriptionType();
        history = userInput.getHistory();
        favoriteMovies = userInput.getFavoriteMovies();
        ratedShows = new HashMap<>();
    }

    public Map<String, Integer> getRatedShows() {
        return ratedShows;
    }

    public String getUsername() {
        return username;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public Map<String, Integer> getHistory() {
        return history;
    }

    public ArrayList<String> getFavoriteMovies() {
        return favoriteMovies;
    }
}
