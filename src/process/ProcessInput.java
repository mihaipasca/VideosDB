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

public final class ProcessInput {
    private ProcessInput() {
    }

    /**
     * Method that initialises the repository and calls the action methods
     * @param input data
     * @param fileWriter for writing the JSONArray
     * @return JSONArray with the result of the executed actions
     * @throws IOException in case of exceptions to writing
     */
    @SuppressWarnings("resultArray")
    public static JSONArray process(final Input input, final Writer fileWriter) throws IOException {
        Repo repository = new Repo(input);
        String result;
        JSONArray resultArray = new JSONArray();
        for (ActionInputData action : input.getCommands()) {
            switch (action.getActionType()) {
                case Constants.COMMAND:
                    Command command = new Command();
                    result = command.execute(repository, action);
                    break;
                case Constants.QUERY:
                    Query query = new Query();
                    result = query.execute(repository, action);
                    break;
                case Constants.RECOMMENDATION:
                    Recommendation recommendation = new Recommendation();
                    result = recommendation.execute(repository, action);
                    break;
                default:
                    result = "error -> Wrong action";
                    break;
            }
            resultArray.add(fileWriter.writeFile(action.getActionId(), "", result));
        }
        return resultArray;
    }
}
