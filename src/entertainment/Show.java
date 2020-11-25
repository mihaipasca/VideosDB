package entertainment;

import fileio.ShowInput;

import java.util.ArrayList;

public abstract class Show {
    private String title;
    private int year;
    private ArrayList<String> cast;
    private ArrayList<String> genres;

    Show(ShowInput showInput) {
        title = showInput.getTitle();
        year = showInput.getYear();
        cast = showInput.getCast();
        genres = showInput.getGenres();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public ArrayList<String> getCast() {
        return cast;
    }

    public void setCast(ArrayList<String> cast) {
        this.cast = cast;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }
}
