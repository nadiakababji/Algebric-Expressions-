package luppolo;

/**
 * The VisitorNodo interface represents a visitor pattern for nodes. Implementations of this
 * interface can visit different types of nodes and perform operations specific to each node type.
 */
public interface VisitorNodo {

  /**
   * Visits a rational number node
   *
   * @param razionale the rational number node to visit
   * @return the resulting node of the visit
   * @throws NullPointerException if {@code moltiplicazione} is {@code null}
   */
  Nodo visit(Razionale razionale) throws NullPointerException;

  /**
   * Visits a symbol node
   *
   * @param simbolo the symbol node to visit
   * @return the resulting node of the visit
   * @throws NullPointerException if {@code moltiplicazione} is {@code null}
   */
  Nodo visit(Simbolo simbolo) throws NullPointerException;

  /**
   * Visits an addition node
   *
   * @param addizione the addition node to visit
   * @return the resulting node of the visit
   * @throws NullPointerException if {@code moltiplicazione} is {@code null}
   */
  Nodo visit(Addizione addizione) throws NullPointerException;

  /**
   * Visits a multiplication node
   *
   * @param moltiplicazione the multiplication node to visit
   * @return the resulting node of the visit
   * @throws NullPointerException if {@code moltiplicazione} is {@code null}
   */
  Nodo visit(Moltiplicazione moltiplicazione) throws NullPointerException;

  /**
   * Visits a power node
   *
   * @param potenza the power node to visit
   * @return the resulting node of the visit
   * @throws NullPointerException if {@code moltiplicazione} is {@code null}
   */
  Nodo visit(Potenza potenza) throws NullPointerException;
}
