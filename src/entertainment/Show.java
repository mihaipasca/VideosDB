package entertainment;

import fileio.ShowInput;

import java.util.ArrayList;

public abstract class Show {
    /**
     * Show's title
     */
    private final String title;
    /**
     * The year the show was released
     */
    private final Integer year;
    /**
     * Show casting
     */
    private final ArrayList<String> cast;
    /**
     * Show genres
     */
    private final ArrayList<String> genres;

    public Show(final ShowInput showInput) {
        title = showInput.getTitle();
        year = showInput.getYear();
        cast = showInput.getCast();
        genres = showInput.getGenres();
    }

    /**
     * Method that computes the duration of a show
     * @return duration in minutes
     */
    public abstract int getDuration();

    /**
     * Method that computes the average rating of a show
     * @return Double
     */
    public abstract Double getRating();

    /**
     *
     * @return String
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @return String
     */
    public Integer getYear() {
        return year;
    }

    /**
     *
     * @return List of Strings with the names of cast
     */
    public ArrayList<String> getCast() {
        return cast;
    }

    /**
     *
     * @return List of Strings
     */
    public ArrayList<String> getGenres() {
        return genres;
    }
}
