package repository;


import java.util.ArrayList;
import java.util.List;

import entertainment.Show;
import fileio.*;
import actor.Actor;
import user.User;
import entertainment.Movie;
import entertainment.Serial;

public class Repo {
    private List<Actor> actorList = new ArrayList<>();
    private List<User> userList = new ArrayList<>();
    private List<Movie> movieList = new ArrayList<>();
    private List<Serial> serialList = new ArrayList<>();
    private List<Show> showList = new ArrayList<>();

    public Repo(Input input) {
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

    public Actor getActor(final String name) {
//        Actor actor ;
//        for (Actor actor : actorList) {
//            if (name.equals(actor.get())) {
//                return user;
//            }
//        }
//        return actor;
        return null;
    }

    public User getUser(final String username) {
        for (User user : userList) {
            if (username.equals(user.getUsername())) {
                return user;
            }
        }
        return null;
    }
    public Show getShow(final String title) {
        for (Show show : showList) {
            if (title.equals(show.getTitle())) {
                return show;
            }
        }
        return null;
    }

    public void setUserList(final List<User> userList) {
        this.userList = userList;
    }

    public List<Movie> getMovieList() {
        return movieList;
    }

    public void setMovieList(final List<Movie> movieList) {
        this.movieList = movieList;
    }

    public List<Serial> getSerialList() {
        return serialList;
    }

    public void setSerialList(final List<Serial> serialList) {
        this.serialList = serialList;
    }

    public List<Actor> getActorList() {
        return actorList;
    }

    public void setActorList(List<Actor> actorList) {
        this.actorList = actorList;
    }

    public List<User> getUserList() {
        return userList;
    }

    public List<Show> getShowList() {
        return showList;
    }

    public void setShowList(List<Show> showList) {
        this.showList = showList;
    }
}
