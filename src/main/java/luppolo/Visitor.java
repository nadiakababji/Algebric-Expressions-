package luppolo;

/**
 * The Visitor interface represents a visitor pattern for nodes Implementations of this interface
 * can visit different types of nodes and return a string representation specific to each node type.
 */
public interface Visitor {

  /**
   * Visits a rational number node
   *
   * @param razionale the rational number node to visit
   * @return the resulting string of the visit
   * @throws NullPointerException if {@code razionale} is {@code null}
   */
  String visit(Razionale razionale) throws NullPointerException;

  /**
   * Visits a symbol node
   *
   * @param simbolo the symbol node to visit
   * @return the resulting string of the visit
   * @throws NullPointerException if {@code simbolo} is {@code null}
   */
  String visit(Simbolo simbolo) throws NullPointerException;

  /**
   * Visits an addition node
   *
   * @param addizione the addition node to visit
   * @return the resulting string of the visit
   * @throws NullPointerException if {@code addizione} is {@code null}
   */
  String visit(Addizione addizione) throws NullPointerException;

  /**
   * Visits a multiplication node
   *
   * @param moltiplicazione the multiplication node to visit
   * @return the resulting string of the visit
   * @throws NullPointerException if {@code moltiplicazione} is {@code null}
   */
  String visit(Moltiplicazione moltiplicazione) throws NullPointerException;

  /**
   * Visits a power node
   *
   * @param potenza the power node to visit
   * @return the resulting string of the visit
   * @throws NullPointerException if {@code potenza} is {@code null}
   */
  String visit(Potenza potenza) throws NullPointerException;
}
