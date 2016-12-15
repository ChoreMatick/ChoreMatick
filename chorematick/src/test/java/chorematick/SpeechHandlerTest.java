package chorematick;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class SpeechHandlerTest extends BaseTestCase {

  private SpeechHandler speechHandler;

  @Before
  public void setup() {
    speechHandler = new SpeechHandler();
  }

  @Test
  public void getSsmlSpeechTest(){
    assertEquals(speechHandler.getSsmlSpeech("<speak> Welcome to </speak>").getSsml(),"<speak> Welcome to </speak>");
  }
  @Test
  public void getPlainSpeechTest(){
    assertEquals(speechHandler.getPlainSpeech("Hello world").getText(),"Hello world");
  }
}
