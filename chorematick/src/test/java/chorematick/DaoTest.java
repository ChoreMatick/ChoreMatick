package chorematick;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class DaoTest extends BaseTestCase {

  private Dao dao;

  @Mock private DynamoDBMapper mockedMapper;
  @Mock private PaginatedScanList<Task> mockedPaginatedScanList;

  @Before
  public void setup() {
    dao = new Dao(mockedMapper);
  }

  @Test
  public void scanTest() {
    when(mockedMapper.scan(eq(Task.class), any(DynamoDBScanExpression.class))).thenReturn(mockedPaginatedScanList);
    dao.scanDB("Due", "2016-12-15");
    verify(mockedMapper).scan(eq(Task.class),any(DynamoDBScanExpression.class));
  }
}
