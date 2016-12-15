package chorematick;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.SsmlOutputSpeech;

class SpeechHandler{

  public SsmlOutputSpeech getSsmlSpeech(String speechText){
    SsmlOutputSpeech speech = new SsmlOutputSpeech();
    speech.setSsml(speechText);
    return speech;
  }

  public PlainTextOutputSpeech getPlainSpeech(String speechText){
    PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
    speech.setText(speechText);
    return speech;
  }
}
