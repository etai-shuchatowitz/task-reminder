package sendreminder;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import sendreminder.component.DaggerSendReminderComponent;
import sendreminder.component.SendReminderComponent;
import sendreminder.service.SendReminderService;

public class SendRemindersLambda implements RequestStreamHandler {

  private SendReminderComponent sendReminderComponent;

  public SendRemindersLambda() {
    sendReminderComponent = DaggerSendReminderComponent.builder().build();
  }

  @Override
  public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context)
      throws IOException {
    SendReminderService sendReminderService = sendReminderComponent.getSendReminderService();
    sendReminderService.sendTexts();
  }
}
