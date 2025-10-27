package luppolo.rappresentazione;

import java.util.*;
import luppolo.*;

/**
 * Concrete class implementing a visitor pattern, responsible for generating textual representations
 * (trees) of mathematical expressions for different node types such as Razionale, Simbolo,
 * Addizione, Moltiplicazione, and Potenza.
 *
 * <p>This visitor traverses the expression tree and produces a structured, indented text
 * representation of the tree.
 *
 * <p>Example of a tree representation:
 *
 * <p>Given a node f0 with children [f1, f2], and f having children [f3 and f4], the tree
 * representation would be:
 *
 * <p>f0.tipo() ├── f1 ╰── f2.tipo() ├── f3 ╰── f4
 */
public class Tree implements Visitor {

  /*
   * AF: prefix is the indentation needed to visualy organize nodes withing the tree structure.
   * RI: prefix field must consist only of spaces or "│".
   */

  /** The prefix for indentation */
  public final String prefix;

  /**
   * Constructs a Tree visitor with the specified prefix.
   *
   * @param prefix the prefix string used for indentation in the tree structure
   * @throws NullPointerException if the prefix is {@code null}
   * @throws IllegalArgumentException if the prefix contains characters other than space or "│"
   */
  public Tree(String prefix) {
    Objects.requireNonNull(prefix, "Prefix must not be null");
    for (int i = 0; i < prefix.length(); i++) {
      char ch = prefix.charAt(i);
      if (ch != ' ' && ch != '│') {
        throw new IllegalArgumentException("Invalid prefix format: " + ch);
      }
    }
    this.prefix = prefix;
  }

  /**
   * Visits a Razionale node and returns its string representation.
   *
   * @param razionale the Razionale node to visit
   * @return the string representation of the Razionale node
   */
  @Override
  public String visit(Razionale razionale) {
    Objects.requireNonNull(razionale);
    return razionale.toString() + "\n";
  }

  /**
   * Visits a Simbolo node and returns its string representation.
   *
   * @param simbolo the Simbolo node to visit
   * @return the string representation of the Simbolo node
   */
  @Override
  public String visit(Simbolo simbolo) {
    return simbolo.toString() + "\n";
  }

  /**
   * Visits an Addizione node and generates its tree representation.
   *
   * @param addizione the Addizione node to visit
   * @return the tree representation of the Addizione node
   */
  @Override
  public String visit(Addizione addizione) {
    return NodoInternoTree(prefix, addizione);
  }

  /**
   * Visits a Moltiplicazione node and generates its tree representation.
   *
   * @param moltiplicazione the Moltiplicazione node to visit
   * @return the tree representation of the Moltiplicazione node
   */
  @Override
  public String visit(Moltiplicazione moltiplicazione) {
    return NodoInternoTree(prefix, moltiplicazione);
  }

  /**
   * Visits a Potenza node and generates its tree representation.
   *
   * @param potenza the Potenza node to visit
   * @return the tree representation of the Potenza node
   */
  @Override
  public String visit(Potenza potenza) {
    return NodoInternoTree(prefix, potenza);
  }

  /**
   * Generates the tree representation of a given internal node .
   *
   * @param prefix the current prefix used for indentation
   * @param d the node to generate the tree representation for
   * @return the tree representation of the node
   */
  private static String NodoInternoTree(final String prefix, final Nodo d) {
    StringBuilder res = new StringBuilder();
    res.append(((NodoInterno) d).tipo()).append("\n");

    if (d instanceof NodoInterno) {
      Iterator<Nodo> it = ((NodoInterno) d).iterator();
      while (it.hasNext()) {
        final Nodo e = it.next();
        String newPrefix = prefix + (it.hasNext() ? "│   " : "    ");
        res.append(prefix)
            .append(it.hasNext() ? "├── " : "╰── ")
            .append(e.accept(new Tree(newPrefix)));
      }
    }
    return res.toString();
  }
}
