package chorematick;

import java.util.HashSet;
import java.util.Set;

import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;

public final class ChorematickSpeechletRequestStreamHandler extends SpeechletRequestStreamHandler {
    private static final Set<String> supportedApplicationIds = new HashSet<String>();
    static {
        supportedApplicationIds.add("****AlexaSkillID****");
    }

    public ChorematickSpeechletRequestStreamHandler() {
        super(new ChorematickSpeechlet(), supportedApplicationIds);
    }
}
