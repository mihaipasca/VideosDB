package entertainment;

import fileio.ShowInput;

import java.util.ArrayList;

public abstract class Show {
    private final String title;
    private final Integer year;
    private final ArrayList<String> cast;
    private final ArrayList<String> genres;

    /**
     *
     * @param showInput TODO
     */
    public Show(final ShowInput showInput) {
        title = showInput.getTitle();
        year = showInput.getYear();
        cast = showInput.getCast();
        genres = showInput.getGenres();
    }

    /**
     *
     * @return todo
     */
    public abstract int getDuration();

    /**
     *
     * @return todo
     */
    public abstract Double getRating();

    /**
     *
     * @return TODO
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @return todo
     */
    public Integer getYear() {
        return year;
    }

    /**
     *
     * @return TODO
     */
    public ArrayList<String> getCast() {
        return cast;
    }

    /**
     *
     * @return TODO
     */
    public ArrayList<String> getGenres() {
        return genres;
    }

    /**
     *
     * @return todo
     */
    @Override
    public String toString() {
        return "Show{" + "title='" + title + '\'' + '}';
    }
}
