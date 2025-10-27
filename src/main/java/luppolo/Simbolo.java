package luppolo;

/** An immutable concrete class, represents a symbolic node in an expression tree. */
public class Simbolo implements Nodo {

  /*
   * AF: a symbolic leaf node in an expression tree where: x = the character symbol represented by this node
   * RI: 'a'<=x<='z'
   */

  /** The character symbol represented by this node. */
  public final char x;

  /**
   * Constructs a new {@code Simbolo} with the specified character symbol.
   *
   * @param simbolo the character symbol to be represented by this node
   * @throws IllegalArgumentException if simblo is not a charachter between 'a' and 'z'
   */
  public Simbolo(char simbolo) {
    if (simbolo > 'z' || simbolo < 'a')
      throw new IllegalArgumentException("Simbolo charachter must be between 'a' and 'z'");
    this.x = simbolo;
  }

  @Override
  public String toString() {
    return Character.toString(x);
  }

  /**
   * Returns the priority of this node.
   *
   * @return 1
   */
  @Override
  public int priority() {
    return 6;
  }

  @Override
  public String accept(Visitor visitor) {
    return visitor.visit(this);
  }

  @Override
  public Nodo accept(VisitorNodo visitor) {
    return visitor.visit(this);
  }

  @Override
  public int hashCode() {
    return Character.hashCode(x);
  }

  @Override
  public boolean equals(Object other) {
    if (other == null) return false;
    if (!(other instanceof Simbolo)) return false;
    Simbolo otherNodo = (Simbolo) other;
    if (x != otherNodo.x) return false;
    return true;
  }
}
