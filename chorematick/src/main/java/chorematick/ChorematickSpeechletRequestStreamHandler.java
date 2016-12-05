package chorematick;

import java.util.HashSet;
import java.util.Set;

import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;

public final class ChorematickSpeechletRequestStreamHandler extends SpeechletRequestStreamHandler {
    private static final Set<String> supportedApplicationIds = new HashSet<String>();
    static {
        supportedApplicationIds.add("amzn1.ask.skill.6ff2efd2-c737-49e4-9dc2-6c33dd33eb9d");
    }

    public ChorematickSpeechletRequestStreamHandler() {
        super(new ChorematickSpeechlet(), supportedApplicationIds);
    }
}
