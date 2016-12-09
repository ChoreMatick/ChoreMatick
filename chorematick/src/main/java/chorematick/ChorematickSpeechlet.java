/**
Copyright 2014-2015 Amazon.com, Inc. or its affiliates. All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in compliance with the License. A copy of the License is located at

http://aws.amazon.com/apache2.0/

or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
*/
package chorematick;

import com.amazon.speech.speechlet.*;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;


import java.util.logging.Logger;


public class ChorematickSpeechlet implements Speechlet {

  private final static Logger log = Logger.getLogger(ChorematickSpeechlet.class.getName());


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


    System.out.println(intentName);
    log.info(intentName);


    if ("ChorematickIntent".equals(intentName)) {
      return getWelcomeResponse();
    } else if ("GetDoneIntent".equals(intentName)){
      return getDoneResponse();
    } else if ("DateIntent".equals(intentName)){
      return getDateResponse(intent);
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
    return SpeechletResponse.newTellResponse(speech);
  }

  private SpeechletResponse getErrorResponse() {
    String speechText = "error error error";
    PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
    speech.setText(speechText);
    return SpeechletResponse.newTellResponse(speech);
  }
  private SpeechletResponse getDateResponse(Intent intent) {
    PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
    // Slot choreDaySlot = intent.getSlot();
    speech.setText("Tell me the date");
    // return SpeechletResponse.newTellResponse(speech);
    System.out.println(intent);
    return SpeechletResponse.newTellResponse(speech);
  }
}
