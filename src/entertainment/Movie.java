package entertainment;

import fileio.MovieInputData;

import java.util.ArrayList;

public final class Movie extends Show {
    /**
     * Duration in minutes of a season
     */
    private final int duration;
    /**
     * Ratings of the movie
     */
    private final ArrayList<Double> ratings;

    public Movie(final MovieInputData movieInput) {
        super(movieInput);
        duration = movieInput.getDuration();
        ratings = new ArrayList<>();
    }

    /**
     * Method that computes the average rating of a movie
     * @return Double
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
     * Method that returns the duration of a movie
     * @return duration in minutes
     */
    public int getDuration() {
        return duration;
    }

    public ArrayList<Double> getRatings() {
        return ratings;
    }
}
