package cs3500.pyramidsolitaire;

import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.model.hw04.PyramidSolitaireCreator;
import java.util.List;

/**
 * Allows user to call different instances of PyramidSolitaireModel depending on inputs.
 */
public final class PyramidSolitaire {

  /**
   * User can call 1 or 3 arguments with the first being argument being the type of model and the
   * 2nd and 3rd arguments being the row number and draw number.
   * @param args First argument: BASIC, RELAXED, MULTIPYRAMID. Second argument: Row Number. Third
   *             Argument: Draw number.
   */
  public static void main(String[] args) {
    try {
      if (args.length == 0) {
        throw new IllegalArgumentException("must pass first argument");
      }
      int numRows = 7;
      int numDraw = 3;
      if (args.length >= 2) {
        numRows = Integer.parseInt(args[1]);
      }
      if (args.length >= 3) {
        numDraw = Integer.parseInt(args[2]);
      }
      PyramidSolitaireModel<Card> model = PyramidSolitaireCreator
          .create(PyramidSolitaireCreator.GameType.valueOf(args[0].toUpperCase()));
      List<Card> deck = model.getDeck();
      model.startGame(deck, true, numRows, numDraw);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("incorrect input passed in");
    }
  }
}