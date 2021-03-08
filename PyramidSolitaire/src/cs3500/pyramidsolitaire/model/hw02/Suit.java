package cs3500.pyramidsolitaire.model.hw02;

/**
 * Represents either hearts, clubs, diamonds, or spades.
 */
public enum Suit {
  HEARTS("♥"), DIAMONDS("♦"), CLUBS("♣"), SPADES("♠");
  private final String suit;

  Suit(String suite) {
    this.suit = suite;
  }

  @Override
  public String toString() {
    return this.suit;
  }
}
