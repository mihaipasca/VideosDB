package repository;


import java.util.ArrayList;
import java.util.List;

import entertainment.Show;
import actor.Actor;
import fileio.ActorInputData;
import fileio.Input;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;
import user.User;
import entertainment.Movie;
import entertainment.Serial;

public final class Repo {
    /**
     * List of actors
     */
    private final List<Actor> actorList = new ArrayList<>();
    /**
     * List of users
     */
    private final List<User> userList = new ArrayList<>();
    /**
     * List of movies
     */
    private final List<Movie> movieList = new ArrayList<>();
    /**
     * List of serials
     */
    private final List<Serial> serialList = new ArrayList<>();
    /**
     * List of shows
     */
    private final List<Show> showList = new ArrayList<>();

    public Repo(final Input input) {
        for (ActorInputData actorInput : input.getActors()) {
            Actor actor = new Actor(actorInput);
            actorList.add(actor);
        }
        for (UserInputData userInput : input.getUsers()) {
            User user = new User(userInput);
            userList.add(user);
        }
        for (MovieInputData movieInput : input.getMovies()) {
            Movie movie = new Movie(movieInput);
            movieList.add(movie);
        }
        for (SerialInputData serialInput : input.getSerials()) {
            Serial serial = new Serial(serialInput);
            serialList.add(serial);
        }
        showList.addAll(movieList);
        showList.addAll(serialList);
    }

    /**
     * Method that finds a user in the repository by the username
     * @param username of the user to find in the repository
     * @return User
     */
    public User getUser(final String username) {
        for (User user : userList) {
            if (username.equals(user.getUsername())) {
                return user;
            }
        }
        return null;
    }
    /**
     * Method that finds a show in the repository by the title
     * @param title of the show to find in the repository
     * @return Show
     */
    public Show getShow(final String title) {
        for (Show show : showList) {
            if (title.equals(show.getTitle())) {
                return show;
            }
        }
        return null;
    }

    public List<Movie> getMovieList() {
        return movieList;
    }

    public List<Serial> getSerialList() {
        return serialList;
    }

    public List<Actor> getActorList() {
        return actorList;
    }

    public List<User> getUserList() {
        return userList;
    }

    public List<Show> getShowList() {
        return showList;
    }
}
