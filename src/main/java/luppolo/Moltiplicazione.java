package luppolo;

import java.util.*;

/**
 * An immutable class rapresenting a moltiplication node with children in a tree structure
 * wherewhere the children are ordered based on NodoComparator logic
 */
public class Moltiplicazione extends NodoInterno {

  /*
   * AF:An internal node in a tree structure with children nodes represented by the list `fattori`, where each element in the list `fattori` is a node.
   *
   * RI: fattori not {@code null}, doesn't contian {@code null} elements, has at least 2 elements and all elements are sorted according to NodoCompartor.
   */

  /** the node's chidren */
  private final List<Nodo> fattori;

  /**
   * Constructs an internal node with given children and sorts them using 'NodoComparator' logic.
   *
   * @param f List of child nodes to be assigned to this internal node
   * @throws IllegalArgumentException if f contains {@code null} values or if it has less then 2
   *     elments
   * @throws NullPointerException if the f is {@code null}
   */
  public Moltiplicazione(List<Nodo> f) {
    Objects.requireNonNull(f, "the list of child nodes cannot be null");
    if (f.contains(null))
      throw new IllegalArgumentException("List of child nodes cannot contain null elements");
    if (f.size() < 2)
      throw new IllegalArgumentException("List of child nodes must contain at least 2 elements");

    NodoComparator comparator = new NodoComparator();
    Collections.sort(f, comparator);
    fattori = Collections.unmodifiableList(f);
  }

  /**
   * Returns the priority of this node.
   *
   * @return 3
   */
  @Override
  public int priority() {
    return 3;
  }

  @Override
  public String tipo() {
    return "*";
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
    return Collections.unmodifiableList(fattori).iterator();
  }
}
