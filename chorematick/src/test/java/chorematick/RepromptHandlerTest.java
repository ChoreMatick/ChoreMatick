package chorematick;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import com.amazon.speech.ui.PlainTextOutputSpeech;


public class RepromptHandlerTest extends BaseTestCase {

  private RepromptHandler repromptHandler;

  @Before
  public void setup() {
    repromptHandler = new RepromptHandler();
  }

  @Test
  public void getRepromptTest(){
    assertEquals(((PlainTextOutputSpeech)repromptHandler.getReprompt("You there?").getOutputSpeech()).getText(),"You there?");
  }
}
