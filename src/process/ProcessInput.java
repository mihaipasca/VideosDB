package process;

import actions.Command;
import actions.Query;
import actions.Recommendation;
import common.Constants;
import fileio.ActionInputData;
import fileio.Input;
import fileio.Writer;
import org.json.simple.JSONArray;
import repository.Repo;

import java.io.IOException;

public class ProcessInput {

    public static JSONArray process(Input input, Writer fileWriter) throws IOException {
        Repo repository = new Repo(input);
        String result;
        JSONArray resultArray = new JSONArray();
        int count = 1;
        for (ActionInputData action : input.getCommands()) {
            switch (action.getActionType()) {
                case Constants.COMMAND -> result = Command.execute(repository, action);
                case Constants.QUERY -> result = Query.execute(repository, action);
                case Constants.RECOMMENDATION -> result = Recommendation.execute(repository, action);
                default -> result = "error -> Wrong action";
            }
            resultArray.add(fileWriter.writeFile(count, "", result));
            count++;
        }
        return resultArray;
    }
}
