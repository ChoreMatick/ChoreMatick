/**
Copyright 2014-2015 Amazon.com, Inc. or its affiliates. All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in compliance with the License. A copy of the License is located at

http://aws.amazon.com/apache2.0/

or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
*/
package chorematick;

import com.amazon.speech.speechlet.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;

public class ChorematickSpeechlet implements Speechlet {

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

    if ("ChorematickIntent".equals(intentName)) {
      return getHelloResponse();
    } else if ("AMAZON.HelpIntent".equals(intentName)) {
      return getHelpResponse();
    } else {
      return getErrorResponse();
    }
  }

  public void onSessionEnded(final SessionEndedRequest request, final Session session) {
  }

  private SpeechletResponse getWelcomeResponse() {
    String speechText = "Hello child, your chore for today is. Sweep the chimney. thats right. Sweep the chimney";

    SimpleCard card = new SimpleCard();
    card.setTitle("Chorematick");
    card.setContent(speechText);

    PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
    speech.setText(speechText);

    Reprompt reprompt = new Reprompt();
    reprompt.setOutputSpeech(speech);

    return SpeechletResponse.newAskResponse(speech, reprompt, card);
  }

  private SpeechletResponse getHelloResponse() {
    String speechText = "Hello world";

    SimpleCard card = new SimpleCard();
    card.setTitle("Chorematick");
    card.setContent(speechText);

    PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
    speech.setText(speechText);

    return SpeechletResponse.newTellResponse(speech, card);
  }

  private SpeechletResponse getHelpResponse() {
    PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
    speech.setText("You can ask me for a chore, by saying, what is my chore?");
    Reprompt reprompt = new Reprompt();
    return SpeechletResponse.newAskResponse(speech, reprompt);
  }

  private SpeechletResponse getErrorResponse() {
    String speechText = "error error error";
    PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
    speech.setText(speechText);
    return SpeechletResponse.newTellResponse(speech);
  }
}
