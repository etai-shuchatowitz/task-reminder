package sendreminder.service;

import com.amazonaws.services.pinpoint.AmazonPinpoint;
import com.amazonaws.services.pinpoint.model.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sendreminder.dao.TaskDao;
import sendreminder.model.Task;

public class SendReminderService {

  private static final Logger LOG = LogManager.getLogger(SendReminderService.class);

  private TaskDao taskDao;
  private Map<String, String> nameToPhoneNumber;
  private AmazonPinpoint amazonPinpoint;

  @Inject
  public SendReminderService(
      TaskDao taskDao, Map<String, String> nameToPhoneNumber, AmazonPinpoint amazonPinpoint) {
    this.taskDao = taskDao;
    this.nameToPhoneNumber = nameToPhoneNumber;
    this.amazonPinpoint = amazonPinpoint;
  }

  public void sendTexts() {
    List<Task> taskList = taskDao.scanTableForTasks();
    List<Task> updatedTasks = scrambleTasks(taskList);
    for (Task task : updatedTasks) {
      sendText(task);
      taskDao.putInTable(task);
      // TODO: reupload to table
    }
  }

  List<Task> scrambleTasks(List<Task> tasks) {

    List<Task> updatedTasks = new ArrayList<>();

    for (Task task : tasks) {
      int updatedTaskNumber = (task.getTaskNumber() + 1) % tasks.size();
      updatedTasks.add(new Task(task.getTaskName(), task.getAssignee(), updatedTaskNumber, false));
    }

    return updatedTasks;
  }

  public void sendText(Task task) {
    try {
      String phoneNumber = nameToPhoneNumber.get(task.getAssignee());
      AddressConfiguration addressConfiguration = new AddressConfiguration();
      addressConfiguration.setChannelType(ChannelType.SMS);
      Map<String, AddressConfiguration> addresses = new HashMap<>();
      addresses.put(phoneNumber, addressConfiguration);

      SMSMessage smsMessage = new SMSMessage();
      smsMessage.setBody(task.getTaskName());
      smsMessage.setMessageType(MessageType.TRANSACTIONAL);

      DirectMessageConfiguration directMessageConfiguration = new DirectMessageConfiguration();
      directMessageConfiguration.setSMSMessage(smsMessage);
      MessageRequest messageRequest = new MessageRequest();
      messageRequest.setAddresses(addresses);
      messageRequest.setMessageConfiguration(directMessageConfiguration);

      SendMessagesRequest sendMessageRequest =
          new SendMessagesRequest()
              .withApplicationId("6987786d73a44f83ad5d320bb456e9ce")
              .withMessageRequest(messageRequest);

      SendMessagesResult result = amazonPinpoint.sendMessages(sendMessageRequest);
      LOG.info(
          "Successfully sent message for task "
              + task
              + " with message "
              + result.getMessageResponse().toString());

    } catch (AmazonPinpointException e) {
      LOG.error("Error sending text for task: " + task + " with message " + e.getMessage(), e);
    }
  }
}
