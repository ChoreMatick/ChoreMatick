package chorematick;

import com.amazon.speech.speechlet.*;
import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.SsmlOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;
import com.amazon.speech.ui.StandardCard;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.ScanSpec;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;
import java.time.*;
import java.text.SimpleDateFormat;

public class ChorematickSpeechlet implements Speechlet {

  private final static Logger log = Logger.getLogger(ChorematickSpeechlet.class.getName());

  private  AmazonDynamoDBClient client;

  private Dao dao;

  private CardHandler cardHandler;

  private SpeechHandler speechHandler;
  private RepromptHandler repromptHandler;

  public ChorematickSpeechlet(Dao dao) {
    super();
    this.dao = dao;
    this.cardHandler = new CardHandler();
    this.speechHandler = new SpeechHandler();
    this.repromptHandler = new RepromptHandler();
  }

  public void onSessionStarted(final SessionStartedRequest request, final Session session) {
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
      return getChoreResponse(intent);
    } else if ("GetDoneIntent".equals(intentName)){
      return getDoneResponse(intent);
    } else if ("ConfirmChoreIntent".equals(intentName)){
      return getConfirmChoreResponse(intent);
    } else if ("ChorematickIntent".equals(intentName)) {
      return getEasterEggResponse();
    } else if ("AddChoreIntent".equals(intentName)) {
      return getAddChoreResponse(intent);
    } else if ("GetChoreListIntent".equals(intentName)) {
      return getChoreList();
    } else if ("NumberOfCompletedChoresIntent".equals(intentName)) {
      return getNumberOfCompletedChoresResponse();
    } else if ("AMAZON.HelpIntent".equals(intentName)) {
      return getHelpResponse();
    } else {
      return getErrorResponse();
    }
  }

  public void onSessionEnded(final SessionEndedRequest request, final Session session) {
  }

  private SpeechletResponse getWelcomeResponse() {
    SsmlOutputSpeech speech = speechHandler.getSsmlSpeech("<speak> Welcome to, <phoneme alphabet=\"ipa\" ph=\"tʃɔːrmætɪk\">Chorematic</phoneme>! What would you like to do today? </speak>");

    Reprompt reprompt = repromptHandler.getReprompt("You can say Help for a full list of options");

    if(this.countChoresCompleted() >= 10){
      StandardCard card = cardHandler.getStandardCard("10 chores complete!! \n Suggested gift:","Hasbro NERF Rebelle Diamondista Blaster £4.99","https://images-na.ssl-images-amazon.com/images/I/61miKEYpgSL._SL1000_.jpg");
      return SpeechletResponse.newAskResponse(speech, reprompt, card);
    } else {
      return SpeechletResponse.newAskResponse(speech, reprompt);
    }
  }

  private SpeechletResponse getChoreResponse(Intent intent) {

    String day;

    if (intent.getSlot("choreDate").getValue() == null) {
      Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("EST"));
      SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");

      day = format1.format(cal.getTime()).toString();

    } else {
      day = intent.getSlot("choreDate").getValue();
    }

    List<Task> chores = dao.scanDB("Due", day);

    String s;
    if (chores.size() != 0) {
        Task task = chores.get(0);
        s = "Your chore is. " + task.getChore();
    } else {
        s = "It's your lucky day! you have no assigned chores.";
    }

    PlainTextOutputSpeech speech = speechHandler.getPlainSpeech(s);

    SimpleCard card = cardHandler.getSimpleCard("Chore requested", "Your child just asked for today's chore");

    return SpeechletResponse.newTellResponse(speech, card);
  }

  private SpeechletResponse getDoneResponse(Intent intent) {
    // Should we refactor this to have default values and so forth?
    String day = intent.getSlot("choreDate").getValue();
    String chore = intent.getSlot("chore").getValue();

    Task task = this.dao.loadFromDB(day, chore);

    SimpleCard card = cardHandler.getSimpleCard("Chore Verification",("Your child claims to have completed their chore. Here is the password: " + task.getPassword()));

    PlainTextOutputSpeech speech = speechHandler.getPlainSpeech("Very well, I have informed your appropriate adult.");

    return SpeechletResponse.newTellResponse(speech, card);
  }

  public SpeechletResponse getChoreList() {

    List<Task> chores =  this.dao.getAllChores();

    String result = "";

    for(Task task : chores) {
      result = result + task.getChore() + ", ";
    }
    return SpeechletResponse.newTellResponse(speechHandler.getPlainSpeech(result));
  }

  private SpeechletResponse getAddChoreResponse(Intent intent){

    Random random = new Random();


    String day = intent.getSlot("choreDate").getValue();
    String chore = intent.getSlot("chore").getValue();
    String password = String.format("%04d", random.nextInt(10000));

    Task task = new Task();
    task.setDate(day);
    task.setChore(chore);
    task.setPassword(password);
    this.dao.saveToDB(task);

    PlainTextOutputSpeech speech = speechHandler.getPlainSpeech(("Very well, I have added a " + chore + " chore for " + day));

    SimpleCard card = cardHandler.getSimpleCard("New chore added",(day + " " + chore + "\nPassword: " + password));

    return SpeechletResponse.newTellResponse(speech, card);
  }

  private SpeechletResponse getHelpResponse() {

    PlainTextOutputSpeech speech = speechHandler.getPlainSpeech("You can tell me to add a chore; you can ask me for today's chore; tell me that you've finished your chore; confirm a password; ask me for a list of chores; or ask me for the number of completed chores");

    Reprompt reprompt = repromptHandler.getReprompt("Tell me what you would like to do!");

    SimpleCard card = cardHandler.getSimpleCard("ChoreMatick Tips!", "You can tell me to add a chore by saying, for example, 'Add mow the lawn for Tuesday'. \n  You can ask me 'What is my chore for today?'. \n You can tell me that you have finished the chore by saying 'I am done with mow the lawn for today'. \n You can confirm that your child has completed their chore by providing the given password e.g 'Confirm 1234'. \n  You can also ask me for a 'Full list of chores', and 'The total number of chores that are completed'." );

    return SpeechletResponse.newAskResponse(speech, reprompt, card);
  }

  private SpeechletResponse getErrorResponse() {
    PlainTextOutputSpeech speech = speechHandler.getPlainSpeech("error error error. Danger Will Robinson.");
    return SpeechletResponse.newTellResponse(speech);
  }

  private SpeechletResponse getConfirmChoreResponse(Intent intent) {


    String password = intent.getSlot("password").getValue();

    List<Task> chores = this.dao.scanDB("password", password);


    String s;
    if (chores.size() > 0) {
      Task task = chores.get(0);
      task.setIsComplete(true);
      this.dao.saveToDB(task);
      s = "I've confirmed "+ task.getDate() + " " + task.getChore() +" chore is completed.";
    } else {
      s = "Unable to confirm password, please try again.";
    }

    PlainTextOutputSpeech speech = speechHandler.getPlainSpeech(s);

    Reprompt reprompt = repromptHandler.getReprompt("Please state the password for the chore you wish to confirm");

    SimpleCard card = cardHandler.getSimpleCard("Chore Completed!","Thank you for confiming your child has completed their chore; the list has been updated");

    return SpeechletResponse.newAskResponse(speech, reprompt, card);
  }

  private SpeechletResponse getNumberOfCompletedChoresResponse(){

    int number = countChoresCompleted();

    PlainTextOutputSpeech speech = speechHandler.getPlainSpeech(("There are " + number + " completed chores."));
    return SpeechletResponse.newTellResponse(speech);
  }

  private SpeechletResponse getEasterEggResponse() {
    PlainTextOutputSpeech speech = speechHandler.getPlainSpeech(("Go stand in the corner and think about what you've done."));
    return SpeechletResponse.newTellResponse(speech);
  }

  private int countChoresCompleted(){

    List<Task> completedChores =  dao.scanDB("Complete", "1");

    return completedChores.size();
  }
}
