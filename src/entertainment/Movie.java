package entertainment;

import fileio.MovieInputData;

import java.util.ArrayList;

public class Movie extends Show {
    private final int duration;
    private final ArrayList<Double> ratings;

    public Movie(final MovieInputData movieInput) {
        super(movieInput);
        duration = movieInput.getDuration();
        ratings = new ArrayList<>();
    }

    /**
     *
     * @return todo
     */
    public Double getRating() {
        Double ratingSum = 0.0;
        if (ratings.isEmpty()) {
            return null;
        }
        for (Double number : ratings) {
            ratingSum += number;
        }
        return ratingSum / ratings.size();
    }

    /**
     *
     * @return todo
     */
    public int getDuration() {
        return duration;
    }

    /**
     *
     * @return todo
     */
    public ArrayList<Double> getRatings() {
        return ratings;
    }
}
