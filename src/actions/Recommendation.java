package actions;

import common.Constants;
import entertainment.Show;
import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONObject;
import repository.Repo;

import java.util.List;

public class Recommendation {

    public static String execute(Repo repository, ActionInputData recommendation) {
        String result;
        switch (recommendation.getType()) {
            case Constants.FAVORITE:
                result = favorite(repository, recommendation);
                break;
            case Constants.STANDARD:
                result = standard(repository, recommendation);
                break;
            case Constants.POPULAR:
                result = popular(repository, recommendation);
                break;
            case Constants.SEARCH:
                result = search(repository, recommendation);
                break;
            case Constants.BEST_UNSEEN:
                result = best_unseen(repository, recommendation);
                break;
            default:
                result = "error -> invalid query";
                break;
        }
        return result;
    }
    public static String standard(Repo repository, ActionInputData recommendation) {
        String result = "";
        return result;
    }
    public static String best_unseen(Repo repository, ActionInputData recommendation) {
        String result = "";
        return result;
    }
    public static String popular(Repo repository, ActionInputData recommendation) {
        String result = "";
        return result;
    }
    public static String search(Repo repository, ActionInputData recommendation) {
        String result = "";
        return result;
    }
    public static String favorite(Repo repository, ActionInputData recommendation) {
        String result = "";
        return result;
    }
}
