package cs3500.pyramidsolitaire.model.hw02;

import java.util.Objects;

/**
 * Represents a card with a suit and value.
 */
public class Card implements ICard {

  private final Value value;
  private final Suit suit;

  public Card(Value value, Suit suit) {
    this.value = value;
    this.suit = suit;
  }

  @Override
  public int getValue() {
    return this.value.getValue();
  }

  @Override
  public String toString() {
    return this.value.toString() + this.suit.toString();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == null) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof Card)) {
      return false;
    }

    Card that = (Card) obj;

    return this.value == that.value && this.suit == that.suit;
  }

  @Override
  public int hashCode() {
    return Objects.hash(value, suit);
  }
}

