package entertainment;

import fileio.MovieInputData;

import java.util.ArrayList;
import java.util.List;

public class Movie extends Show{
    private int duration;
    private ArrayList<Double> ratings;
    public Movie(MovieInputData movieInput) {
        super(movieInput);
        duration = movieInput.getDuration();
        ratings = new ArrayList<>();
    }

    public Double getRating(){
        Double ratingSum = 0.0;
        if (ratings.isEmpty()) {
            return null;
        }
        for(Double number : ratings) {
            ratingSum += number;
        }
        return ratingSum / ratings.size();
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public ArrayList<Double> getRatings() {
        return ratings;
    }

    public void setRatings(ArrayList<Double> ratings) {
        this.ratings = ratings;
    }
}
