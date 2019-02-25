package sendreminder.dao;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AmazonDynamoDBException;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sendreminder.model.Task;

public class TaskDao {

  private static final Logger LOG = LogManager.getLogger(TaskDao.class);

  private Table table;

  public TaskDao(Table table) {
    this.table = table;
  }

  public List<Task> scanTableForTasks() {
    List<Task> tasks = new ArrayList<>();
    try {
      ItemCollection<ScanOutcome> items =
          table.scan(
              null, // FilterExpression
              "taskNumber, taskName, assignee, done", // ProjectionExpression
              null, // ExpressionAttributeName)s - not used in this example
              null);

      for (Item item : items) {
        String assignee = item.getString("assignee");
        boolean done = item.getBOOL("done");
        int taskNumber = item.getInt("taskNumber");
        String taskName = item.getString("taskName");
        Task task = new Task(taskName, assignee, taskNumber, done);
        tasks.add(task);
        LOG.info("Retrieved " + task);
      }
    } catch (AmazonDynamoDBException e) {
      LOG.error("Error scanning for tasks with message: " + e.getMessage(), e);
    }
    return tasks;
  }

  public void putInTable(Task task) {
    try {
      Item item =
          new Item()
              .withPrimaryKey("taskName", task.getTaskName())
              .withString("assignee", task.getAssignee())
              .withInt("taskNumber", task.getTaskNumber())
              .withBoolean("done", task.isDone());
      table.putItem(item);
      LOG.info("succesfully reuploaded item " + task);
    } catch (AmazonDynamoDBException e) {
      LOG.error("Error saving task " + task + " to dynamo with error " + e.getMessage(), e);
    }
  }
}
