package luppolo.rappresentazione;

import java.util.*;
import luppolo.*;

/**
 * A visitor implementation designed to convert mathematical expressions represented by various
 * nodes (Nodo instances) into a linear string format. This linear representation simplifies the
 * visualization and understanding of complex expressions, such as additions, multiplications,
 * powers, rational numbers, and symbols. genreally for internal nodes the FormaLineare will return
 * "(type(child1, child2, ...))". for leaf nodes it will simplly return its string representation
 */
public class FormaLineare implements Visitor {

  /**
   * Visits a {@code Razionale} node and returns its string representation.
   *
   * @param razionale The {@code Razionale} node to visit. Must not be {@code null}.
   * @return The string representation of the {@code Razionale} node.
   * @throws NullPointerException if {@code razionale} is {@code null}.
   */
  @Override
  public String visit(Razionale razionale) {
    Objects.requireNonNull(razionale);
    return razionale.toString();
  }

  /**
   * Visits a {@code Simbolo} node and returns its string representation.
   *
   * @param simbolo The {@code Simbolo} node to visit. Must not be {@code null}.
   * @return The string representation of the {@code Simbolo} node.
   * @throws NullPointerException if {@code simbolo} is {@code null}.
   */
  @Override
  public String visit(Simbolo simbolo) {
    Objects.requireNonNull(simbolo);
    return simbolo.toString();
  }

  /**
   * Visits an {@code Addizione} node and constructs a linear string representation of the node and
   * its children in a nested format.
   *
   * @param addizione The {@code Addizione} node to visit. Must not be {@code null}.
   * @return A linear string representation of the {@code Addizione} node and its children.
   * @throws NullPointerException if {@code addizione} is {@code null}.
   */
  @Override
  public String visit(Addizione addizione) {
    Objects.requireNonNull(addizione);
    return fl(addizione);
  }

  /**
   * Visits a {@code Moltiplicazione} node and constructs a linear string representation of the node
   * and its children in a nested format.
   *
   * @param moltiplicazione The {@code Moltiplicazione} node to visit. Must not be {@code null}.
   * @return A linear string representation of the {@code Moltiplicazione} node and its children.
   * @throws NullPointerException if {@code moltiplicazione} is {@code null}.
   */
  @Override
  public String visit(Moltiplicazione moltiplicazione) {
    Objects.requireNonNull(moltiplicazione);
    return fl(moltiplicazione);
  }

  /**
   * Visits a {@code Moltiplicazione} node and constructs a linear string representation of the node
   * and its children in a nested format.
   *
   * @param potenza The {@code Moltiplicazione} node to visit. Must not be {@code null}.
   * @return A linear string representation of the {@code Moltiplicazione} node and its children.
   * @throws NullPointerException if {@code potenza} is {@code null}.
   */
  @Override
  public String visit(Potenza potenza) {
    Objects.requireNonNull(potenza);
    return fl(potenza);
  }

  /**
   * Constructs a linear string representation of a node and its children in a nested format. This
   * method recursively traverses the children of the given node to build a linear string that
   * represents the nested structure of the node and its children.
   *
   * @param n The node for which to construct the linear string representation.
   * @return A linear string representation of the node and its children, formatted as
   *     "(type(child1, child2, ...))".
   */
  private String fl(NodoInterno n) {
    StringBuilder res = new StringBuilder();
    Iterator<Nodo> it = n.iterator();
    res.append(n.tipo() + "(");
    while (it.hasNext()) {
      Nodo figlio = it.next();
      res.append(figlio.accept(this));
      if (it.hasNext()) {
        res.append(", ");
      }
    }
    res.append(")");
    return res.toString();
  }
}
