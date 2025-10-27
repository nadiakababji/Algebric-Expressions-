package luppolo.manipolazione;

import java.util.*;
import java.util.Map.Entry;
import luppolo.*;

/**
 * A concrete class, provids functionality to simplify mathematical expressions It utilizes the
 * Visitor pattern (VisitorNodo interface) to traverse different types of nodes (Razionale, Simbolo,
 * Addizione, Moltiplicazione, Potenza) and applies semplification rules accordingly
 */
public class Semplificazione implements VisitorNodo {

  /**
   * Visits a rational number node and ensures it's not {@code null}.
   *
   * @param razionale The rational number node to visit.
   * @return The same rational node.
   * @throws NullPointerException If the rational node is {@code null}.
   */
  @Override
  public Nodo visit(Razionale razionale) {
    Objects.requireNonNull(razionale);
    return razionale;
  }

  /**
   * Visits a symbol node and ensures it's not {@code null}.
   *
   * @param simbolo The symbol node to visit.
   * @return The same symbol node.
   * @throws NullPointerException If the symbol node is {@code null}.
   */
  @Override
  public Nodo visit(Simbolo simbolo) {
    Objects.requireNonNull(simbolo);
    return simbolo;
  }

  /**
   * Visits an addition node, simplifies its internal nodes, and returns the simplified addition
   * node following these rules:
   *
   * <ul>
   *   <li>Substitutes nested addition nodes with their addends.
   *   <li>Collects rational terms into a single rational term (which may be omitted if zero).
   *   <li>Combines identical non-rational terms into a single Moltiplicazione term.
   * </ul>
   *
   * @param addizione The addition node to visit and simplify.
   * @return The simplified addition node after applying simplification rules.
   * @throws NullPointerException If the addition node is {@code null}.
   */
  @Override
  public Nodo visit(Addizione addizione) {
    Objects.requireNonNull(addizione);

    ArrayList<Nodo> figliSemplificati = semplificaNodiInterni(addizione);
    Razionale resRazionale = new Razionale();
    Map<Nodo, Razionale> occMap = new HashMap<>();
    Razionale ZERO = new Razionale(0);
    Razionale UNO = new Razionale(1);

    for (Nodo nodo : figliSemplificati) {
      if (nodo instanceof Razionale) {
        resRazionale = resRazionale.somma((Razionale) nodo);

      } else if (nodo
          instanceof
          Moltiplicazione) { // controllo il razionale di ogni nodo per sapere quanto aggiungere
        // alla mappa di fattore
        Iterator<Nodo> it2 = ((Moltiplicazione) nodo).iterator();
        Nodo next2 = it2.next();
        if (next2
            instanceof
            Razionale) { // razionale*moltiplicazione dei restanti figli o razionale*unico figlio
          // restante
          ArrayList<Nodo> figliMolt = new ArrayList<>();
          while (it2.hasNext()) {
            figliMolt.add(it2.next());
          }

          Nodo restoMolt;
          if (figliMolt.size() > 1) {
            restoMolt = new Moltiplicazione(figliMolt);
          } else {
            restoMolt = figliMolt.get(0);
          }
          occMap.put(restoMolt, occMap.getOrDefault(restoMolt, ZERO).somma((Razionale) next2));
        } else {
          occMap.put(nodo, occMap.getOrDefault(nodo, ZERO).somma(UNO));
        }
      } else {
        occMap.put(nodo, occMap.getOrDefault(nodo, ZERO).somma(UNO));
      }
    }

    ArrayList<Nodo> figli = new ArrayList<>();
    for (Entry<Nodo, Razionale> entry : occMap.entrySet()) {
      if (!entry.getValue().equals(UNO)) {
        Moltiplicazione e =
            new Moltiplicazione(new ArrayList<>(Arrays.asList(entry.getKey(), entry.getValue())));
        if (entry.getKey() instanceof Moltiplicazione) {
          ArrayList<Nodo> figliMolt = new ArrayList<>();
          figliMolt.add(entry.getValue()); // Aggiungo il razionale
          Iterator<Nodo> it =
              ((Moltiplicazione) entry.getKey()).iterator(); // aggiungo i restanti figli
          while (it.hasNext()) {
            figliMolt.add(it.next());
          }
          figli.add(new Moltiplicazione(figliMolt));
          continue;
        }
        figli.add(e);

      } else {
        figli.add(entry.getKey());
      }
    }
    // aggiungo alla somma il razionale totale
    if (resRazionale.num != 0) {
      figli.add(resRazionale);
    }

    if (figli.size() > 1) {
      Addizione add = new Addizione(figli);
      return add;
    } else if (figli.size() == 1) {
      return figli.get(0);
    } else {
      return ZERO;
    }
  }

  /**
   * Simplifies all internal nodes of a given node with children using the `Semplificazione`
   * visitor. It iterates through the children of the node, applies simplification recursively, and
   * collects the simplified children nodes into an ArrayList.
   *
   * <p>For each child node:
   *
   * <ul>
   *   <li>If the child is an instance of the same class as the input node (`n`), simplifies its
   *       children and adds them to the list (`figliSemplificati`).
   *   <li>Otherwise, simplifies the child directly and adds it to the list (`figliSemplificati`).
   * </ul>
   *
   * @param n The internal node with children to be simplified.
   * @return An ArrayList containing the simplified child nodes of the input node `n`.
   */
  private ArrayList<Nodo> semplificaNodiInterni(NodoInterno n) {
    ArrayList<Nodo> figliSemplificati = new ArrayList<>();
    Iterator<Nodo> it = n.iterator();
    while (it.hasNext()) {
      Nodo next = it.next().accept(this); // semplifico tutti i figli
      if (next.getClass()
          == n.getClass()) { // aggiungo i figli semplificati dei nodi che appartengono alla stessa
        // classe di n
        Iterator<Nodo> it1 = ((NodoInterno) next).iterator();
        while (it1.hasNext()) {
          Nodo next1 = it1.next();
          figliSemplificati.add(next1.accept(this));
        }
      } else {
        figliSemplificati.add(next.accept(this));
      }
    }
    return figliSemplificati;
  }

  /**
   * Visits a multiplication node, simplifies its internal nodes, and returns the simplified
   * multiplication node following these rules:
   *
   * <ul>
   *   <li>Substitutes nested multiplication nodes with their factors.
   *   <li>Combines identical non-rational factors by summing their exponents.
   *   <li>Collects rational factors into a single rational factor (which may be omitted if one).
   * </ul>
   *
   * @param mult The multiplication node to visit and simplify.
   * @return The simplified multiplication node after applying simplification rules and
   *     transformations.
   * @throws NullPointerException If the multiplication node is {@code null}.
   */
  @Override
  public Nodo visit(Moltiplicazione mult) {
    Objects.requireNonNull(mult);
    ArrayList<Nodo> figliMul = semplificaNodiInterni(mult);
    // Razionali sum che verra aggiunto alla fine
    Razionale rProd = new Razionale(1);
    Map<Nodo, Razionale> occMap = new HashMap<>();

    Razionale ZERO = new Razionale(0);
    Razionale UNO = new Razionale(1);
    boolean usedRazionale =
        false; // per sapere se aggiungere un eventuale razionale di valore 1 oppure no
    for (Nodo nodo : figliMul) {
      if (nodo instanceof Razionale) {
        usedRazionale = true;
        rProd = rProd.moltRazionale((Razionale) nodo);

      } else if (nodo instanceof Potenza) {
        Potenza p = (Potenza) (nodo);
        occMap.put(p.getBase(), occMap.getOrDefault(p.getBase(), ZERO).somma(p.getEsponente()));

      } else {
        occMap.put(nodo, occMap.getOrDefault(nodo, ZERO).somma(UNO));
      }
    }

    ArrayList<Nodo> figli = new ArrayList<>();
    for (Entry<Nodo, Razionale> entry : occMap.entrySet()) {
      Nodo n = entry.getKey();
      if (entry.getValue().num != 0) {

        Nodo e = new Potenza(n, entry.getValue()).accept(this);
        if (e instanceof Razionale) {
          rProd = rProd.moltRazionale((Razionale) e);
          continue;
        }
        figli.add(e.accept(this));

      } else {
        figli.add(n);
      }
    }

    if (rProd.equals(ZERO)) {
      figli = new ArrayList<>();
    } else if (!rProd.equals(UNO)) {
      figli.add(rProd);
    }

    if (figli.size() > 1) {
      return new Moltiplicazione(figli);
    } else if (figli.size() == 0) {
      if (usedRazionale) {
        return rProd;
      } else {
        return ZERO;
      }
    } else {
      return figli.get(0);
    }
  }

  /**
   * Visits a power node, simplifies its base and exponent, and returns the simplified power node.
   * following these rules:
   *
   * <ul>
   *   <li>If the base is a power node, treats the base as its base and the exponent as the product
   *       of their exponents.
   *   <li>If the exponent is 1, the simplified result is the base.
   *   <li>If the base is rational, considers the exponent:
   *   <li>If both base and exponent are 0 (indeterminate form 0^0), throws an error.
   *   <li>Result is 0 if the base is 0, or 1 if the exponent is 0.
   *   <li>If the exponent is negative, flips numerator and denominator and changes the exponent
   *       sign.
   *   <li>If base is in the form p/q and exponent is s/r > 0, simplifies using integers t and u
   *       such that t = p^(1/r) and u = q^(1/r): Result is rational number t^s / u^s if such
   *       integers exist; otherwise, it's (p/q)^(s/r).
   *   <li>If exponent is 0 at this stage (indeterminate form b^0), throws an error.
   *   <li>Final simplified result is the base raised to the exponent.
   * </ul>
   *
   * @param potenza The power node to visit and simplify.
   * @return The simplified power node after applying simplification rules and transformations.
   * @throws ArithmeticException If there are specific errors encountered during simplification,
   *     such as 0^0 or base^0 indeterminate forms.
   * @throws NullPointerException If the power node is {@code null}.
   */
  @Override
  public Nodo visit(Potenza potenza) {
    Objects.requireNonNull(potenza);

    Nodo base = potenza.getBase().accept(this);
    Razionale esponente = (Razionale) potenza.getEsponente().accept(this);

    Razionale ZERO = new Razionale(0);
    Razionale UNO = new Razionale(1);

    if (base instanceof Potenza) { // Potenza(base.base, esponente 1 *esponente 2)
      Potenza p1 = (Potenza) base;
      base = p1.getBase();
      esponente = (esponente).moltRazionale(p1.getEsponente());
    }

    if (esponente.num == esponente.den) { // se potenza è 1 allora il risultato è la base
      return base;
    }

    if (base instanceof Razionale) {
      if (((Razionale) base).num == 0) {
        if (esponente.num == 0) { // 0^0
          throw new ArithmeticException("0^0 non è permesso");
        } else { // caso 0^qualcosa
          return ZERO;
        }
      } else { // base diversa da 0
        if (esponente.num == 0) {
          return UNO;
        } else if (esponente.num < 0) {
          base = new Razionale(((Razionale) base).den, ((Razionale) base).num);
          esponente = esponente.moltRazionale(new Razionale(-1));
        }
      }

      if ((((Razionale) base).num > 0 || esponente.den % 2 != 0)
          && ((Razionale) base).radice(esponente.den)
              != null) { // caso in cui si può semplificare la base razionale
        return ((Razionale) base).radice(esponente.den).power(new Razionale(esponente.num));
      }
    }
    if (esponente.num == 0)
      throw new ArithmeticException("l'esponenete dopo la semplificazine è 0");
    return new Potenza(base, esponente);
  }
}
