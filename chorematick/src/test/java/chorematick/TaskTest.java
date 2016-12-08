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
  private LocalDate day = LocalDate.parse("2016-10-10");

  @Before
  public void setup() {
    task = new Task("Sweep the chimney", day);
  }

  @Test
  public void getDateTest() {
    assertEqual(day, task.getDate());
  }

  @Test
  public void getAction() {
    assertEqual("Sweep the chimney", task.getAction());
  }
}
