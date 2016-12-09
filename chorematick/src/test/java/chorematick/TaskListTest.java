package chorematick;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.mockito.Mock;
import org.junit.Before;
import org.mockito.MockitoAnnotations;
import static org.hamcrest.CoreMatchers.*;
import java.util.ArrayList;
import java.time.*;

public class TaskListTest extends BaseTestCase {

  private TaskList taskList;
  private LocalDate day = LocalDate.parse("2016-10-10");

  @Mock private Task mockedTask;

  @Before
  public void setup() {
    taskList = new TaskList();
    when(mockedTask.getDate()).thenReturn(day);
  }

  @Test
  public void getTasksTest(){
    assertThat(taskList.getTasks(), is(instanceOf(ArrayList.class)));
  }

  @Test
  public void addTaskTest(){
    taskList.add(mockedTask);
    assertTrue(taskList.getTasks().contains(mockedTask));
  }

  @Test
  public void getDaysTaskTest() {
    taskList.add(mockedTask);
    when(mockedTask.getDate()).thenReturn(day);
    assertThat(taskList.getDaysTask(day), equalTo(mockedTask));
  }

  @Test (expected = RuntimeException.class)
  public void getDaysTaskExceptionTest() {
    taskList.getDaysTask(LocalDate.parse("2016-12-10"));
  }

}
