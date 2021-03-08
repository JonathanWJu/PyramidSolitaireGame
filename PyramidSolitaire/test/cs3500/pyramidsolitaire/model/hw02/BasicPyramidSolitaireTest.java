package cs3500.pyramidsolitaire.model.hw02;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

/**
 * Tests for various things in the BasicPyramidSolitaire model.
 */
public class BasicPyramidSolitaireTest {

  PyramidSolitaireModel model = new BasicPyramidSolitaire();
  List<Card> deck = new ArrayList<>();

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

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveTwoMultipleTimes() {
    deck = model.getDeck();
    model.startGame(deck, false, 7, 3);
    model.remove(6, 0, 6, 6);
    model.remove(6, 1, 6, 3);
    // this step is not allowed in basic model
    model.remove(5, 5, 6, 5);
    assertEquals(73, model.getScore());
  }

  @Test(expected = IllegalStateException.class)
  public void testGameStartException() {
    model.getDrawCards();
  }

  @Test(expected = IllegalStateException.class)
  public void testGameStartException2() {
    model.getScore();
  }

  @Test(expected = IllegalStateException.class)
  public void testGameStartException3() {
    model.remove(7, 1, 7, 5);
  }

  @Test
  public void testGameStart() {
    assertEquals(-1, model.getNumRows());
    assertEquals(-1, model.getNumDraw());
  }

  @Test
  public void testIsGameOver() {
    deck = model.getDeck();
    model.startGame(deck, false, 7, 3);
    assertEquals(false, model.isGameOver());
  }

  @Test
  public void testGetScore() {
    deck = model.getDeck();
    model.startGame(deck, false, 7, 3);
    assertEquals(112, model.getScore());
  }

  @Test(expected = IllegalStateException.class)
  public void testRemoveOne() {
    deck = model.getDeck();
    model.startGame(deck, false, 7, 3);
    model.remove(6, 0);
  }

  @Test
  public void testRemoveTwo() {
    deck = model.getDeck();
    model.startGame(deck, false, 7, 3);
    model.remove(6, 0, 6, 6);
    assertEquals(99, model.getScore());
    model.remove(6, 1, 6, 3);
    assertEquals(86, model.getScore());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveTwo2() {
    deck = model.getDeck();
    model.startGame(deck, false, 7, 3);
    model.remove(5, 0, 6, 6);
    assertEquals(99, model.getScore());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveUsingDraw() {
    deck = model.getDeck();
    model.startGame(deck, false, 7, 3);
    model.removeUsingDraw(1, 6, 2);
  }

  @Test
  public void testDiscardDraw() {
    deck = model.getDeck();
    model.startGame(deck, false, 7, 3);
    model.discardDraw(1);
    assertEquals(3, model.getDrawCards().size());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDiscardDraw2() {
    deck = model.getDeck();
    model.startGame(deck, false, 7, 3);
    model.discardDraw(5);
    assertEquals(3, model.getDrawCards().size());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDiscardDraw3() {
    deck = model.getDeck();
    model.startGame(deck, false, 7, 3);
    model.discardDraw(-1);
    assertEquals(3, model.getDrawCards().size());
  }

  @Test
  public void testGetNumRows() {
    deck = model.getDeck();
    model.startGame(deck, false, 6, 3);
    assertEquals(6, model.getNumRows());
  }

  @Test
  public void testGetNumDraw() {
    deck = model.getDeck();
    model.startGame(deck, false, 7, 5);
    assertEquals(5, model.getNumDraw());
  }

  @Test
  public void testGetRowWidth() {
    model.startGame(deck, false, 7, 3);
    assertEquals(6, model.getRowWidth(5));
  }

}