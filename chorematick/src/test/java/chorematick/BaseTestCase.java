package chorematick;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.mockito.Mock;
import org.junit.Before;
import org.mockito.MockitoAnnotations;
import static org.hamcrest.CoreMatchers.*;

public class BaseTestCase {

  @Before public void initMocks() {
    MockitoAnnotations.initMocks(this);
  }
}
