package utils;

import common.Constants;
import entertainment.Show;
import repository.Repo;
import user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class ActionsUtils {
    /**
     * for coding style
     */
    private ActionsUtils() {
    }

    /**
     * Method that sorts the items of a map by the value field, in a ascendant
     * or descendant order
     * @param itemMap with the items that need to be sorted
     * @param order of the sorting
     * @return a list with the sorted items
     */
    public static ArrayList<String> sortMap(final Map<String, Double> itemMap, final String order,
                                            final Integer number) {
        ArrayList<Map.Entry<String, Double>> itemEntries = new ArrayList<>(itemMap.entrySet());
        ArrayList<String> result = new ArrayList<>();
        if (order.equals(Constants.ASCENDANT)) {
            itemEntries.sort((item1, item2) -> {
                if (item1.getValue().equals(item2.getValue())) {
                    return item1.getKey().compareTo(item2.getKey());
                } else {
                    return Double.compare(item1.getValue(), item2.getValue());
                }
            });
        }
        if (order.equals(Constants.DESCENDANT)) {
            itemEntries.sort((item1, item2) -> {
                if (item1.getValue().equals(item2.getValue())) {
                    return item2.getKey().compareTo(item1.getKey());
                } else {
                    return Double.compare(item2.getValue(), item1.getValue());
                }
            });
        }
        if (order.equals(Constants.DESC_INPUT)) {
            itemEntries.sort((item1, item2) -> Double.compare(item2.getValue(), item1.getValue()));
        }
        for (Map.Entry<String, Double> entry : itemEntries) {
            if (number != null && result.size() == number) {
                break;
            }
            result.add(entry.getKey());
        }
        return result;
    }

    /**
     * Method that filters shows by the year in which they were produced and
     * the genres in which they are categorized into
     * @param repository with the input data
     * @param objectType of the items to be filtered
     * @param year to be filtered by
     * @param genres to be filtered by
     * @return a list with the filtered shows
     */
    public static List<Show> filterShows(final Repo repository, final String objectType,
                                         final String year, final List<String> genres) {
        List<Show> showList = new ArrayList<>();
        List<Show> filteredShowList = new ArrayList<>();
        switch (objectType) {
            case Constants.SHOWS -> showList.addAll(repository.getSerialList());
            case Constants.MOVIES -> showList.addAll(repository.getMovieList());
            case Constants.ALL -> showList.addAll(repository.getShowList());
            // default
        }
        for (Show show : showList) {
            boolean itsOk = true;
            if (year != null && !year.equals(show.getYear().toString())) {
                    itsOk = false;
            }
            for (String genre : genres) {
                if (genre != null && !show.getGenres().contains(genre)) {
                    itsOk = false;
                    break;
                }
            }
            if (itsOk) {
                filteredShowList.add(show);
            }
        }
        return filteredShowList;
    }

    /**
     * TODO
     * @param userList
     * @param showList
     * @param filteredShowMap
     */
    public static void getFavoriteMap(final List<User> userList, final List<Show> showList, Map<String, Double> filteredShowMap) {
        for (Show show : showList) {
            int favoriteCount = 0;
            for (User user : userList) {
                if (user.getFavoriteMovies().contains(show.getTitle())) {
                    favoriteCount++;
                }
            }
            if (favoriteCount != 0) {
                filteredShowMap.put(show.getTitle(), (double) favoriteCount);
            }
        }
    }
}
