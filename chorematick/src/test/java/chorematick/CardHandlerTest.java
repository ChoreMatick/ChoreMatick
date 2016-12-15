package chorematick;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class CardHandlerTest extends BaseTestCase {

  private CardHandler cardHandler;

  @Before
  public void setup() {
    cardHandler = new CardHandler();
  }

  @Test
  public void getSimpleCardTest(){
    assertEquals(cardHandler.getSimpleCard("Title", "Some wordy content").getTitle(), "Title");
    assertEquals(cardHandler.getSimpleCard("Title", "Some wordy content").getContent(), "Some wordy content");
  }

  @Test
  public void getStandardCardTest(){
    assertEquals(cardHandler.getStandardCard("Title", "Some wordy content", "www.image.com").getTitle(), "Title");
    assertEquals(cardHandler.getStandardCard("Title", "Some wordy content", "www.image.com").getText(), "Some wordy content");
    assertEquals(cardHandler.getStandardCard("Title", "Some wordy content", "www.image.com").getImage().getSmallImageUrl(), "www.image.com");
    assertEquals(cardHandler.getStandardCard("Title", "Some wordy content", "www.image.com").getImage().getLargeImageUrl(), "www.image.com");
  }

}
