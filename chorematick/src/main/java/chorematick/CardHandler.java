package chorematick;

import com.amazon.speech.ui.SimpleCard;
import com.amazon.speech.ui.StandardCard;
import com.amazon.speech.ui.Image;

public class CardHandler {

  public SimpleCard getSimpleCard(String title, String content){
    SimpleCard card = new SimpleCard();
    card.setTitle(title);
    card.setContent(content);
    return card;
  }

  public StandardCard getStandardCard(String title, String text, String imageURL){
    StandardCard card = new StandardCard();
    Image image = new Image();
    image.setSmallImageUrl(imageURL);
    image.setLargeImageUrl(imageURL);
    card.setTitle(title);
    card.setText(text);
    card.setImage(image);
    return card;
  }
}
