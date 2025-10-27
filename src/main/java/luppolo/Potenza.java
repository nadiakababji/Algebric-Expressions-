package luppolo;

import java.util.*;

/**
 * An immutable concrete class, rappresents a power operation node within a tree structure of
 * mathematical expressions It encapsulates two main components: a {@code Nodo} base and an exponent
 * represented by a {@code Razionale}
 */
public class Potenza extends NodoInterno {
  /*
   * AF:A power operation node in a tree structure,where:
   * - base represents the base of the power operation
   * - esponente represents the exponent of the power operation
   *
   * RI:base and esponente are not {@code null}, esponente is a node of type Razionale
   */

  /** the base of the power */
  private final Nodo base;

  /** the exponent of the power */
  private final Razionale esponente;

  /**
   * Constructs a Potenza node with given base and exponent
   *
   * @param b base
   * @param e exponent
   * @throws NullPointerException if b or e is {@code null};
   */
  public Potenza(Nodo b, Razionale e) {
    Objects.requireNonNull(b, "the base of Potenza cannot be null");
    Objects.requireNonNull(e, "the exponent of Potenza cannot be {@code null}");
    base = b;
    esponente = e;
  }

  /**
   * Retrieves the base of this node.
   *
   * @return The base {@code Nodo} of this node.
   */
  public Nodo getBase() {
    return base;
  }

  /**
   * Retrieves the exponent of this node.
   *
   * @return The exponent {@code Razionale} of this node.
   */
  public Razionale getEsponente() {
    return esponente;
  }

  /**
   * Returns the priority of this node.
   *
   * @return 2
   */
  @Override
  public int priority() {
    return 2;
  }

  @Override
  public String tipo() {
    return "^";
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
  public Iterator<Nodo> iterator() {
    List<Nodo> figli = new ArrayList<>(Arrays.asList(base, esponente));
    return Collections.unmodifiableList(figli).iterator();
  }
}
