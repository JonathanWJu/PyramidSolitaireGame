package cs3500.pyramidsolitaire.model.hw04;

import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.ICard;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.model.hw02.Suit;
import cs3500.pyramidsolitaire.model.hw02.Value;
import org.junit.Test;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Tests various things for the RelaxedPyramidSolitaire model.
 */
public class RelaxedPyramidSolitaireTest {

  PyramidSolitaireModel model = new RelaxedPyramidSolitaire();
  List<Card> deck = model.getDeck();
  ICard card = new Card(Value.ACE, Suit.CLUBS);

  @Test
  public void testGetDeck() {
    deck = model.getDeck();
    assertEquals(52, deck.size());
  }

  @Test
  public void testStartGame() {
    deck = model.getDeck();
    model.startGame(deck, false, 7, 3);
    assertEquals(model.getNumRows(), 7);
    assertEquals(model.getNumDraw(), 3);
    assertEquals(model.getRowWidth(0), 1);
    assertEquals(model.getRowWidth(6), 7);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameWithLargeRow() {
    deck = model.getDeck();
    model.startGame(deck, false, 10, 3);
    assertEquals(model.getNumRows(), 7);
  }

  /*
   *  This test will only pass with relaxed model
   */
  @Test
  public void testRemoveTwoMultipleTimes() {
    model.startGame(deck, false, 7, 3);
    model.remove(6, 0, 6, 6);
    model.remove(6, 1, 6, 3);
    // this step is not allowed in basic model
    model.remove(5, 5, 6, 5);
    assertEquals(73, model.getScore());
  }

  // game must start first
  @Test(expected = IllegalStateException.class)
  public void testStartGame2() {
    List<Card> deck = model.getDeck();
    assertEquals(52, deck.size());
    model.getDrawCards();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveOne() {
    model.startGame(deck, false, 7, 3);
    model.remove(6, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveTwo2() {
    model.startGame(deck, false, 7, 3);
    model.remove(5, 0, 6, 6);
    assertEquals(99, model.getScore());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveUsingDraw() {
    model.startGame(deck, false, 7, 3);
    model.removeUsingDraw(1, 6, 2);
  }

  @Test
  public void testDiscardDraw() {
    model.startGame(deck, false, 7, 3);
    model.discardDraw(1);
    assertEquals(3, model.getDrawCards().size());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDiscardDraw2() {
    model.startGame(deck, false, 7, 3);
    model.discardDraw(5);
    assertEquals(3, model.getDrawCards().size());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDiscardDraw3() {
    model.startGame(deck, false, 7, 3);
    model.discardDraw(-1);
    assertEquals(3, model.getDrawCards().size());
  }

  @Test
  public void testGetNumRows() {
    model.startGame(deck, false, 6, 3);
    assertEquals(6, model.getNumRows());
  }

  @Test
  public void testGetNumDraw() {
    model.startGame(deck, false, 7, 5);
    assertEquals(5, model.getNumDraw());
  }

  @Test
  public void testGetRowWidth() {
    model.startGame(deck, false, 7, 3);
    assertEquals(6, model.getRowWidth(5));
  }

  @Test
  public void testIsGameOver() {
    model.startGame(deck, false, 7, 3);
    assertEquals(false, model.isGameOver());
  }

  @Test
  public void testGetScore() {
    model.startGame(deck, false, 7, 3);
    assertEquals(112, model.getScore());
  }


}