package cs3500.pyramidsolitaire.controller;

import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.view.PyramidSolitaireTextualView;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * A textual controller for interacting with the model. A player can give commands to play the game
 * as well as quit the game when wanted.
 */
public class PyramidSolitaireTextualController implements PyramidSolitaireController {

  private final Readable rd;
  private final Appendable ap;
  private boolean quit = false;
  private String in;
  private Scanner scan;
  private String error = "Invalid move. Play again. ";

  /**
   * serves as a controller for the model and view.
   *
   * @param rd data to read
   * @param ap data to append to
   * @throws IllegalArgumentException if the model or deck is null
   */
  public PyramidSolitaireTextualController(Readable rd, Appendable ap)
      throws IllegalArgumentException {
    if (rd == null || ap == null) {
      throw new IllegalArgumentException("both arguments must not be null");
    }
    this.rd = rd;
    this.ap = ap;
  }

  @Override
  public <K> void playGame(PyramidSolitaireModel<K> model, List<K> deck, boolean shuffle,
      int numRows, int numDraw) throws IllegalArgumentException, IllegalStateException {
    if (model == null) {
      throw new IllegalArgumentException("model cannot be null");
    }
    if (deck == null) {
      throw   new IllegalArgumentException("deck cannot be null");
    }

    model.startGame(deck, shuffle, numRows, numDraw);

    this.scan = new Scanner(this.rd);
    try {
      new PyramidSolitaireTextualView(model, this.ap).render();
      this.ap.append("\nScore: " + model.getScore() + "\n");
      while (!model.isGameOver() && !this.quit) {
        if (scan.hasNext()) {
          in = scan.next();
          switch (in) {
            case "rm1":
              this.rmOne(model);
              break;

            case "rm2":
              this.rmTwo(model);
              break;

            case "rmwd":
              this.rmDraw(model);
              break;

            case "dd":
              this.discard(model);
              break;

            case "q":
            case "Q":
              this.quit = true;
              break;

            default:
              break;
          }
          if (model.isGameOver() || this.quit) {
            break;
          }
          new PyramidSolitaireTextualView(model, this.ap).render();
          this.ap.append("\nScore: " + model.getScore() + "\n");
        }

      }
      if (model.isGameOver()) {
        new PyramidSolitaireTextualView(model, this.ap).render();
      }

      if (this.quit) {
        this.ap.append("Game quit!\nState of game when quit:\n");
        new PyramidSolitaireTextualView(model, this.ap).render();
        this.ap.append("\nScore: " + model.getScore());
      }
    } catch (IOException e) {
      new IllegalStateException("Input and output were not successfully transmitted");
    }
  }

  private <K> void discard(PyramidSolitaireModel<K> model) throws IOException {
    int drawIndex = correctInput();
    if (!quit) {
      try {
        model.discardDraw(drawIndex - 1);
      } catch (IllegalArgumentException e) {
        this.ap.append(error + e.getMessage() + "\n");
      }
    }
  }

  private <K> void rmTwo(PyramidSolitaireModel<K> model) throws IOException {
    int row1 = correctInput();
    int card1 = correctInput();
    int row2 = correctInput();
    int card2 = correctInput();

    if (!quit) {
      try {
        model.remove(row1 - 1, card1 - 1, row2 - 1, card2 - 1);
      } catch (IllegalArgumentException e) {
        this.ap.append(error + e.getMessage() + "\n");
      }
    }
  }

  private <K> void rmDraw(PyramidSolitaireModel<K> model) throws IOException {
    int drawIndex = correctInput();
    int row = correctInput();
    int card = correctInput();
    if (!quit) {
      try {
        model.removeUsingDraw(drawIndex - 1, row - 1, card - 1);
      } catch (IllegalArgumentException e) {
        this.ap.append(error + e.getMessage() + "\n");
      }
    }
  }

  private <K> void rmOne(PyramidSolitaireModel<K> model) throws IOException {
    int row = correctInput();
    int card = correctInput();
    if (!quit) {
      try {
        model.remove(row - 1, card - 1);
      } catch (IllegalArgumentException e) {
        this.ap.append(error + e.getMessage() + "\n");
      } catch (IllegalStateException e) {
        this.ap.append(error + e.getMessage() + "\n");
      }
    }
  }

  private int correctInput() {
    while (!this.scan.hasNextInt() && this.scan.hasNext()) {
      if (scan.next().equalsIgnoreCase("q")) {
        quit = true;
        return -1;
      }
    }
    if (this.scan.hasNext()) {
      return this.scan.nextInt();
    } else {
      return -1;
    }
  }
}
