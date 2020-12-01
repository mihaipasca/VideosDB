package actions;

import common.Constants;
import entertainment.Show;
import fileio.ActionInputData;
import repository.Repo;
import user.User;
import utils.ActionsUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class Recommendation {

    public Recommendation() {
    }

    /**
     * Method that interprets the recommendation and calls the helper
     * method for the recommendation type
     * @param repository with the input command
     * @param recommendation parameters of the recommendation
     * @return String with the result
     */
    public String execute(final Repo repository, final ActionInputData recommendation) {
        return switch (recommendation.getType()) {
            case Constants.FAVORITE -> favorite(repository, recommendation);
            case Constants.STANDARD -> standard(repository, recommendation);
            case Constants.POPULAR -> popular(repository, recommendation);
            case Constants.SEARCH -> search(repository, recommendation);
            case Constants.BEST_UNSEEN -> bestUnseen(repository, recommendation);
            default -> "error -> invalid query";
        };
    }

    /**
     * Method that finds the first not viewed show by a user in the database
     * @param repository with the input command
     * @param recommendation parameters of the recommendation
     * @return String with the result
     */
    private String standard(final Repo repository, final ActionInputData recommendation) {
        User user = repository.getUser(recommendation.getUsername());
        if (user == null) {
            return "error -> invalid user";
        }
        List<Show> showList = repository.getShowList();
        for (Show show : showList) {
            if (!user.getHistory().containsKey(show.getTitle())) {
                return "StandardRecommendation result: " + show.getTitle();
            }
        }
        return "StandardRecommendation cannot be applied!";
    }

    /**
     * Method that sorts the list of shows by their rating and finds the
     * the best rated one that is not seen by a user
     * @param repository with the input command
     * @param recommendation parameters of the recommendation
     * @return String with the result
     */
    private String bestUnseen(final Repo repository, final ActionInputData recommendation) {
        User user = repository.getUser(recommendation.getUsername());
        if (user == null) {
            return "error -> invalid user";
        }
        List<Show> showList = repository.getShowList();
        List<Show> sortedShowList = showList.stream().filter(show -> show.getRating() != null)
                                            .sorted(Comparator.comparingDouble(Show::getRating)
                                            .reversed()).collect(Collectors.toList());
        List<Show> sorted2ShowList = showList.stream().filter(show -> show.getRating() == null)
                                             .collect(Collectors.toList());
        sortedShowList.addAll(sorted2ShowList);
        for (Show show : sortedShowList) {
            if (!user.getHistory().containsKey(show.getTitle())) {
                return "BestRatedUnseenRecommendation result: " + show.getTitle();
            }
        }
        return "BestRatedUnseenRecommendation cannot be applied!";

    }

    /**
     * Method that finds the show that was not seen by the user from the
     * genre with the most views
     * @param repository with the input command
     * @param recommendation parameters of the recommendation
     * @return String with the result
     */
    private String popular(final Repo repository, final ActionInputData recommendation) {
        User user = repository.getUser(recommendation.getUsername());
        if (user == null) {
            return "error -> invalid user";
        }
        if (!user.getSubscriptionType().equals(Constants.PREMIUM)) {
            return "PopularRecommendation cannot be applied!";
        }
        ArrayList<String> result;
        List<Show> showList = repository.getShowList();
        List<User> userList = repository.getUserList();
        Map<String, Double> genreMap = new HashMap<>();
        Map<Show, Double> filteredShowMap = new HashMap<>();
        for (Show show : showList) {
            int viewCount = 0;
            for (User databaseUser: userList) {
                if (databaseUser.getHistory().containsKey(show.getTitle())) {
                    viewCount += databaseUser.getHistory().get(show.getTitle());
                }
            }
            if (viewCount != 0) {
                filteredShowMap.put(show, (double) viewCount);
            }
        }
        for (Map.Entry<Show, Double> show : filteredShowMap.entrySet()) {
            for (String genre : show.getKey().getGenres()) {
                if (genreMap.containsKey(genre)) {
                     genreMap.replace(genre, genreMap.get(genre) + show.getValue());
                } else {
                    genreMap.put(genre, show.getValue());
                }
            }
        }
        result = ActionsUtils.sortMap(genreMap, Constants.DESCENDANT, null);
        for (String genre : result) {
            for (Show show : showList) {
                if (show.getGenres().contains(genre)
                        && !user.getHistory().containsKey(show.getTitle())) {
                    return "PopularRecommendation result: " + show.getTitle();
                }
            }
        }
        return "PopularRecommendation cannot be applied!";
    }

    /**
     * Method that finds the best rated show from a specified genre
     * that was not seen by the user
     * @param repository with the input command
     * @param recommendation parameters of the recommendation
     * @return String with the result
     */
    private String search(final Repo repository, final ActionInputData recommendation) {
        User user = repository.getUser(recommendation.getUsername());
        if (user == null) {
            return "error -> invalid user";
        }
        if (!user.getSubscriptionType().equals(Constants.PREMIUM)) {
            return "SearchRecommendation cannot be applied!";
        }
        ArrayList<String> result;
        Map<String, Double> sortedShowList = new HashMap<>();
        ArrayList<String> genreList = new ArrayList<>();
        genreList.add(recommendation.getGenre());
        List<Show> filteredShowList = ActionsUtils.filterShows(repository, Constants.ALL, null,
                genreList);
        if (filteredShowList == null) {
            return "SearchRecommendation cannot be applied!";
        }
        for (Show show : filteredShowList) {
            if (!user.getHistory().containsKey(show.getTitle())) {
                if (show.getRating() == null) {
                    sortedShowList.put(show.getTitle(), 0.0);
                } else {
                    sortedShowList.put(show.getTitle(), show.getRating());
                }
            }
        }
        result = ActionsUtils.sortMap(sortedShowList,  Constants.ASCENDANT, null);
        if (result.isEmpty()) {
            return "SearchRecommendation cannot be applied!";
        }
        return "SearchRecommendation result: " + result;
    }

    /**
     * Method that finds the show which is put in the favorite list the most and which
     * is not seen by the user
     * @param repository with the input command
     * @param recommendation parameters of the recommendation
     * @return String with the result
     */
    private String favorite(final Repo repository, final ActionInputData recommendation) {
        User user = repository.getUser(recommendation.getUsername());
        if (user == null) {
            return "error -> invalid user";
        }
        if (!user.getSubscriptionType().equals(Constants.PREMIUM)) {
            return "FavoriteRecommendation cannot be applied!";
        }
        List<User> userList = repository.getUserList();
        List<Show> showList = repository.getShowList();
        Map<String, Double> filteredShowMap;
        filteredShowMap = ActionsUtils.getFavoriteMap(userList, showList);
        ArrayList<String> result = ActionsUtils.sortMap(filteredShowMap,
                Constants.DESC_INPUT, null);
        for (String title : result) {
            if (!user.getHistory().containsKey(title)) {
                return "FavoriteRecommendation result: " + title;
            }
        }
        return "FavoriteRecommendation cannot be applied!";
    }
}
