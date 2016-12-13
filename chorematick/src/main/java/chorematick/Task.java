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
  private boolean complete = false;
  private String password;

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

  @DynamoDBAttribute(attributeName="Complete")
  public boolean getIsComplete(){
    return complete;
  }

  public void setIsComplete(boolean bool) {
    this.complete = bool;
  }

  @DynamoDBAttribute(attributeName="password")
  public String getPassword(){
    return password;
  }

  public void setPassword(String password) {
    this.password = password;

  }
}
