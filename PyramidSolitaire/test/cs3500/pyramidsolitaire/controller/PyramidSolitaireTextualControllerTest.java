package cs3500.pyramidsolitaire.controller;

import cs3500.pyramidsolitaire.controller.PyramidSolitaireController;
import cs3500.pyramidsolitaire.controller.PyramidSolitaireTextualController;
import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.model.hw02.Suit;
import cs3500.pyramidsolitaire.model.hw02.Value;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Contains tests for the controller.
 */
public class PyramidSolitaireTextualControllerTest {

  protected PyramidSolitaireModel model = new BasicPyramidSolitaire();
  protected List<Card> deck = new ArrayList<Card>();

  /**
   * Creates an example deck.
   */
  @Before
  public void createDeck() {
    for (Value value : Value.values()) {
      for (Suit suit : Suit.values()) {
        deck.add(new Card(value, suit));
      }
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullInputOne() {
    Readable in = new StringReader("dd 0");
    Appendable out = new StringBuffer();

    PyramidSolitaireController con = new PyramidSolitaireTextualController(in, out);
    con.playGame(model, null, true, 7, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullInputTwo() {
    Readable in = new StringReader("dd 0");
    Appendable out = new StringBuffer();

    PyramidSolitaireController con = new PyramidSolitaireTextualController(in, out);
    con.playGame(null, deck, true, 7, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidDeck() {
    Readable in = new StringReader("dd 0");
    Appendable out = new StringBuffer();

    List<Card> invalid = new ArrayList<>();

    PyramidSolitaireController con = new PyramidSolitaireTextualController(in, out);
    con.playGame(model, invalid, true, 7, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidRow() {
    Readable in = new StringReader("dd 0");
    Appendable out = new StringBuffer();

    PyramidSolitaireController con = new PyramidSolitaireTextualController(in, out);
    con.playGame(model, deck, true, 5000, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidDraw() {
    Readable in = new StringReader("dd 0");
    Appendable out = new StringBuffer();

    PyramidSolitaireController con = new PyramidSolitaireTextualController(in, out);
    con.playGame(model, deck, true, 7, 5000);
  }

  @Test
  public void removeInvalid() throws Exception {
    Appendable out = new StringBuffer();
    Readable in = new StringReader("dd 30300");

    PyramidSolitaireController con = new PyramidSolitaireTextualController(in, out);
    con.playGame(model, deck, true, 7, 1);
    assertEquals(deck.size(), 52);
  }

  @Test
  public void removeNonExposed() throws Exception {
    Readable in = new StringReader("rm1 1 1");
    Appendable out = new StringBuffer();

    PyramidSolitaireController con = new PyramidSolitaireTextualController(in, out);
    con.playGame(model, deck, false, 7, 1);
    assertFalse(out.toString().contains("invalid"));
  }

  @Test
  public void quitOne() {
    Readable in = new StringReader("q");
    Appendable out = new StringBuffer();

    PyramidSolitaireController con = new PyramidSolitaireTextualController(in, out);
    con.playGame(model, deck, false, 7, 1);
    assertTrue(out.toString().contains("quit"));
  }

  @Test
  public void quitTwo() {
    Readable in = new StringReader("Q");
    Appendable out = new StringBuffer();

    PyramidSolitaireController con = new PyramidSolitaireTextualController(in, out);
    con.playGame(model, deck, false, 7, 1);
    assertTrue(out.toString().contains("quit"));
  }
}