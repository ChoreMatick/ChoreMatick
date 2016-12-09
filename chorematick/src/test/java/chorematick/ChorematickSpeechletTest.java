package chorematick;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.mockito.Mock;
import org.junit.Before;
import org.mockito.MockitoAnnotations;
import static org.hamcrest.CoreMatchers.*;
import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SessionEndedRequest;
import com.amazon.speech.speechlet.SessionStartedRequest;
import com.amazon.speech.speechlet.Speechlet;
import com.amazon.speech.speechlet.SpeechletException;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;

public class ChorematickSpeechletTest extends BaseTestCase {

  @Mock private IntentRequest mockedIntentRequest;
  @Mock private Session mockedSession;
  @Mock Intent mockedIntent;

  @Test
  public void errorResponseTest() {

    when(mockedIntentRequest.getIntent()).thenReturn(mockedIntent);
    when(mockedIntent.getName()).thenReturn("jibberish");

    ChorematickSpeechlet speechlet = new ChorematickSpeechlet();
    SpeechletResponse response = speechlet.onIntent(mockedIntentRequest, mockedSession);

    assertEquals("error error error", ((PlainTextOutputSpeech) response.getOutputSpeech()).getText());
  }

  @Test
  public void testHelpResponse() throws SpeechletException {

    when(mockedIntentRequest.getIntent()).thenReturn(mockedIntent);
    when(mockedIntent.getName()).thenReturn("AMAZON.HelpIntent");

    ChorematickSpeechlet speechlet = new ChorematickSpeechlet();
    SpeechletResponse response = speechlet.onIntent(mockedIntentRequest, mockedSession);

    assertThat(response.getCard(), nullValue());
    assertThat(((PlainTextOutputSpeech) response.getOutputSpeech()).getText(), equalTo("You can ask me for a chore, by saying, what is my chore?"));

  }

  @Test
  public void testDoneResponse() {

    when(mockedIntentRequest.getIntent()).thenReturn(mockedIntent);
    when(mockedIntent.getName()).thenReturn("GetDoneIntent");

    ChorematickSpeechlet speechlet = new ChorematickSpeechlet();
    SpeechletResponse response = speechlet.onIntent(mockedIntentRequest, mockedSession);

    assertThat(((PlainTextOutputSpeech) response.getOutputSpeech()).getText(), equalTo("Very well, I have informed your appropriate adult."));
  }

  // @Test
  // public void testDateResponse() {
  //   when(mockedIntentRequest.getIntent()).thenReturn(mockedIntent);
  //   when(mockedIntent.getName()).thenReturn("DateIntent");
  //
  //   ChorematickSpeechlet speechlet = new ChorematickSpeechlet();
  //   SpeechletResponse response = speechlet.onIntent(mockedIntentRequest, mockedSession);
  //
  //   assertEquals("tell me the date", ((PlainTextOutputSpeech) response.getOutputSpeech()).getText());
  //
  // }
}
