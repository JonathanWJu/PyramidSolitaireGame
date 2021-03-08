package cs3500.pyramidsolitaire.model.hw04;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a model containing multiple pyramids combined together.
 */
public class MultiPyramidSolitaire extends BasicPyramidSolitaire implements
    PyramidSolitaireModel<Card> {

  public MultiPyramidSolitaire() {
    super();
  }

  @Override
  public List getDeck() {
    List<Card> copyDeck = new ArrayList<>();
    List<Card> copy = super.getDeck();
    copyDeck.addAll(copy);
    copyDeck.addAll(copy);
    return copyDeck;
  }

  @Override
  public void startGame(List<Card> deck, boolean shuffle, int numRows, int numDraw)
      throws IllegalArgumentException {
    if (deck == null) {
      throw new IllegalArgumentException("deck cannot be null");
    }
    if (deck.size() != 104) {
      throw new IllegalArgumentException("deck must have 104 cards");
    }
    if (numRows <= 0 || numRows > 9) {
      throw new IllegalArgumentException("invalid number of rows");
    }
    if (numDraw < 0 || numDraw > 52) {
      throw new IllegalArgumentException("invalid number of draw cards");
    }
    for (int i = 0; i < 52; i++) {
      for (int j = i + 1; j < 52; j++) {
        if (i != j && deck.get(i) == deck.get(j)) {
          throw new IllegalArgumentException("deck contains duplicates");
        }
      }
    }
    List<Card> copyOfDeck = new ArrayList<>();
    copyOfDeck.addAll(deck);
    if (shuffle) {
      Collections.shuffle(copyOfDeck);
    }
    int counter = fillMulti(numRows, copyOfDeck);
    draw.addAll(copyOfDeck.subList(counter, counter + numDraw));
    stock.addAll(copyOfDeck.subList(counter + numDraw, copyOfDeck.size()));
    start = true;
  }


  @Override
  public int getRowWidth(int row) throws IllegalArgumentException, IllegalStateException {
    if (!start) {
      throw new IllegalStateException("game hasn't started");
    }
    if (row < 0 || row > pyramid.size()) {
      throw new IllegalArgumentException("invalid row");
    }
    return pyramid.get(row).size();
  }

  private int fillMulti(int numRows, List<Card> deck) {
    int counter = 0;
    int overlapped = (numRows + 1) / 2;
    int nonOverlapped = numRows - overlapped;
    int firstRowWidth = 2 * (numRows - overlapped) + 1;
    for (int i = 0; i < numRows; i++) {
      ArrayList<Card> newList = new ArrayList<>(firstRowWidth + i);
      for (int j = 0; j < firstRowWidth + i; j++) {
        if (nonOverlapped != 0) {
          if ((j % nonOverlapped) <= i) {
            newList.add(deck.get(counter++));
          } else {
            newList.add(null);
          }
        }
      }
      pyramid.add(newList);
    }
    return counter;
  }
}
