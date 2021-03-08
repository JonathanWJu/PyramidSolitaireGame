package cs3500.pyramidsolitaire.model.hw04;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a model with relaxed rules of solitaire where certain pairs of cards can now be
 * removed.
 */
public class RelaxedPyramidSolitaire extends BasicPyramidSolitaire implements
    PyramidSolitaireModel<Card> {

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
      if (exposed.contains(getCardAt(row1, card1))
          && exposed.contains(getCardAt(row2, card2))) {
        pyramid.get(row1).set(card1, null);
        pyramid.get(row2).set(card2, null);
      } else if (row1 + 1 == row2) {
        if (((card1 == card2) || (card1 + 1 == card2))
            &&
            exposed.contains(getCardAt(row2, card2))) {
          pyramid.get(row1).set(card1, null);
          pyramid.get(row2).set(card2, null);
        }
      } else if (row2 + 1 == row1) {
        if (((card2 == card1) || (card2 + 1 == card1))
            &&
            exposed.contains(getCardAt(row1, card1))) {
          pyramid.get(row1).set(card1, null);
          pyramid.get(row2).set(card2, null);
        }
      } else {
        throw new IllegalArgumentException("cards not uncovered");
      }
    } catch (IndexOutOfBoundsException e) {
      throw new IllegalArgumentException("did not select valid cards");
    } catch (NullPointerException e) {
      throw new IllegalArgumentException("card already removed");
    }
  }

  @Override
  public boolean isGameOver() throws IllegalStateException {
    if (super.isGameOver()) {
      if (getScore() != 0) {
        return numRelaxedPairs() == 0;
      }
    }
    if (getScore() == 0) {
      return getScore() == 0;
    }
    return false;
  }

  protected boolean isRelaxedPair(int row1, int col1, int row2, int col2, List<Card> exposedCards) {
    boolean relaxed = false;
    Card card1 = getCardAt(row1, col1);
    Card card2 = getCardAt(row2, col2);
    if (card1 != null && card2 != null && (card1.getValue() + card2.getValue() == 13)) {
      if (row1 + 1 == row2) {
        relaxed = ((col1 == col2) || (col1 + 1 == col2)) && exposedCards.contains(card2);
      } else if (row2 + 1 == row1) {
        relaxed = ((col2 == col1) || (col2 + 1 == col1)) && exposedCards.contains(card1);
      }
    }
    return relaxed;
  }

  protected int numRelaxedPairs() {
    List<String> relaxed = new ArrayList<String>();
    List<Card> exposed = getExposed();
    for (int i = 0; i < getNumRows() - 1; ++i) {
      for (int j = 0; j < getRowWidth(i); ++j) {
        if (isRelaxedPair(i, j, i + 1, j, exposed)) {
          relaxed.add(getCardAt(i, j).toString() + "-" + getCardAt(i + 1, j));
        }
        if (isRelaxedPair(i, j, i + 1, j + 1, exposed)) {
          relaxed.add(getCardAt(i, j).toString() + "-" + getCardAt(i + 1, j + 1));
        }
      }
    }
    return relaxed.size();
  }
}

