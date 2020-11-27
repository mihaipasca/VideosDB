package actions;

import common.Constants;
import entertainment.Show;
import fileio.ActionInputData;
import repository.Repo;
import user.User;

import java.util.*;
import java.util.stream.Collectors;

public class Recommendation {

    public static String execute(Repo repository, ActionInputData recommendation) {
        String result = switch (recommendation.getType()) {
            case Constants.FAVORITE -> favorite(repository, recommendation);
            case Constants.STANDARD -> standard(repository, recommendation);
            case Constants.POPULAR -> popular(repository, recommendation);
            case Constants.SEARCH -> search(repository, recommendation);
            case Constants.BEST_UNSEEN -> best_unseen(repository, recommendation);
            default -> "error -> invalid query";
        };
        return result;
    }
    public static String standard(Repo repository, ActionInputData recommendation) {
        List<Show> showList = repository.getShowList();
        User user = repository.getUser(recommendation.getUsername());
        for (Show show : showList) {
            if (!user.getHistory().containsKey(show.getTitle())) {
                return "StandardRecommendation result: " + show.getTitle();
            }
        }
        return "StandardRecommendation cannot be applied!";
    }
    public static String best_unseen(Repo repository, ActionInputData recommendation) {
        List<Show> showList = repository.getShowList();
        List<Show> sortedShowList = showList.stream().filter(show -> show.getRating() != null)
                .collect(Collectors.toList());
        User user = repository.getUser(recommendation.getUsername());
        sortedShowList.sort(Comparator.comparingDouble(Show::getRating).reversed());
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
    public static String popular(Repo repository, ActionInputData recommendation) {
        ArrayList<String> result;
        List<Show> showList = repository.getShowList();
        List<User> userList = repository.getUserList();
        Map<String, Double> genreMap= new HashMap<>();
        Map<Show, Double> filteredShowMap= new HashMap<>();
        User user = repository.getUser(recommendation.getUsername());
        if (!user.getSubscriptionType().equals(Constants.PREMIUM)) {
            return "PopularRecommendation cannot be applied!";
        }
        for (Show show : showList) {
            int viewCount = 0;
            for (User user1 : userList) {
                if (user1.getHistory().containsKey(show.getTitle()))
                    viewCount += user1.getHistory().get(show.getTitle());
            }
            if (viewCount != 0)
                filteredShowMap.put(show, (double) viewCount);
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
        result = SortUtils.sortMap(genreMap, "desc", null);
        for (String genre : result) {
            for (Show show : showList) {
                if (show.getGenres().contains(genre) &&
                        !user.getHistory().containsKey(show.getTitle())) {
                    return "PopularRecommendation result: " + show.getTitle();
                }
            }
        }
        return "PopularRecommendation cannot be applied!";
    }
    public static String search(Repo repository, ActionInputData recommendation) {
        ArrayList<String> result;
        User user = repository.getUser(recommendation.getUsername());
        Map<String, Double> sortedShowList = new HashMap<>();
        if (!user.getSubscriptionType().equals(Constants.PREMIUM)) {
            return "SearchRecommendation cannot be applied!";
        }
        ArrayList<String> genreList = new ArrayList<>();
        genreList.add(recommendation.getGenre());
        List<Show> filteredShowList = SortUtils.filterShows(repository, Constants.ALL, null,
                genreList);
        for (Show show : filteredShowList) {
            if (!user.getHistory().containsKey(show.getTitle())) {
                if (show.getRating() == null)
                    sortedShowList.put(show.getTitle(), 0.0);
                else
                    sortedShowList.put(show.getTitle(), show.getRating());
            }
        }
        result = SortUtils.sortMap(sortedShowList,  "asc", null);
        if (result.isEmpty())
            return "SearchRecommendation cannot be applied!";
        return "SearchRecommendation result: " + result;
    }
    public static String favorite(Repo repository, ActionInputData recommendation) {
        User user = repository.getUser(recommendation.getUsername());
        if (!user.getSubscriptionType().equals(Constants.PREMIUM)) {
            return "FavoriteRecommendation cannot be applied!";
        }
        List<User> userList = repository.getUserList();
        List<Show> showList = repository.getShowList();
        Map<String, Double> filteredShowMap = new HashMap<>();
        for (Show show : showList) {
            int favoriteCount = 0;
            for (User user1 : userList) {
                if (user1.getFavoriteMovies().contains(show.getTitle()))
                    favoriteCount++;
            }
            if (favoriteCount != 0) {
                filteredShowMap.put(show.getTitle(), (double) favoriteCount);
            }
        }
        ArrayList<String> result = SortUtils.sortMap(filteredShowMap, "desc/name", null);
        for (String title : result) {
            if (!user.getHistory().containsKey(title)) {
                return "FavoriteRecommendation result: " + title;
            }
        }
        return "FavoriteRecommendation cannot be applied!";

    }
}
