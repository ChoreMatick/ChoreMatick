package chorematick;

import java.util.ArrayList;
import java.time.*;

public class TaskList {

  private ArrayList<Task> tasks = new ArrayList<Task>();

  public ArrayList getTasks(){
    return tasks;
  }

  public void add(Task task){
    tasks.add(task);
  }

  public Task getDaysTask(String day) {
    for(Task t : tasks){
      if (t.getDate() == day){
        return t;
      }
    }
    throw new RuntimeException("No tasks for the given day.");
  }
}
