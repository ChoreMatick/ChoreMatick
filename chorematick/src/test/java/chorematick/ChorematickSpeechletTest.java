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
import com.amazonaws.partitions.PartitionsLoader;

public class ChorematickSpeechletTest extends BaseTestCase {

  private ChorematickSpeechlet speechlet;

  @Mock private IntentRequest mockedIntentRequest;
  @Mock private Session mockedSession;
  @Mock Intent mockedIntent;
  @Mock private SessionStartedRequest mockedSessionStartedRequest;

  @Before
  public void setup() {
    when(mockedIntentRequest.getIntent()).thenReturn(mockedIntent);
    speechlet = new ChorematickSpeechlet();
    speechlet.onSessionStarted(mockedSessionStartedRequest, mockedSession);
  }

  @Test
  public void errorResponseTest() {

    when(mockedIntent.getName()).thenReturn("jibberish");

    SpeechletResponse response = speechlet.onIntent(mockedIntentRequest, mockedSession);

    assertEquals("error error error. Danger Will Robinson.", ((PlainTextOutputSpeech) response.getOutputSpeech()).getText());
  }

  @Test
  public void testHelpResponse() {

    when(mockedIntent.getName()).thenReturn("AMAZON.HelpIntent");

    SpeechletResponse response = speechlet.onIntent(mockedIntentRequest, mockedSession);

    assertThat(response.getCard(), nullValue());
    assertThat(((PlainTextOutputSpeech) response.getOutputSpeech()).getText(), equalTo("You can ask me for a chore, by saying, what is my chore?"));

  }

  // @Test
  // public void testgetChoreResponse() {
  //   when(mockedIntent.getName()).thenReturn("GetChoreIntent");
  //
  //   SpeechletResponse response = speechlet.onIntent(mockedIntentRequest, mockedSession);
  //   SimpleCard card = (SimpleCard) response.getCard();
  //
  //   assertThat(((PlainTextOutputSpeech) response.getOutputSpeech()).getText(), equalTo("Your chore for today is. Sweep the chimney. That's right. Sweep the chimney."));
  //   assertThat(card.getTitle(), equalTo("Chore requested"));
  //   assertThat(card.getContent(), equalTo("Your child just asked for today's chore"));
  // }

  @Test
  public void testDoneResponse() {
    when(mockedIntent.getName()).thenReturn("GetDoneIntent");

    SpeechletResponse response = speechlet.onIntent(mockedIntentRequest, mockedSession);

    assertThat(((PlainTextOutputSpeech) response.getOutputSpeech()).getText(), equalTo("Very well, I have informed your appropriate adult."));
  }


  @Test
  public void testEasterEggResponse() {
    when(mockedIntent.getName()).thenReturn("ChorematickIntent");

    SpeechletResponse response = speechlet.onIntent(mockedIntentRequest, mockedSession);

    assertThat(((PlainTextOutputSpeech) response.getOutputSpeech()).getText(), equalTo("Go stand in the corner and think about what you've done."));
  }

  // @Test
  // public void testChorelist() {
  //   speechlet.getChoreList();
  // }

}
