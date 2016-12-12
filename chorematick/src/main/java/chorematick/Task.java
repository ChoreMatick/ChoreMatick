package chorematick;

import java.time.*;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "Tasks")
public class Task {

  private String chore;
  private String day;

  @DynamoDBHashKey(attributeName="Due")
  public String getDate(){
    return day;
  }
  public void setDate(String day) {
    this.day = day;
  }

  @DynamoDBRangeKey(attributeName="Chore")
  public String getChore(){
    return chore;
  }

  public void setChore(String chore) {
    this.chore = chore;
  }
}
