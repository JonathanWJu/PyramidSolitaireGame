
package cs3500.pyramidsolitaire.model.hw02;

import static java.util.Objects.isNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Model that maintains the game state and utilizes the Card class.
 */
public class BasicPyramidSolitaire implements PyramidSolitaireModel<Card> {

  protected List<Card> refDeck = new ArrayList<>();
  protected List<Card> draw = new ArrayList<>();
  protected List<Card> stock = new ArrayList<>();
  protected ArrayList<ArrayList<Card>> pyramid = new ArrayList<>();
  protected boolean start = false;


  @Override
  public List getDeck() {
    List<Card> copyDeck = new ArrayList<>();
    for (Value value : Value.values()) {
      for (Suit suit : Suit.values()) {
        copyDeck.add(new Card(value, suit));
      }
    }
    return copyDeck;
  }

  @Override
  public void startGame(List<Card> deck, boolean shuffle, int numRows, int numDraw)
      throws IllegalArgumentException {
    if (deck == null) {
      throw new IllegalArgumentException("deck cannot be null");
    }
    if (deck.size() != 52) {
      throw new IllegalArgumentException("deck must have 52 cards");
    }
    if (numRows <= 0 || numRows > 10) {
      throw new IllegalArgumentException("invalid number of rows");
    }
    if (numDraw < 0 || numDraw > 52 - ((numRows * (numRows + 1)) / 2)) {
      throw new IllegalArgumentException("invalid number of draw cards");
    }

    refDeck.addAll(deck);
    for (int i = 0; i < refDeck.size(); i++) {
      for (int j = i + 1; j < refDeck.size(); j++) {
        if (i != j && refDeck.get(i) == refDeck.get(j)) {
          throw new IllegalArgumentException("deck contains duplicates");
        }
      }
    }
    if (shuffle) {
      Collections.shuffle(refDeck);
    }
    int counter = 0;
    for (int i = 0; i < numRows; i++) {
      pyramid.add(new ArrayList<>(i + 1));
      for (int j = 0; j <= i; j++) {
        pyramid.get(i).add(refDeck.get(counter++));
      }
    }
    try {
      draw.addAll(refDeck.subList(counter, counter + numDraw));
      stock.addAll(refDeck.subList(counter + numDraw, refDeck.size()));
      start = true;
    } catch (IndexOutOfBoundsException i) {
      throw new IllegalArgumentException();
    }
  }


  @Override
  public void remove(int row1, int card1, int row2, int card2)
      throws IllegalArgumentException, IllegalStateException {
    try {
      if (!start) {
        throw new IllegalStateException("game hasn't started");
      }
      if (pyramid.get(row1).get(card1).getValue() + pyramid.get(row2).get(card2).getValue() != 13) {
        throw new IllegalArgumentException("combined value of two cards is not 13");
      }
      List<Card> exposed = getExposed();
      if (!(exposed.contains(getCardAt(row1, card1))
          && exposed.contains(getCardAt(row2, card2)))) {
        throw new IllegalArgumentException("can't remove non-exposed cards");
      }
      pyramid.get(row1).set(card1, null);
      pyramid.get(row2).set(card2, null);
    } catch (IndexOutOfBoundsException e) {
      throw new IllegalArgumentException("did not select valid cards");
    } catch (NullPointerException e) {
      throw new IllegalArgumentException("card already removed");
    }
  }

  @Override
  public void remove(int row, int card) throws IllegalArgumentException, IllegalStateException {
    try {
      if (!start) {
        throw new IllegalStateException("game hasn't started");
      }
      if (pyramid.get(row).get(card).getValue() != 13) {
        throw new IllegalArgumentException("only kings can be removed by themselves");
      }
      List<Card> exposed = getExposed();
      if (!exposed.contains(getCardAt(row, card))) {
        throw new IllegalArgumentException("can't remove non-exposed card");
      }
      pyramid.get(row).set(card, null);
    } catch (IndexOutOfBoundsException e) {
      throw new IllegalArgumentException("did not select valid card");
    } catch (NullPointerException e) {
      throw new IllegalArgumentException("card already removed");
    }
  }


  @Override
  public void removeUsingDraw(int drawIndex, int row, int card)
      throws IllegalArgumentException, IllegalStateException {
    try {
      if (!start) {
        throw new IllegalStateException("game hasn't started");
      }
      if (draw.get(drawIndex).getValue() + getCardAt(row, card).getValue() != 13) {
        throw new IllegalArgumentException("combined value of the cards is not 13");
      }
      List<Card> exposed = getExposed();
      if (!exposed.contains(getCardAt(row, card))) {
        throw new IllegalArgumentException("can't remove non-exposed card");
      }
      discardDraw(drawIndex);
      pyramid.get(row).set(card, null);
    } catch (IndexOutOfBoundsException e) {
      throw new IllegalArgumentException("did not select valid cards");
    } catch (NullPointerException e) {
      throw new IllegalArgumentException("card already removed");
    }
  }

  @Override
  public void discardDraw(int drawIndex) throws IllegalArgumentException, IllegalStateException {
    try {
      if (!start) {
        throw new IllegalStateException("game hasn't started");
      }
      if (draw.size() <= 0) {
        throw new IllegalArgumentException("draw must have cards");
      }
      if (stock.size() > 0) {
        draw.remove(drawIndex);
        draw.add(drawIndex, stock.get(0));
        stock.remove(0);
      } else {
        draw.remove(drawIndex);
      }
    } catch (IndexOutOfBoundsException e) {
      throw new IllegalArgumentException("invalid discard");
    } catch (NullPointerException e) {
      throw new IllegalArgumentException("card already removed");
    }
  }

  @Override
  public int getNumRows() {
    if (!start) {
      return -1;
    } else {
      return pyramid.size();
    }
  }

  @Override
  public int getNumDraw() {
    if (!start) {
      return -1;
    } else {
      return draw.size();
    }
  }

  @Override
  public int getRowWidth(int row) throws IllegalArgumentException, IllegalStateException {
    if (!start) {
      throw new IllegalStateException("game hasn't started");
    }
    if (row < 0 || row >= pyramid.size()) {
      throw new IllegalArgumentException("invalid row");
    }
    return row + 1;
  }

  @Override
  public boolean isGameOver() throws IllegalStateException {
    if (!start) {
      throw new IllegalStateException("game hasn't started");
    }
    if (getScore() == 0) {
      return true;
    }
    if (stock.size() > 0) {
      return false;
    }
    List<Card> exposed = getExposed();

    for (int i = 0; i < exposed.size(); i++) {
      if (exposed.get(i) != null && exposed.get(i).getValue() == 13) {
        return false;
      }
      for (int j = 0; j < exposed.size(); j++) {
        if (exposed.get(i) != null && exposed.get(j) != null
            && exposed.get(i).getValue() + exposed.get(j).getValue() == 13) {
          return false;
        }
      }
    }
    for (int i = 0; i < exposed.size(); i++) {
      for (int j = 0; j < draw.size(); j++) {
        if (exposed.get(i) != null && draw.get(j) != null
            && exposed.get(i).getValue() + draw.get(j).getValue() == 13) {
          return false;
        }
      }
    }
    return true;
  }

  @Override
  public int getScore() throws IllegalStateException {
    int counter = 0;
    if (!start) {
      throw new IllegalStateException("game hasn't started");
    }
    try {
      for (int i = 0; i < pyramid.size(); i++) {
        for (int j = 0; j <= i; j++) {
          if (isNull(pyramid.get(i).get(j))) {
            counter += 0;
          } else {
            counter += pyramid.get(i).get(j).getValue();
          }
        }
      }
      return counter;
    } catch (Exception e) {
      throw new IllegalArgumentException("invalid coordinates");
    }
  }

  @Override
  public Card getCardAt(int row, int card)
      throws IllegalArgumentException, IllegalStateException {
    if (!start) {
      throw new IllegalStateException("game hasn't started");
    }
    try {
      return pyramid.get(row).get(card);
    } catch (Exception e) {
      throw new IllegalArgumentException("invalid coordinates");
    }
  }

  protected List<Card> getExposed() {
    List<Card> exposed = new ArrayList<>();
    List<Card> bottomRow = pyramid.get(pyramid.size() - 1);
    for (int i = 0; i < bottomRow.size(); i++) {
      if (bottomRow.get(i) != null) {
        exposed.add(bottomRow.get(i));
      }
    }
    for (int i = 0; i < pyramid.size() - 1; i++) {
      for (int j = 0; j <= i; j++) {
        if ((pyramid.get(i + 1).get(j) == null) && (pyramid.get(i + 1).get(j + 1) == null)) {
          exposed.add(pyramid.get(i).get(j));
        }
      }
    }
    return exposed;
  }

  @Override
  public List getDrawCards() throws IllegalStateException {
    List<Card> drawCards = new ArrayList<>();
    drawCards.addAll(draw);
    if (!start) {
      throw new IllegalStateException("game hasn't started");
    }
    return drawCards;
  }
}
