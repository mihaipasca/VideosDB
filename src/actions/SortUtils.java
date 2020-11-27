package actions;

import common.Constants;
import entertainment.Show;
import repository.Repo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class SortUtils {
    /**
     * for coding style
     */
    private SortUtils(){
    }

    /**
     *
     * @param itemList
     * @param order
     * @return
     */
    public static ArrayList<String> sortMap(Map<String, Double> itemList, String order, Integer number) {
        ArrayList<Map.Entry<String, Double>> itemEntries = new ArrayList<>(itemList.entrySet());
        ArrayList<String> result = new ArrayList<>();
        if (order.equals(Constants.ASCENDENT)) {
            itemEntries.sort((item1, item2) -> {
                if (item1.getValue().equals(item2.getValue())) {
                    return item1.getKey().compareTo(item2.getKey());
                } else {
                    return Double.compare(item1.getValue(), item2.getValue());
                }
            });
        }
        if (order.equals("desc")) {
            itemEntries.sort((item1, item2) -> {
                if (item1.getValue().equals(item2.getValue())) {
                    return item2.getKey().compareTo(item1.getKey());
                } else {
                    return Double.compare(item2.getValue(), item1.getValue());
                }
            });
        }
        if (order.equals("desc/name")) {
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

    public static List<Show> filterShows(Repo repository, String objectType, String year, List<String> genres){
        List<Show> showList = new ArrayList<>();
        List<Show> filteredShowList = new ArrayList<>();
        switch (objectType) {
            case Constants.SHOWS -> showList.addAll(repository.getSerialList());
            case Constants.MOVIES -> showList.addAll(repository.getMovieList());
            case Constants.ALL -> showList.addAll(repository.getShowList());
        }
        for(Show show : showList) {
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
            if (itsOk)
                filteredShowList.add(show);
        }
        return filteredShowList;
    }
}
