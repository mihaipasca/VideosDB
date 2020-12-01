package actions;

import actor.Actor;
import common.Constants;
import entertainment.Show;
import fileio.ActionInputData;
import repository.Repo;
import user.User;
import utils.ActionsUtils;
import utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Query {

    public Query() {
    }

    /**
     * Method that interprets the query and calls the helper
     * method for the query type
     * @param repository with the input data
     * @param query parameters of the query
     * @return String with the result of the query
     */
    public String execute(final Repo repository, final ActionInputData query) {
        switch (query.getObjectType()) {
            case Constants.ACTORS:
                return switch (query.getCriteria()) {
                    case Constants.AVERAGE -> actorsAverage(repository, query);
                    case Constants.AWARDS -> actorsAwards(repository, query);
                    case Constants.FILTER_DESCRIPTIONS -> actorsFilter(repository, query);
                    default -> "error -> invalid query";
                };
            case Constants.SHOWS:
            case Constants.MOVIES:
                // Filter
                List<Show> filteredShows = ActionsUtils.filterShows(repository,
                        query.getObjectType(),
                        query.getFilters().get(Constants.YEAR_FIELD).get(Constants.YEAR_FIELD),
                        query.getFilters().get(Constants.GENRE_FIELD));
                if (filteredShows == null) {
                    return "QueryResult: []";
                }
                return switch (query.getCriteria()) {
                    case Constants.RATINGS -> showRating(filteredShows, query);
                    case Constants.FAVORITE -> showFavorite(repository, filteredShows, query);
                    case Constants.LONGEST -> showLongest(filteredShows, query);
                    case Constants.MOST_VIEWED -> mostViewed(repository, filteredShows, query);
                    default -> "error -> invalid query";
                };
            case Constants.USERS:
                if (query.getCriteria().equals(Constants.NUM_RATINGS)) {
                    return mostRatingsUsers(repository, query);
                } else {
                    return "error -> invalid query";
                }
            default:
                return "error -> invalid query";
        }
    }

    /**
     * Method that finds the actors with all the specified awards, sorts them by the
     * number of awards in a specified order and returns a number of them
     * @param repository with the input data
     * @param query parameters of the query
     * @return String with the result of the query
     */
    private String actorsAwards(final Repo repository, final ActionInputData query) {
        List<Actor> actorList = repository.getActorList();
        Map<String, Double> filteredActorMap = new HashMap<>();
        List<String> result;
        // Filter
        for (Actor actor : actorList) {
            boolean hasAwards = true;
            for (String award : query.getFilters().get(Constants.AWARDS_FIELD)) {
                if (actor.getAwards().get(Utils.stringToAwards(award)) == null) {
                    hasAwards = false;
                    break;
                }
            }
            if (hasAwards) {
                filteredActorMap.put(actor.getName(), (double) actor.getAwardsNumber());
            }
        }
        // Sort
        result = ActionsUtils.sortMap(filteredActorMap, query.getSortType(), query.getNumber());
        return "Query result: " + result;
    }

    /**
     * Method that sorts all the actors by the average ratings of the shows in which
     * they played in a specified order and returns a number of them
     * @param repository with the input data
     * @param query parameters of the query
     * @return String with the result of the query
     */
    private String actorsAverage(final Repo repository, final ActionInputData query) {
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
        result = ActionsUtils.sortMap(filteredActorMap, query.getSortType(), query.getNumber());
        return "Query result: " + result;
    }

    /**
     * Method that filters the description of the actors by some keywords, and then sorts them
     * alphabetically
     * @param repository with the input data
     * @param query parameters of the query
     * @return String with the result of the query
     */
    private String actorsFilter(final Repo repository, final ActionInputData query) {
        List<Actor> actorList = repository.getActorList();
        Map<String, Double> filteredActorMap = new HashMap<>();
        List<String> result;
        // Filter
        for (Actor actor : actorList) {
            boolean hasKeywords = true;
            for (String word : query.getFilters().get(Constants.WORDS_FIELD)) {
                String patternString = "[ -]" + word + "[ ,.]";
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
        result = ActionsUtils.sortMap(filteredActorMap, query.getSortType(), query.getNumber());
        return "Query result: " + result;
    }

    /**
     * Method that sorts a list of shows by their rating
     * @param showList filtered list of the shows in the repository
     * @param query parameters of the query
     * @return String with the result of the query
     */
    private String showRating(final List<Show> showList, final ActionInputData query) {
        Map<String, Double> filteredShowMap = new HashMap<>();
        List<String> result;
        for (Show show : showList) {
            if (show.getRating() != null) {
                filteredShowMap.put(show.getTitle(), show.getRating());
            }
        }
        // Sort
        result = ActionsUtils.sortMap(filteredShowMap, query.getSortType(), query.getNumber());
        return "Query result: " + result;
    }

    /**
     * Method that sorts a list of shows by the number of times they are being
     * added to the favorite list
     * @param showList filtered list of the shows in the repository
     * @param query parameters of the query
     * @return String with the result of the query
     */
    private String showFavorite(final Repo repository, final List<Show> showList,
                                      final ActionInputData query) {
        List<User> userList = repository.getUserList();
        Map<String, Double> filteredShowMap;
        ArrayList<String> result;
        filteredShowMap = ActionsUtils.getFavoriteMap(userList, showList);
        // Sort
        result = ActionsUtils.sortMap(filteredShowMap, query.getSortType(), query.getNumber());
        return "Query result: " + result;
    }

    /**
     * Method that sorts a list of shows by their duration
     * @param showList filtered list of the shows in the repository
     * @param query parameters of the query
     * @return String with the result of the query
     */
    private String showLongest(final List<Show> showList, final ActionInputData query) {
        Map<String, Double> filteredShowMap = new HashMap<>();
        ArrayList<String> result;
        for (Show show : showList) {
            filteredShowMap.put(show.getTitle(), (double) show.getDuration());
        }
        // Sort
        result = ActionsUtils.sortMap(filteredShowMap, query.getSortType(), query.getNumber());
        return "Query result: " + result;
    }

    /**
     * Method that sorts a list of shows by the number of views they have
     * @param showList filtered list of the shows in the repository
     * @param query parameters of the query
     * @return String with the result of the query
     */
    private String mostViewed(final Repo repository, final List<Show> showList,
                                    final ActionInputData query) {
        List<User> userList = repository.getUserList();
        Map<String, Double> filteredShowMap = new HashMap<>();
        ArrayList<String> result;
        for (Show show : showList) {
            int viewCount = 0;
            for (User user : userList) {
                if (user.getHistory().containsKey(show.getTitle())) {
                    viewCount += user.getHistory().get(show.getTitle());
                }
            }
            if (viewCount != 0) {
                filteredShowMap.put(show.getTitle(), (double) viewCount);
            }
        }
        result = ActionsUtils.sortMap(filteredShowMap, query.getSortType(), query.getNumber());
        return "Query result: " + result;
    }

    /**
     * Method that sorts a list of users by the number of rating they gave
     * @param repository with the input data
     * @param query parameters of the query
     * @return String with the result of the query
     */
    private String mostRatingsUsers(final Repo repository, final ActionInputData query) {
        List<User> userList = repository.getUserList();
        List<String> result;
        Map<String, Double> filteredUserMap = new HashMap<>();
        // Filter
        for (User user : userList) {
            if (user.getRatedShows().size() != 0) {
                filteredUserMap.put(user.getUsername(), (double) user.getRatedShows().size());
            }
        }
        // Sort
        result = ActionsUtils.sortMap(filteredUserMap, query.getSortType(), query.getNumber());
        return "Query result: " + result;
    }
}
