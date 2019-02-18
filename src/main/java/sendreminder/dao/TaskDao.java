package sendreminder.dao;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import java.util.ArrayList;
import java.util.List;
import sendreminder.model.Task;

public class TaskDao {

  //    private static final Gson gson = new Gson();

  private Table table;

  public TaskDao(Table table) {
    this.table = table;
  }

  public List<Task> scanTableForTasks() {
    ItemCollection<ScanOutcome> items =
        table.scan(
            null, // FilterExpression
            "index, task, assignee, done", // ProjectionExpression
            null, // ExpressionAttributeName)s - not used in this example
            null);
    List<Task> tasks = new ArrayList<>();
    for (Item item : items) {
      String assignee = item.getString("assignee");
      boolean done = item.getBOOL("done");
      int index = item.getInt("index");
      String taskName = item.getString("task");
      Task task = new Task(taskName, assignee, index, done);
      tasks.add(task);
    }
    return tasks;
  }
}
