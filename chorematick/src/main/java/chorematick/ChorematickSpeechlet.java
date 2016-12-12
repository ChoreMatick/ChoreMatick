package chorematick;

import com.amazon.speech.speechlet.*;
import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;

import java.util.logging.Logger;
import java.util.Calendar;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.regions.Regions;
import java.time.*;



public class ChorematickSpeechlet implements Speechlet {

  private final static Logger log = Logger.getLogger(ChorematickSpeechlet.class.getName());

  private  AmazonDynamoDBClient client;

  private DynamoDBMapper mapper;

  public void onSessionStarted(final SessionStartedRequest request, final Session session) {
    this.client = new AmazonDynamoDBClient();
    this.mapper = new DynamoDBMapper(client);
  }

  @Override
  public SpeechletResponse onLaunch(final LaunchRequest request, final Session session) {
    return getWelcomeResponse();
  }

  @Override
  public SpeechletResponse onIntent(final IntentRequest request, final Session session) {

    Intent intent = request.getIntent();
    String intentName = (intent != null) ? intent.getName() : null;


    if ("GetChoreIntent".equals(intentName)) {
      return getChoreResponse();
    } else if ("GetDoneIntent".equals(intentName)){
      return getDoneResponse();
    } else if ("ChorematickIntent".equals(intentName)) {
      return getEasterEggResponse();
    } else if ("AMAZON.HelpIntent".equals(intentName)) {
      return getHelpResponse();
    } else {
      return getErrorResponse();
    }
  }

  public void onSessionEnded(final SessionEndedRequest request, final Session session) {
  }

  private SpeechletResponse getWelcomeResponse() {
    String speechText = "Hello child, Would you like to hear your chore for today, or tell me you have completed your chore";

    PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
    speech.setText(speechText);

    Reprompt reprompt = new Reprompt();
    reprompt.setOutputSpeech(speech);

    return SpeechletResponse.newAskResponse(speech, reprompt);
  }

  private SpeechletResponse getChoreResponse() {

    Task task = new Task();
    task.setChore("Sweep the chimney");
    task.setDate("2016-10-10");
    this.mapper.save(task);

    String speechText = "Your chore for today is. Sweep the chimney. That's right. Sweep the chimney.";
    PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
    speech.setText(speechText);

    SimpleCard card = new SimpleCard();
    card.setTitle("Chore requested");
    card.setContent("Your child just asked for today's chore");
    return SpeechletResponse.newTellResponse(speech, card);
  }

  private SpeechletResponse getDoneResponse() {
    String speechText = "Very well, I have informed your appropriate adult.";

    SimpleCard card = new SimpleCard();
    card.setTitle("Chore Verification");
    card.setContent("Your child claims to have completed their chore, please check and verify");

    PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
    speech.setText(speechText);

    return SpeechletResponse.newTellResponse(speech, card);
  }

  private SpeechletResponse getHelpResponse() {

    PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
    speech.setText("You can ask me for a chore, by saying, what is my chore?");

    Reprompt reprompt = new Reprompt();
    PlainTextOutputSpeech repromptSpeech = new PlainTextOutputSpeech();
    repromptSpeech.setText("Would you like your chore?");
    reprompt.setOutputSpeech(repromptSpeech);

    return SpeechletResponse.newAskResponse(speech, reprompt);
  }

  private SpeechletResponse getErrorResponse() {
    String speechText = "error error error";
    PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
    speech.setText(speechText);
    return SpeechletResponse.newTellResponse(speech);
  }

  public SpeechletResponse getDateResponse(Intent intent) {

    PlainTextOutputSpeech speech = new PlainTextOutputSpeech();

    Slot daySlot = intent.getSlot("choreDate");
    String day = daySlot.getValue();
    speech.setText(day);

    return SpeechletResponse.newTellResponse(speech);
  }

  private SpeechletResponse getEasterEggResponse() {
    PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
    speech.setText("Go stand in the corner and think about what you've done.");
    return SpeechletResponse.newTellResponse(speech);
  }

}
