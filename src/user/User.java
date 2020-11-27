package user;

import fileio.UserInputData;

import java.util.*;

public class User {
    private String username;
    private String subscriptionType;
    private Map<String, Integer> history;
    private ArrayList<String> favoriteMovies;
    private Map<String, Integer> ratedShows;

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

    public void setRatedShows(final Map<String, Integer> ratedShows) {
        this.ratedShows = ratedShows;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(final String subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public Map<String, Integer> getHistory() {
        return history;
    }

    public void setHistory(final Map<String, Integer> history) {
        this.history = history;
    }

    public ArrayList<String> getFavoriteMovies() {
        return favoriteMovies;
    }

    public void setFavoriteMovies(final ArrayList<String> favoriteMovies) {
        this.favoriteMovies = favoriteMovies;
    }
}
