package sendreminder.service;

import com.amazonaws.services.pinpoint.AmazonPinpoint;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import sendreminder.dao.TaskDao;
import sendreminder.model.Task;

public class SendReminderServiceTest {

  private SendReminderService sendReminderService;

  @Before
  public void setup() {
    Map<String, String> nameToPhoneNumber = new HashMap<>();
    nameToPhoneNumber.put("Etai Shuchatowitz", "+17813431806");
    TaskDao taskDao = Mockito.mock(TaskDao.class);
    AmazonPinpoint amazonPinpoint = Mockito.mock(AmazonPinpoint.class);
    sendReminderService = new SendReminderService(taskDao, nameToPhoneNumber, amazonPinpoint);
  }

  @Test
  public void testScrambleTasks() {
    Task task = new Task("Test", "Etai Shuchatowitz", 0, false);
    List<Task> tasks = new ArrayList<>();
    tasks.add(task);
    List<Task> scrambledTasks = sendReminderService.scrambleTasks(tasks);

    for (Task t : scrambledTasks) {
      System.out.println(t);
    }
  }
}
