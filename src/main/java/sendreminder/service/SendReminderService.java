package sendreminder.service;

import com.amazonaws.services.pinpoint.AmazonPinpoint;
import com.amazonaws.services.pinpoint.model.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import sendreminder.dao.TaskDao;
import sendreminder.model.Task;

public class SendReminderService {

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
    }
  }

  public List<Task> scrambleTasks(List<Task> tasks) {
    Map<String, Integer> nameToIndex = new HashMap<>();
    Map<Integer, String> indexToTaskName = new HashMap<>();

    List<Task> updatedTasks = new ArrayList<>();

    for (Task task : tasks) {
      indexToTaskName.put(task.getIndex(), task.getTask());
      nameToIndex.put(task.getAssignee(), task.getIndex());
    }

    for (Map.Entry<String, Integer> entry : nameToIndex.entrySet()) {
      int updatedIndex = (entry.getValue() + 1) % 4;
      String taskName = indexToTaskName.get(updatedIndex);
      updatedTasks.add(new Task(taskName, entry.getKey(), updatedIndex, false));
    }

    return updatedTasks;
  }

  public void sendText(Task task) {
    String phoneNumber = nameToPhoneNumber.get(task.getAssignee());
    AddressConfiguration addressConfiguration = new AddressConfiguration();
    addressConfiguration.setChannelType(ChannelType.SMS);
    Map<String, AddressConfiguration> addresses = new HashMap<>();
    addresses.put(phoneNumber, addressConfiguration);

    SMSMessage smsMessage = new SMSMessage();
    smsMessage.setBody(task.getTask());
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

    amazonPinpoint.sendMessages(sendMessageRequest);
  }
}
