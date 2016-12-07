package chorematick;

import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.SimpleCard;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Kath on 06/12/2016.
 */
public class ChorematickSpeechletTest {

    @Test
    public void errorResponseTest() {

        String speechText = "error error error";
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);
        assertEquals("error error error", speech.getText());
    }

}