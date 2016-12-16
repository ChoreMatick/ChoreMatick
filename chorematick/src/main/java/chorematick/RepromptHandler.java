package chorematick;

import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.PlainTextOutputSpeech;

public class RepromptHandler {

  public Reprompt getReprompt(String text){
    Reprompt reprompt = new Reprompt();
    PlainTextOutputSpeech repromptSpeech = new PlainTextOutputSpeech();
    repromptSpeech.setText(text);
    reprompt.setOutputSpeech(repromptSpeech);
    return reprompt;
  }
}
