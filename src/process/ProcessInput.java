package process;

import actions.Command;
import actions.Query;
import actions.Recommendation;
import fileio.ActionInputData;
import fileio.Input;
import fileio.Writer;
import org.json.simple.JSONArray;
import repository.Repo;

import java.io.IOException;

public class ProcessInput {

    public static JSONArray process(Input input, Writer fileWriter) throws IOException {
        Repo repository = new Repo(input);
        String result = "";
        JSONArray resultArray = new JSONArray();
        int count = 1;
        for (ActionInputData action : input.getCommands()) {
            switch (action.getActionType()) {
                case "command" -> result = Command.execute(repository, action);
                case "query" -> result = Query.execute(action);
                case "recommendation" -> result = Recommendation.execute(action);
            }
            // System.out.println("xd1");
            resultArray.add(fileWriter.writeFile(count, "", result));
            count++;
        }
        return resultArray;
    }
}
