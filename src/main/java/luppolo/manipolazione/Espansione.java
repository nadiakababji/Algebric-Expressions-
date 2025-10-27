package luppolo.manipolazione;

import java.util.*;
import luppolo.*;

/**
 * A concrete class that aims to simplify and transform complex expressions into their expanded
 * forms, It utilizes the Visitor pattern (VisitorNodo interface) to traverse different types of
 * nodes (Razionale, Simbolo, Addizione, Moltiplicazione, Potenza) and applies expanding rules
 * accordingly
 */
public class Espansione implements VisitorNodo {

  /**
   * Visits and returns the given rational node without any modifications.
   *
   * @param razionale The rational node to visit.
   * @return The same rational node as input.
   * @throws NullPointerException If {@code razionale} is {@code null}.
   */
  @Override
  public Nodo visit(Razionale razionale) {
    Objects.requireNonNull(razionale);
    return razionale;
  }

  /**
   * Visits and returns the given symbol node without any modifications.
   *
   * @param simbolo The symbol node to visit.
   * @return The same symbol node as input.
   * @throws NullPointerException If {@code simbolo} is {@code null}.
   */
  @Override
  public Nodo visit(Simbolo simbolo) {
    Objects.requireNonNull(simbolo);
    return simbolo;
  }

  /**
   * Visits the addition node and expands it by expanding its children nodes.
   *
   * @param addizione The addition node to visit and expand.
   * @return the expended node.
   * @throws NullPointerException If {@code addizione} is {@code null}.
   */
  @Override
  public Nodo visit(Addizione addizione) {
    Objects.requireNonNull(addizione);

    ArrayList<Nodo> arr = new ArrayList<Nodo>();
    Iterator<Nodo> it = addizione.iterator();
    while (it.hasNext()) {
      arr.add((it.next()).accept(this));
    }
    return new Addizione(arr);
  }

  /**
   * Handles the combination of two internal nodes (`NodoInterno`), performing specific checks and
   * manipulations based on their types. Depending on whether the nodes are instances of `Addizione`
   * or `Moltiplicazione`, the method applies the appropriate arithmetic rules, such as distributing
   * multiplication over addition.
   *
   * @param f1 The first internal node.
   * @param f2 The second internal node.
   * @param first A boolean flag indicating if this is the first operation in a sequence.
   * @return A new `Nodo` that represents the result of combining `f1` and `f2` according to the
   *     specified rules.
   */
  private Nodo controlli(Nodo f1, Nodo f2, boolean first) {
    Objects.requireNonNull(f1, "The first node cannot be null");
    Objects.requireNonNull(f2, "The second node cannot be null");

    boolean isF1Addizione = f1 instanceof Addizione;
    boolean isF2Addizione = f2 instanceof Addizione;

    if (isF1Addizione && !isF2Addizione) {
      return handleSingleAddizione((Addizione) f1, f2);
    } else if (!isF1Addizione && isF2Addizione) {
      return handleSingleAddizione((Addizione) f2, f1);
    } else if (isF1Addizione && isF2Addizione) {
      return handleDoubleAddizione((Addizione) f1, (Addizione) f2);
    } else if (f1 instanceof Moltiplicazione && !isF2Addizione && !first) {
      return handleMoltiplicazioneWithNonAddizione((Moltiplicazione) f1, f2);
    } else {
      return new Moltiplicazione(Arrays.asList(f1, f2));
    }
  }

  /**
   * Handles the multiplication of an Addizione node with another Nodo. This method distributes the
   * multiplication over the addition.
   *
   * @param addizione the Addizione node to be multiplied
   * @param other the other Nodo to multiply with the Addizione node
   * @return a new Addizione node representing the result of the distributed multiplication
   */
  private Nodo handleSingleAddizione(Addizione addizione, Nodo other) {
    ArrayList<Nodo> figliAdd = new ArrayList<>();
    Iterator<Nodo> it = addizione.iterator();
    while (it.hasNext()) {
      Nodo next = it.next();
      figliAdd.add(new Moltiplicazione(Arrays.asList(next, other)));
    }
    return new Addizione(figliAdd);
  }

  /**
   * Handles the multiplication of two Addizione (addition) nodes. This method performs the
   * distributive property of multiplication over addition for both Addizione nodes.
   *
   * @param add1 the first Addizione node
   * @param add2 the second Addizione node
   * @return a new Addizione node representing the result of the distributed multiplication
   */
  private Nodo handleDoubleAddizione(Addizione add1, Addizione add2) {
    ArrayList<Nodo> figliAdd = new ArrayList<>();
    Iterator<Nodo> it1 = add1.iterator();
    while (it1.hasNext()) {
      Nodo next1 = it1.next();
      Iterator<Nodo> it2 = add2.iterator();
      while (it2.hasNext()) {
        figliAdd.add(new Moltiplicazione(Arrays.asList(next1, it2.next())));
      }
    }
    return new Addizione(figliAdd);
  }

  /**
   * Handles the multiplication of a Moltiplicazione (multiplication) node with another Nodo (node)
   * that is not an Addizione (addition) node. This method combines the factors of the
   * Moltiplicazione node with the other node.
   *
   * @param moltiplicazione the Moltiplicazione node to be multiplied
   * @param other the other Nodo to multiply with the Moltiplicazione node
   * @return a new Moltiplicazione node representing the result of the multiplication
   */
  private Nodo handleMoltiplicazioneWithNonAddizione(Moltiplicazione moltiplicazione, Nodo other) {
    ArrayList<Nodo> fattoriSottoespressioni = new ArrayList<>();
    Iterator<Nodo> it1 = moltiplicazione.iterator();
    while (it1.hasNext()) {
      fattoriSottoespressioni.add(it1.next());
    }
    fattoriSottoespressioni.add(other);
    return new Moltiplicazione(fattoriSottoespressioni);
  }

  /**
   * Visits the multiplication node and expands it according to distributive property if applicable.
   * It iterates through its factors, applies expansion recursively, and performs necessary checks
   * and manipulations based on the rules provided. Example:
   *
   * <ul>
   *   <li>3*(x+y) expands to 3*x + 3*y
   *   <li>(a+b)*(c+d) expands to a⋅c + a⋅d + b⋅c + b⋅d
   *   <li>(a+b)*(c+d)*(e+f) expands to e*(a*c) + e*(a*d) + e*(b*c) + e*(b*d)) + f*(a*c) + f*(a*d) +
   *       f*(b*c) + f*(b*d))
   * </ul>
   *
   * @param moltiplicazione The multiplication node to visit and expand.
   * @return the expended node.
   * @throws NullPointerException If {@code moltiplicazione} is {@code null}.
   */
  @Override
  public Nodo visit(Moltiplicazione moltiplicazione) {
    Objects.requireNonNull(moltiplicazione);

    ArrayList<Nodo> arr = new ArrayList<Nodo>();
    Iterator<Nodo> it = moltiplicazione.iterator();

    while (it.hasNext()) {
      arr.add(it.next().accept(this));
    }

    Nodo f1 = controlli(arr.get(0), arr.get(1), true);
    Nodo f2;

    // arr deve avere almeno due elementi
    for (int i = 2; i < arr.size(); i++) {
      f2 = arr.get(i);
      f1 = controlli(f1, f2, false);
    }

    return f1;
  }

  /**
   * Visits the power node and expands it based on the rules of exponentiation. Power nodes with
   * exponent in the form p/q are expanded as the product of the base itself |p| times, raised to
   * the power of p/|p|q: For example, (x+y)^(-2/3) is expanded as (x*x + x*y + x*y + y*y)^(-1/3).
   * If the exponent is 0, the power is expanded as 1. If the exponent is 1, it is expanded as the
   * base itself.
   *
   * @param potenza The power node to visit and expand.
   * @return the expanded node.
   * @throws NullPointerException If {@code potenza} is {@code null}.
   */
  @Override
  public Nodo visit(Potenza potenza) {
    Objects.requireNonNull(potenza);

    ArrayList<Nodo> MoltBase = new ArrayList<Nodo>();

    Nodo base = potenza.getBase().accept(this);
    Razionale esponente = potenza.getEsponente();
    long esponenteNum = esponente.num;
    long esponenteDen = esponente.den;
    Nodo res = new Potenza(base, potenza.getEsponente());

    if (esponente.equals(new Razionale(1))) {
      return base;
    } else if (esponente.equals(new Razionale(0))) {
      res = new Razionale(1);
    } else if (Math.abs(esponenteNum) > 1) {
      for (int i = 0; i < (int) Math.abs(esponenteNum); i++) {
        MoltBase.add(base);
      }

      Nodo newBase = new Moltiplicazione(MoltBase).accept(this);
      if (esponenteDen == 1 && esponenteNum > 1) {
        res = newBase;
      } else {
        res =
            new Potenza(
                newBase, new Razionale(Math.abs(esponenteNum) / esponenteNum, esponenteDen));
      }
    }
    return res;
  }
}
