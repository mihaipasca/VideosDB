# VideosDB

Github Repository: https://github.com/Alexmihax/VideosDB

The implementation starts with the instantiation of a ProcessInput object which
then processes the input data sent through an Input object. In the process 
method the input data will be stored through the instantiation of a Repo object
that will save each type of entities in lists (User,Movie, Serial, Show, Actor).

After saving the data in the repository the action list will start to be executed
by instantiating the corresponding object for each type of action (Command, Query,
Recommendation) and calling their execute methods. The command is further
interpreted and a helper method is called corresponding to the subtype of the
action that has to be executed called. The helper methods have different purposes,
as the commands add data into the repository, the queries show filtered and sorted 
data from the repository, and the recommendations show different movies and serials
based on recommendation strategies.

The output of the actions will be transmitted from the helper methods as a string
with the result of the action that was executed. If the action couldn't have been
finished, an error message will be transmitted as result. The output string will
be written in an JSONArray to be further transformed into a JSONObject which is
the result of the implementation.