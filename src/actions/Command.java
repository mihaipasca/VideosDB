package actions;


import common.Constants;
import entertainment.Movie;
import entertainment.Season;
import entertainment.Serial;
import fileio.ActionInputData;
import repository.Repo;
import user.User;

import java.util.ArrayList;
import java.util.Map;

public final class Command {

    public Command() {
    }

    /**
     * Method that interprets the command and calls the helper method
     * for the command type
     * @param repository with the input data
     * @param command parameters of the command to be executed
     * @return String with the result of the command
     */
    public String execute(final Repo repository, final ActionInputData command) {
        return switch (command.getType()) {
            case Constants.FAVORITE -> favorite(repository, command);
            case Constants.VIEW -> view(repository, command);
            case Constants.RATING -> rating(repository, command);
            default -> "error -> invalid command";
        };
    }

    /**
     * Method that adds a video in the favorite list of a user
     * @param repository with the input data
     * @param command parameters of the command to be executed
     * @return String with the result of the favorite command
     */
    private String favorite(final Repo repository, final ActionInputData command) {
        User user = repository.getUser(command.getUsername());
        if (user == null) {
            return "error -> invalid user";
        }
        if (user.getHistory().containsKey(command.getTitle())) {
            if (user.getFavoriteMovies().contains(command.getTitle())) {
                return "error -> " + command.getTitle() + " is already in favourite list";
            }
            ArrayList<String> favoriteList = user.getFavoriteMovies();
            favoriteList.add(command.getTitle());
            return "success -> " + command.getTitle() + " was added as favourite";
        }
        return "error -> " + command.getTitle() + " is not seen";
    }

    /**
     * Method that adds a view for a show in the user history
     * @param repository with all the data
     * @param command parameters of the command to be executed
     * @return String with the result of the view command
     */
    private String view(final Repo repository, final ActionInputData command) {
        User user = repository.getUser(command.getUsername());
        if (user == null) {
            return "error -> invalid user";
        }
        int viewsNumber = 1;
        for (Map.Entry<String, Integer> entry : user.getHistory().entrySet()) {
            if (entry.getKey().equals(command.getTitle())) {
                viewsNumber += entry.getValue();
                break;
            }
        }
        user.getHistory().put(command.getTitle(), viewsNumber);
        return "success -> " + command.getTitle() + " was viewed with total views of "
                + viewsNumber;
    }

    /**
     * Method that adds a rating of a user for a season of a serial or for a movie done
     * @param repository with all the data
     * @param command parameters of the command to be executed
     * @return String with the result of the rating command
     */
    private String rating(final Repo repository, final ActionInputData command) {
        User user = repository.getUser(command.getUsername());
        if (user == null) {
            return "error -> invalid user";
        }
        int seasonNumber = 0;
        if (command.getSeasonNumber() != 0) {
            seasonNumber = command.getSeasonNumber();
        }
        if (user.getRatedShows().containsKey(command.getTitle())
                && user.getRatedShows().get(command.getTitle()) == seasonNumber) {
            return "error -> " + command.getTitle() + " has been already rated";
        }
        if (user.getHistory().get(command.getTitle()) != null) {
            user.getRatedShows().put(command.getTitle(), seasonNumber);
            if (seasonNumber != 0) {
                for (Serial serial : repository.getSerialList()) {
                    if (command.getTitle().equals(serial.getTitle())) {
                        Season season = serial.getSeasons().get(seasonNumber - 1);
                        season.getRatings().add(command.getGrade());
                    }
                }
            } else {
                for (Movie movie : repository.getMovieList()) {
                    if (command.getTitle().equals(movie.getTitle())) {
                        ArrayList<Double> ratingsList = movie.getRatings();
                        ratingsList.add(command.getGrade());
                    }
                }
            }
            return "success -> " + command.getTitle() + " was rated with " + command.getGrade()
                    + " by " + command.getUsername();
        }
        return "error -> " + command.getTitle() + " is not seen";
    }
}
