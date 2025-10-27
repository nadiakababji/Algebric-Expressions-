package luppolo;

/** An interface representing a generic node in an expression tree. */
public interface Nodo {
  /**
   * Returns the priority associated to the node.
   *
   * @return the priority of this node
   */
  public abstract int priority();

  /**
   * Accepts a visitor that operates on this node and returns a string result.
   *
   * @param visitor the Visitor object visiting this node
   * @return the resulting string after visiting this node
   */
  String accept(Visitor visitor);

  /**
   * Accepts a visitor that operates on this node and returns a node.
   *
   * @param visitor the VisitorNodo object visiting this node
   * @return the resulting node after visiting this node
   */
  Nodo accept(VisitorNodo visitor);
}
