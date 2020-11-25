package actions;


import entertainment.Movie;
import entertainment.Season;
import entertainment.Serial;
import fileio.ActionInputData;
import repository.Repo;
import user.User;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class Command {

    public static String execute(final Repo repository, final ActionInputData command) {
        String result;
        switch (command.getType()) {
            case "favorite" -> result = favorite(repository, command);
            case "view" -> result = view(repository, command);
            case "rating" -> result = rating(repository, command);
            default -> result = "error -> invalid command";
        }
        return result;
    }

    private static String favorite(final Repo repository, final ActionInputData command) {
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
                user.setFavoriteMovies(favoriteList);
                return "success -> " + command.getTitle() + " was added as favourite";
            }
        }
        return "error -> " + command.getTitle() + " is not seen";
    }

    private static String view(final Repo repository, final ActionInputData command) {
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
        user.setHistory(historyList);
        return "success -> " + command.getTitle() + " was viewed with total views of "
                + viewsNumber;
    }

    private static String rating(final Repo repository, final ActionInputData command) {
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
                    if (ratedShow.getKey().equals(command.getTitle()) && ratedShow.getValue() == seasonNumber) {
                        return "error -> " + command.getTitle() + " is already rated";
                    }
                }
                Map<String, Integer> ratedList = user.getRatedShows();
                ratedList.put(command.getTitle(), seasonNumber);
                user.setRatedShows(ratedList);
                if (seasonNumber != 0) {
                    List<Serial> serialList = repository.getSerialList();
                    for (Serial serial : serialList) {
                        if (command.getTitle().equals(serial.getTitle())) {
                            ArrayList<Season> seasonList = serial.getSeasons();
                            Season season = serial.getSeasons().get(seasonNumber - 1);
                            List<Double> seasonRatings = season.getRatings();
                            seasonRatings.add(command.getGrade());
                            season.setRatings(seasonRatings);
                            seasonList.set(seasonNumber - 1, season);
                            serial.setSeasons(seasonList);
                        }
                    }
                } else {
                    List<Movie> movieList = repository.getMovieList();
                    for (Movie movie : movieList) {
                        if (command.getTitle().equals(movie.getTitle())) {
                            ArrayList<Double> ratingsList= movie.getRatings();
                            ratingsList.add(command.getGrade());
                            movie.setRatings(ratingsList);
                        }
                    }
                }
                return "success -> " + command.getTitle() + " was rated with " + command.getGrade()
                        + " by " + command.getUsername();
            }
        }
        return "error -> " + command.getTitle() + " is not seen";
    }

    private Command() {

    }
}
