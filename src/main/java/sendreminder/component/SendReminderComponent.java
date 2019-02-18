package sendreminder.component;

import dagger.Component;
import javax.inject.Singleton;
import sendreminder.module.SendReminderModule;
import sendreminder.service.SendReminderService;

@Singleton
@Component(modules = SendReminderModule.class)
public interface SendReminderComponent {
  SendReminderService getSendReminderService();
}
