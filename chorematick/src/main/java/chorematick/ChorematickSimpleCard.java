package chorematick;

import com.amazon.speech.ui.SimpleCard;

public class ChorematickSimpleCard extends SimpleCard {

  public SimpleCard getSimpleCard(String title, String content){

    SimpleCard simpleCard = new SimpleCard();
    this.setTitle(title);
    this.setContent(content);

    return simpleCard;
  }
}
