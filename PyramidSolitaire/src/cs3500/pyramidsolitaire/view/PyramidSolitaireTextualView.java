package cs3500.pyramidsolitaire.view;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import java.io.IOException;

/**
 * Represents a view that converts the state of the game into a rendering for the player.
 */
public class PyramidSolitaireTextualView implements PyramidSolitaireView {

  private final PyramidSolitaireModel<?> model;
  private Appendable ap;

  public PyramidSolitaireTextualView(PyramidSolitaireModel<?> model) {
    this.model = model;
  }

  public PyramidSolitaireTextualView(PyramidSolitaireModel<?> model, Appendable ap) {
    this.model = model;
    this.ap = ap;
  }

  @Override
  public String toString() {
    StringBuilder str = new StringBuilder("");
    if (model.getNumRows() == -1) {
      return "";
    } else if (model.getScore() == 0) {
      return "You win!";
    } else if (model.isGameOver()) {
      return "Game over. Score: " + model.getScore();
    } else {
      for (int i = 0; i < model.getNumRows(); i++) {
        if (i != 0) {
          str.append("\n");
        }
        StringBuilder space = new StringBuilder("");
        for (int j = 0; j < model.getRowWidth(i); j++) {
          if (j != 0) {
            str.append(" ");
          }
          if (j == 0) {
            for (int k = 0; k < ((model.getNumRows() - (i + 1)) * 2); k++) {
              space.append(" ");
            }
            str.append(space);
          }
          if (isNull((model.getCardAt(i, j)))) {
            str.append(".");
            if (j != i) {
              str.append("  ");
            }
          }
          if (nonNull(model.getCardAt(i, j))) {
            str.append(model.getCardAt(i, j).toString());
            if (model.getCardAt(i, j).toString().length() == 2 && (i != j
                || model.getRowWidth(i) > model.getNumRows())) {
              if (j != model.getRowWidth(i) - 1) {
                str.append(" ");
              }
            }
          }
        }
      }
    }
    StringBuilder lastLine = new StringBuilder("\n");
    lastLine.append("Draw:");
    for (int i = 0; i < model.getDrawCards().size(); i++) {
      lastLine.append(" " + model.getDrawCards().get(i).toString());
      if (i != (model.getDrawCards().size() - 1)) {
        lastLine.append(",");
      }
    }
    str.append(lastLine);
    return str.toString();
  }

  @Override
  public void render() throws IOException {
    this.ap.append(this.toString());
  }
}
