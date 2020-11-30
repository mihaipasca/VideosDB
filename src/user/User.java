package user;

import fileio.UserInputData;

import java.util.*;

public final class User {
    private final String username;
    private final String subscriptionType;
    private final Map<String, Integer> history;
    private final ArrayList<String> favoriteMovies;
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
