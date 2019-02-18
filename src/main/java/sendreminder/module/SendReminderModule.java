package sendreminder.module;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.pinpoint.AmazonPinpoint;
import com.amazonaws.services.pinpoint.AmazonPinpointClientBuilder;
import dagger.Module;
import dagger.Provides;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Singleton;
import sendreminder.dao.TaskDao;

@Module
public class SendReminderModule {

  @Provides
  @Singleton
  AmazonDynamoDB provideAmazonDynamoDB() {
    return AmazonDynamoDBClientBuilder.standard().build();
  }

  @Provides
  @Singleton
  TaskDao provideTaskDao(AmazonDynamoDB amazonDynamoDB) {
    String tableName = System.getenv("tableName");
    Table table = new DynamoDB(amazonDynamoDB).getTable(tableName);
    return new TaskDao(table);
  }

  @Provides
  @Singleton
  AmazonPinpoint provideAmazonPinpoint() {
    return AmazonPinpointClientBuilder.standard().build();
  }

  @Provides
  @Singleton
  Map<String, String> provideNameToPhoneNumber() {
    Map<String, String> nameToPhoneNumber = new HashMap<>();
    nameToPhoneNumber.put("Etai Shuchatowitz", "+17813431806");
    return nameToPhoneNumber;
  }
}
