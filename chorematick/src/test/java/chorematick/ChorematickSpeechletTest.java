package chorematick;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
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
import com.amazon.speech.ui.StandardCard;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
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

public class ChorematickSpeechletTest extends BaseTestCase {

  private ChorematickSpeechlet speechlet;

  @Mock private IntentRequest mockedIntentRequest;
  @Mock private LaunchRequest mockedLaunchRequest;
  @Mock private Session mockedSession;
  @Mock private Intent mockedIntent;
  @Mock private SessionStartedRequest mockedSessionStartedRequest;
  @Mock private Slot mockedDateSlot;
  @Mock private Slot mockedChoreSlot;
  @Mock private Slot mockedPasswordSlot;
  @Mock private DynamoDBMapper mockedMapper;
  @Mock private Task mockedTask;
  @Mock private PaginatedScanList<Task> mockedPaginatedScanList;
  @Mock private DynamoDBScanExpression mockedExpression;

  @Before
  public void setup() {
    when(mockedIntentRequest.getIntent()).thenReturn(mockedIntent);
    speechlet = new ChorematickSpeechlet(mockedMapper);
    speechlet.onSessionStarted(mockedSessionStartedRequest, mockedSession);
  }

  @Test
  public void WelcomeResponseTest() {
    when(mockedMapper.scan(eq(Task.class), any(DynamoDBScanExpression.class))).thenReturn(mockedPaginatedScanList);
    when(mockedPaginatedScanList.size()).thenReturn(5);

    SpeechletResponse response = speechlet.onLaunch(mockedLaunchRequest, mockedSession);

    assertEquals("Hello child, Would you like to hear your chore for today, or tell me you have completed your chore", ((PlainTextOutputSpeech) response.getOutputSpeech()).getText());
  }

  @Test
  public void testGiftSuggestion(){
    when(mockedMapper.scan(eq(Task.class), any(DynamoDBScanExpression.class))).thenReturn(mockedPaginatedScanList);
    when(mockedPaginatedScanList.size()).thenReturn(11);

    SpeechletResponse response = speechlet.onLaunch(mockedLaunchRequest, mockedSession);

    assertEquals("Hello child, Would you like to hear your chore for today, or tell me you have completed your chore", ((PlainTextOutputSpeech) response.getOutputSpeech()).getText());

    StandardCard card = (StandardCard) response.getCard();

    assertThat(card.getTitle(), equalTo("10 chores complete!! \n Suggested gift:"));
    assertThat(card.getText(), equalTo("Hasbro NERF Rebelle Diamondista Blaster £4.99"));
    assertThat(card.getImage().getSmallImageUrl(), equalTo("https://images-na.ssl-images-amazon.com/images/I/61miKEYpgSL._SL1000_.jpg"));
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

  @Test
  public void testgetChoreResponse() {
    when(mockedIntent.getName()).thenReturn("GetChoreIntent");

    SpeechletResponse response = speechlet.onIntent(mockedIntentRequest, mockedSession);
    SimpleCard card = (SimpleCard) response.getCard();

    assertThat(((PlainTextOutputSpeech) response.getOutputSpeech()).getText(), equalTo("Your chore for today is. Sweep the chimney. That's right. Sweep the chimney."));
    assertThat(card.getTitle(), equalTo("Chore requested"));
    assertThat(card.getContent(), equalTo("Your child just asked for today's chore"));
  }

  @Test
  public void testConfirmChoreResponse(){
    when(mockedIntent.getName()).thenReturn("ConfirmChoreIntent");
    when(mockedIntent.getSlot("choreDate")).thenReturn(mockedDateSlot);
    when(mockedDateSlot.getValue()).thenReturn("12-12-2016");
    when(mockedIntent.getSlot("chore")).thenReturn(mockedChoreSlot);
    when(mockedChoreSlot.getValue()).thenReturn("Shear the sheep");
    when(mockedMapper.load(Task.class, "12-12-2016", "Shear the sheep")).thenReturn(mockedTask);

    SpeechletResponse response = speechlet.onIntent(mockedIntentRequest, mockedSession);

    verify(mockedMapper).load(Task.class, "12-12-2016", "Shear the sheep");
    verify(mockedMapper).save(any(Task.class));
    verify(mockedTask).setIsComplete(true);
    assertThat(((PlainTextOutputSpeech) response.getOutputSpeech()).getText(), equalTo("I've confirmed 12-12-2016 Shear the sheep chore is completed."));
  }

  @Test
  public void testAddChoreResponse(){
    when(mockedIntent.getName()).thenReturn("AddChoreIntent");
    when(mockedIntent.getSlot("choreDate")).thenReturn(mockedDateSlot);
    when(mockedDateSlot.getValue()).thenReturn("02-03-2016");
    when(mockedIntent.getSlot("chore")).thenReturn(mockedChoreSlot);
    when(mockedChoreSlot.getValue()).thenReturn("Shear the sheep");

    SpeechletResponse response = speechlet.onIntent(mockedIntentRequest, mockedSession);
    SimpleCard card = (SimpleCard) response.getCard();
    verify(mockedMapper).save(any(Task.class));
    assertThat(((PlainTextOutputSpeech) response.getOutputSpeech()).getText(), equalTo("Very well, I have added a Shear the sheep chore for 02-03-2016"));
    assertThat(card.getTitle(), equalTo("02-03-2016 " + "Shear the sheep"));
  }

  // @Test
  // public void testDoneResponse() {
  //   when(mockedIntent.getName()).thenReturn("GetDoneIntent");
  //
  //   SpeechletResponse response = speechlet.onIntent(mockedIntentRequest, mockedSession);
  //
  //   assertThat(((PlainTextOutputSpeech) response.getOutputSpeech()).getText(), equalTo("Very well, I have informed your appropriate adult."));
  // }

  // @Test
  // public void testGetChoreList(){
  //   when(mockedIntent.getName()).thenReturn("GetChoreListIntent");
  //   when(mockedIntent.getSlot("choreDate")).thenReturn(mockedDateSlot);
  //   when(mockedDateSlot.getValue()).thenReturn("02-03-2016");
  //   when(mockedIntent.getSlot("chore")).thenReturn(mockedChoreSlot);
  //   when(mockedChoreSlot.getValue()).thenReturn("Shear the sheep");
  //
  //   SpeechletResponse response = speechlet.onIntent(mockedIntentRequest, mockedSession);
  //
  //   verify(mockedMapper).scan(Task.class, mockedExpression);
  //   assertThat(((PlainTextOutputSpeech) response.getOutputSpeech()).getText(), equalTo("Clean the gutters"));
  // }


  @Test
  public void testEasterEggResponse() {
    when(mockedIntent.getName()).thenReturn("ChorematickIntent");


    SpeechletResponse response = speechlet.onIntent(mockedIntentRequest, mockedSession);

    assertThat(((PlainTextOutputSpeech) response.getOutputSpeech()).getText(), equalTo("Go stand in the corner and think about what you've done."));
  }


  @Test
  public void testGetNumberOfCompletedChoresResponse() {
    when(mockedIntent.getName()).thenReturn("NumberOfCompletedChoresIntent");
    when(mockedMapper.scan(eq(Task.class), any(DynamoDBScanExpression.class))).thenReturn(mockedPaginatedScanList);
    when(mockedPaginatedScanList.size()).thenReturn(5);

    SpeechletResponse response = speechlet.onIntent(mockedIntentRequest, mockedSession);

    verify(mockedMapper).scan(eq(Task.class),any(DynamoDBScanExpression.class));
    verify(mockedPaginatedScanList).size();

    assertThat(((PlainTextOutputSpeech) response.getOutputSpeech()).getText(), equalTo("There are 5 completed chores."));
  }
}
