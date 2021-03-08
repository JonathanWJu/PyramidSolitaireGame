package cs3500.pyramidsolitaire.view;

import static org.junit.Assert.assertEquals;
import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.ICard;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.model.hw02.Suit;
import cs3500.pyramidsolitaire.model.hw02.Value;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

/**
 * Holds various tests for the PyramidSolitaireTextualView view.
 */
public class PyramidSolitaireTextualViewTest {

  @Test
  public void testToString() {
    PyramidSolitaireModel model = new BasicPyramidSolitaire();
    List<Card> deck = new ArrayList<>();
    deck = model.getDeck();
    ICard card = new Card(Value.ACE, Suit.CLUBS);
    PyramidSolitaireTextualView view = new PyramidSolitaireTextualView(model);
    model.startGame(deck, true, 7, 3);
    assertEquals(deck.size(), 52);
  }
}