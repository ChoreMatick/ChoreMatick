package chorematick;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.mockito.Mock;
import org.junit.Before;
import org.mockito.MockitoAnnotations;
import java.time.*;

public class TaskTest extends BaseTestCase {

  private Task task;
  private String day = "2016-10-10";

  @Before
  public void setup() {
    task = new Task();
    task.setDate(day);
    task.setChore("Sweep the chimney");
    task.setPassword("1234");
  }

  @Test
  public void getDateTest() {
    assertEquals(day, task.getDate());
  }

  @Test
  public void getChoreTest() {
    assertEquals("Sweep the chimney", task.getChore());
  }

  @Test
  public void getIsCompleteTest() {
    assertEquals(false, task.getIsComplete());
  }

  @Test
  public void setIsCompleteTest() {
    task.setIsComplete(true);
    assertEquals(true, task.getIsComplete());
  }

  @Test
  public void getPasswordTest() {
    assertEquals("1234", task.getPassword());
  }
}
