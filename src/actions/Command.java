package actions;


import common.Constants;
import entertainment.Movie;
import entertainment.Season;
import entertainment.Serial;
import fileio.ActionInputData;
import repository.Repo;
import user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class Command {


    public Command() {
    }

    /**
     *
     * @param repository with the input data
     * @param command parameters of the command to be executed
     * @return String with the result of the command
     */
    public String execute(final Repo repository, final ActionInputData command) {
        String result;
        switch (command.getType()) {
            case Constants.FAVORITE:
                result = favorite(repository, command);
                break;
            case Constants.VIEW:
                result = view(repository, command);
                break;
            case Constants.RATING:
                result = rating(repository, command);
                break;
            default:
                result = "error -> invalid command";
                break;
        }
        return result;
    }

    /**
     *
     * @param repository with the input data
     * @param command parameters of the command to be executed
     * @return String with the result of the favorite command
     */
    private String favorite(final Repo repository, final ActionInputData command) {
        User user = repository.getUser(command.getUsername());
        if (!user.getUsername().equals(command.getUsername())) {
            return "error -> " + user.getUsername() + " does not exist";
        }
        for (Map.Entry<String, Integer> show : user.getHistory().entrySet()) {
            if (show.getKey().equals(command.getTitle())) {
                for (String movie : user.getFavoriteMovies()) {
                    if (movie.equals(command.getTitle())) {
                        return "error -> " + command.getTitle() + " is already in favourite list";
                    }
                }
                ArrayList<String> favoriteList = user.getFavoriteMovies();
                favoriteList.add(command.getTitle());
                return "success -> " + command.getTitle() + " was added as favourite";
            }
        }
        return "error -> " + command.getTitle() + " is not seen";
    }

    /**
     *
     * @param repository with all the data
     * @param command to be executed
     * @return String with the result of the view command
     */
    private String view(final Repo repository, final ActionInputData command) {
        int viewsNumber = 1;
        User user = repository.getUser(command.getUsername());
        if (!user.getUsername().equals(command.getUsername())) {
            return "error -> " + user.getUsername() + " does not exist";
        }
        for (Map.Entry<String, Integer> entry : user.getHistory().entrySet()) {
            if (entry.getKey().equals(command.getTitle())) {
                viewsNumber += entry.getValue();
                break;
            }
        }
        Map<String, Integer> historyList = user.getHistory();
        historyList.put(command.getTitle(), viewsNumber);
        return "success -> " + command.getTitle() + " was viewed with total views of "
                + viewsNumber;
    }

    /**
     *
     * @param repository with all the data
     * @param command to be executed
     * @return String with the result of the rating command
     */
    private String rating(final Repo repository, final ActionInputData command) {
        User user = repository.getUser(command.getUsername());
        int seasonNumber;
        if (command.getSeasonNumber() != 0) {
            seasonNumber = command.getSeasonNumber();
        } else {
            seasonNumber = 0;
        }
        if (!user.getUsername().equals(command.getUsername())) {
            return "error -> " + user.getUsername() + " does not exist";
        }
        for (Map.Entry<String, Integer> show : user.getHistory().entrySet()) {
            if (show.getKey().equals(command.getTitle())) {
                for (Map.Entry<String, Integer> ratedShow : user.getRatedShows().entrySet()) {
                    if (ratedShow.getKey().equals(command.getTitle())
                            && ratedShow.getValue() == seasonNumber) {
                        return "error -> " + command.getTitle() + " has been already rated";
                    }
                }
                Map<String, Integer> ratedList = user.getRatedShows();
                ratedList.put(command.getTitle(), seasonNumber);
                if (seasonNumber != 0) {
                    List<Serial> serialList = repository.getSerialList();
                    for (Serial serial : serialList) {
                        if (command.getTitle().equals(serial.getTitle())) {
                            Season season = serial.getSeasons().get(seasonNumber - 1);
                            List<Double> seasonRatings = season.getRatings();
                            seasonRatings.add(command.getGrade());
                        }
                    }
                } else {
                    List<Movie> movieList = repository.getMovieList();
                    for (Movie movie : movieList) {
                        if (command.getTitle().equals(movie.getTitle())) {
                            ArrayList<Double> ratingsList = movie.getRatings();
                            ratingsList.add(command.getGrade());
                        }
                    }
                }
                return "success -> " + command.getTitle() + " was rated with " + command.getGrade()
                        + " by " + command.getUsername();
            }
        }
        return "error -> " + command.getTitle() + " is not seen";
    }
}
