package cs3500.pyramidsolitaire.model.hw04;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;

/**
 * Factory class that allows users to create different instances of PyramidSolitaireModel.
 */
public class PyramidSolitaireCreator {

  /**
   * Enumeration representing 3 types of PyramidSolitaireModel's.
   */
  public enum GameType {
    BASIC("BASIC"),
    RELAXED("RELAXED"),
    MULTIPYRAMID("MULTIPYRAMID");

    private final String type;

    GameType(String gameType) {
      this.type = gameType;
    }

    public String getValue() {
      return this.type;
    }

    @Override
    public String toString() {
      return this.type;
    }
  }

  /**
   * Creates an instance of PyramidSolitaireModel depending on the GameType.
   * @param type One of the three PyramidSolitaireModel game types.
   * @return An instance of the desired PyramidSolitaireModel.
   */
  public static PyramidSolitaireModel<Card> create(GameType type) {
    PyramidSolitaireModel<Card> model = null;
    switch (type) {
      case BASIC:
        model = new BasicPyramidSolitaire();
        break;
      case RELAXED:
        model = new RelaxedPyramidSolitaire();
        break;
      case MULTIPYRAMID:
        model = new MultiPyramidSolitaire();
        break;
      default:
        break;
    }
    return model;
  }
}

