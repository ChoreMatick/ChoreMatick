package chorematick;

import java.time.*;

public class Task {

  private String action;
  private LocalDate date;

  Task(String chore, LocalDate day){
    action = chore;
    date = day;
  }

  public LocalDate getDate(){
    return date;
  }

  public String getAction(){
    return action;
  }
}
