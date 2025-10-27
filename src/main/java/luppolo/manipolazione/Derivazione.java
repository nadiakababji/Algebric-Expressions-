package luppolo.manipolazione;

import java.util.*;
import luppolo.*;

/**
 * An immutable concrete class designed to compute derivatives using the Visitor pattern
 * (VisitorNodo interface). It ensures that derivatives are computed correctly based on the type of
 * mathematical expression node.
 */
public class Derivazione implements VisitorNodo {
  /** derivation variable */
  private final char var;

  /*
   * AF:  The derivation will be performed with respect to the variable {@code var}
   * RI: 'a'<=var<='z'
   */

  /**
   * Constructs a Derivazione object with the specified variable.
   *
   * @param v the variable on which to perform the derivation, must be a lowercase letter between
   *     'a' and 'z'
   * @throws IllegalArgumentException if the variable is not a lowercase letter between 'a' and 'z'
   */
  public Derivazione(char v) {
    if (v > 'z' || v < 'a')
      throw new IllegalArgumentException(
          "The variable on which to perform derivation must be a between 'a' and 'z'");
    var = v;
  }

  /**
   * Visits the rational node and returns its derivative, which is 0.
   *
   * @param razionale The rational node to visit.
   * @return A rational node representing the derivative, which is 0.
   * @throws NullPointerException If {@code razionale} is {@code null}.
   */
  @Override
  public Nodo visit(Razionale razionale) {
    Objects.requireNonNull(razionale);
    return new Razionale(0);
  }

  /**
   * Visits the symbol node and returns its derivative. If the symbol matches the derivative
   * variable, returns 1; otherwise, returns 0.
   *
   * @param simbolo The symbol node to visit.
   * @return A rational node representing the derivative.
   * @throws NullPointerException If {@code simbolo} is {@code null}.
   */
  @Override
  public Nodo visit(Simbolo simbolo) {
    Objects.requireNonNull(simbolo);
    if (simbolo.x == var) {
      return new Razionale(1);
    }
    return new Razionale(0);
  }

  /**
   * Visits the addition node and returns its derivative. The derivative of an addition node is the
   * sum of derivatives of its operands.
   *
   * @param addizione The addition node to visit.
   * @return An addition node representing the derivative.
   * @throws NullPointerException If {@code addizione} is {@code null}.
   */
  @Override
  public Nodo visit(Addizione addizione) {
    Objects.requireNonNull(addizione);

    ArrayList<Nodo> d = new ArrayList<Nodo>();
    Iterator<Nodo> it = addizione.iterator();
    while (it.hasNext()) {
      d.add(it.next().accept(this));
    }

    return new Addizione(d);
  }

  /**
   * Visits the multiplication node and returns its derivative. The derivative of a multiplication
   * node is computed using derivation rule of the product.
   *
   * @param moltiplicazione The multiplication node to visit.
   * @return An addition node representing the derivative.
   * @throws NullPointerException If {@code moltiplicazione} is {@code null}.
   */
  @Override
  public Nodo visit(Moltiplicazione moltiplicazione) {
    ArrayList<Nodo> ds = new ArrayList<Nodo>();
    ArrayList<Nodo> arr = new ArrayList<Nodo>();
    Iterator<Nodo> it = moltiplicazione.iterator();
    while (it.hasNext()) {
      arr.add(it.next());
    }

    for (int i = 0; i < arr.size(); i++) {
      ArrayList<Nodo> dm = new ArrayList<Nodo>();
      for (int j = 0; j < arr.size(); j++) {
        if (j != i) {
          dm.add(arr.get(j));
        } else { // caso i=j voglio derivata
          dm.add(arr.get(j).accept(this));
        }
      }
      ds.add(new Moltiplicazione(dm));
    }
    return new Addizione(ds);
  }

  /**
   * Visits the power node and returns its derivative. The derivative of a power node is computed
   * using the derivation rule for a power.
   *
   * @param potenza The power node to visit.
   * @return A multiplication node representing the derivative.
   * @throws NullPointerException If {@code potenza} is {@code null}.
   */
  @Override
  public Nodo visit(Potenza potenza) {
    Objects.requireNonNull(potenza);
    Nodo base = potenza.getBase();
    Razionale esp = potenza.getEsponente();
    ArrayList<Nodo> d = new ArrayList<Nodo>();
    d.add(esp);
    d.add(new Potenza(base, esp.somma(new Razionale(-1))));
    d.add(base.accept(this));
    return new Moltiplicazione(d);
  }
}
