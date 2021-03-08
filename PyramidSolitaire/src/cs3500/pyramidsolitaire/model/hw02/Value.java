package cs3500.pyramidsolitaire.model.hw02;

/**
 * represents a value between 1 and 13.
 */
public enum Value {
  ACE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8),
  NINE(9), TEN(10), JACK(11), QUEEN(12), KING(13);

  private final int value;

  Value(int value) {
    this.value = value;
  }

  public int getValue() {
    return this.value;
  }

  @Override
  public String toString() {
    if (this.value == 1) {
      return "A";
    } else if (this.value == 11) {
      return "J";
    } else if (this.value == 12) {
      return "Q";
    } else if (this.value == 13) {
      return "K";
    } else {
      return String.valueOf(this.value);
    }
  }
}
