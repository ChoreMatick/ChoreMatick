package chorematick;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
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

public class ChorematickSpeechletTest {

  @Test
  public void testHelpResponse() throws SpeechletException {
    IntentRequest mockedIntentRequest = mock(IntentRequest.class);
    Session mockedSession = mock(Session.class);
    Intent mockedIntent = mock(Intent.class);

    when(mockedIntentRequest.getIntent()).thenReturn(mockedIntent);
    when(mockedIntent.getName()).thenReturn("AMAZON.HelpIntent");

    ChorematickSpeechlet speechlet = new ChorematickSpeechlet();
    SpeechletResponse response = speechlet.onIntent(mockedIntentRequest, mockedSession);

    assertThat(response.getCard(), nullValue());
    assertThat(response.getReprompt(), nullValue());
    assertThat(((PlainTextOutputSpeech) response.getOutputSpeech()).getText(), equalTo("You can ask me for a chore, by saying, what is my chore?"));


  }
}
