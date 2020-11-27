package actions;

import actor.Actor;
import common.Constants;
import entertainment.Show;
import fileio.ActionInputData;
import repository.Repo;
import user.User;
import utils.Utils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Query {

    /**
     * for coding style
     */
    private Query() {
    }

    /**
     *
     * @param repository
     * @param query
     * @return
     */
    public static String execute(Repo repository, ActionInputData query) {
        String result;
        switch (query.getObjectType()) {
            case Constants.ACTORS:
                switch (query.getCriteria()) {
                    case Constants.AVERAGE -> result = actorsAverage(repository, query);
                    case Constants.AWARDS -> result = actorsAwards(repository, query);
                    case Constants.FILTER_DESCRIPTIONS -> result = actorsFilter(repository, query);
                    default -> result = "error -> invalid query";
                }
                break;
            case Constants.SHOWS:
            case Constants.MOVIES:
                List<Show> filteredShows = SortUtils.filterShows(repository, query);
                switch (query.getCriteria()) {
                    case Constants.RATINGS -> result = showRating(filteredShows, query);
                    case Constants.FAVORITE -> result = showFavorite(repository, filteredShows, query);
                    case Constants.LONGEST -> result = showLongest(filteredShows, query);
                    case Constants.MOST_VIEWED -> result = mostViewed(repository, filteredShows, query);
                    default -> result = "error -> invalid query";
                }
                break;
            case Constants.USERS:
                if (query.getCriteria().equals(Constants.NUM_RATINGS))
                    result = mostRatingsUsers(repository, query);
                else
                    result = "error -> invalid query";
                break;
            default:
                result = "error -> invalid query";
                break;
        }
        return result;
    }

    public static String actorsAwards(Repo repository, ActionInputData query) {
        List<Actor> actorList = repository.getActorList();
        Map<String, Double> filteredActorMap = new HashMap<>();
        List<String> result;
        // Filter
        for (Actor actor : actorList) {
            boolean hasAwards = true;
            for (String award : query.getFilters().get(3)) {
                if (actor.getAwards().get(Utils.stringToAwards(award)) == null) {
                    hasAwards = false;
                    break;
                }
            }
            if (hasAwards)
                filteredActorMap.put(actor.getName(), (double) actor.getAwardsNumber());
        }
        // Sort
        result = SortUtils.sortMap(filteredActorMap, query.getSortType(), query.getNumber());
        return "Query result: " + result;
    }

    public static String actorsAverage(Repo repository, ActionInputData query) {
        List<Actor> actorList = repository.getActorList();
        Map<String, Double> filteredActorMap = new HashMap<>();
        List<String> result;
        // Filter
        for (Actor actor : actorList) {
            int ratingCount = 0;
            double ratingSum = 0;
            for (String title : actor.getFilmography()) {
                Show show = repository.getShow(title);
                if (show == null || show.getRating() == null) {
                    continue;
                }
                ratingCount++;
                ratingSum += show.getRating();
            }
            if (ratingCount != 0) {
                filteredActorMap.put(actor.getName(), ratingSum / ratingCount);
            }
        }
        // Sort
        result = SortUtils.sortMap(filteredActorMap, query.getSortType(), query.getNumber());
        return "Query result: " + result;
    }

    public static String actorsFilter(Repo repository, ActionInputData query) {
        List<Actor> actorList = repository.getActorList();
        Map<String, Double> filteredActorMap = new HashMap<>();
        List<String> result;
        // Filter
        for (Actor actor : actorList) {
            boolean hasKeywords = true;
            for (String word : query.getFilters().get(2)) {
                String patternString = " " + word + " ";
                Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(actor.getCareerDescription());
                boolean matchFound = matcher.find();
                if (!matchFound) {
                    hasKeywords = false;
                    break;
                }
            }
            if (hasKeywords) {
                filteredActorMap.put(actor.getName(), 0.0);
            }
        }
        result = SortUtils.sortMap(filteredActorMap, query.getSortType(), query.getNumber());
        return "Query result: " + result;
    }

    public static String showRating(List<Show> showList, ActionInputData query) {
        Map<String, Double> filteredShowMap = new HashMap<>();
        List<String> result;
        for (Show show : showList) {
            if (show.getRating() != null) {
                filteredShowMap.put(show.getTitle(), show.getRating());
            }
        }
        result = SortUtils.sortMap(filteredShowMap, query.getSortType(), query.getNumber());
        return "Query result: " + result;
    }

    public static String showFavorite(Repo repository, List<Show> showList, ActionInputData query) {
        List<User> userList = repository.getUserList();
        Map<String, Double> filteredShowMap = new HashMap<>();
        ArrayList<String> result;
        for (Show show : showList) {
            int favoriteCount = 0;
            for (User user : userList) {
                if (user.getFavoriteMovies().contains(show.getTitle()))
                    favoriteCount++;
            }
            if (favoriteCount != 0) {
                filteredShowMap.put(show.getTitle(), (double) favoriteCount);
            }
        }
        result = SortUtils.sortMap(filteredShowMap, query.getSortType(), query.getNumber());
        return "Query result: " + result;
    }

    public static String showLongest(List<Show> showList, ActionInputData query) {
        Map<String, Double> filteredShowMap = new HashMap<>();
        ArrayList<String> result;
        for(Show show : showList) {
            filteredShowMap.put(show.getTitle(), (double)show.getDuration());
        }
        result = SortUtils.sortMap(filteredShowMap, query.getSortType(), query.getNumber());
        return "Query result: " + result;
    }

    public static String mostViewed(Repo repository, List<Show> showList, ActionInputData query) {
        List<User> userList = repository.getUserList();
        Map<String, Double> filteredShowMap = new HashMap<>();
        ArrayList<String> result;
        for (Show show : showList) {
            int viewCount = 0;
            for (User user : userList) {
                if (user.getHistory().containsKey(show.getTitle()))
                    viewCount += user.getHistory().get(show.getTitle());
            }
            if (viewCount != 0)
                filteredShowMap.put(show.getTitle(), (double) viewCount);
        }
        result = SortUtils.sortMap(filteredShowMap, query.getSortType(), query.getNumber());
        return "Query result: " + result;
    }

    public static String mostRatingsUsers(Repo repository, ActionInputData query) {
        List<User> userList = repository.getUserList();
        List<String> result;
        Map<String, Double> filteredUserMap= new HashMap<>();
        // filter
        for (User user : userList) {
            if (user.getRatedShows().size() != 0) {
                filteredUserMap.put(user.getUsername(), (double) user.getRatedShows().size());
            }
        }
        // sort
        result = SortUtils.sortMap(filteredUserMap, query.getSortType(), query.getNumber());
        return "Query result: " + result;
    }
}
