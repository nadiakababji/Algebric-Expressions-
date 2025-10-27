package luppolo;

import java.util.*;

/**
 * Concrete class, comparator for nodes implementing the {@code Nodo} interface. Nodes are compared
 * based on their class type. If nodes are of the same class, specific comparison methods are
 * invoked to determine their order. Nodes are ordered by their priority if they are not of the same
 * class.
 */
public class NodoComparator implements Comparator<Nodo> {

  /**
   * Compares two nodes {@code o1} and {@code o2} based on their class type. If nodes are of the
   * same class, specific comparison methods are invoked to determine their order. Nodes are ordered
   * by their priority if they are not of the same class.
   *
   * @param o1 the first node to compare
   * @param o2 the second node to compare
   * @return a negative integer, zero, or a positive integer if the first node is less than, equal
   *     to, or greater than the second node, respectively
   * @throws NullPointerException if o1 or o2 is {@code null}
   */
  @Override
  public int compare(Nodo o1, Nodo o2) {
    if (o1 == null || o2 == null) throw new NullPointerException("Cannot compare null objects");

    if (o1.getClass() == o2.getClass()) {
      if (o1 instanceof Razionale) {
        return compareRazionale((Razionale) o1, (Razionale) o2);
      } else if (o1 instanceof Simbolo) {
        return compareSimbolo((Simbolo) o1, (Simbolo) o2);
      } else if (o1 instanceof Addizione) {
        return compareNodiInterni((Addizione) o1, (Addizione) o2);
      } else if (o1 instanceof Moltiplicazione) {
        return compareNodiInterni((Moltiplicazione) o1, (Moltiplicazione) o2);
      } else if (o1 instanceof Potenza) {
        return compareNodiInterni((Potenza) o1, (Potenza) o2);
      }
    }
    return Integer.compare(o1.priority(), o2.priority());
  }

  /**
   * Compares two {@code Razionale} nodes based on their numerical values.
   *
   * @param r1 the first {@code Razionale} node to compare
   * @param r2 the second {@code Razionale} node to compare
   * @return a negative integer, zero, or a positive integer if the first Razionale node is less
   *     than, equal to, or greater than the second Razionale node, respectively
   */
  private int compareRazionale(Razionale r1, Razionale r2) {
    long diff = r1.num * r2.den - r2.num * r1.den;
    return Long.compare(diff, 0);
  }

  /**
   * Compares two {@code Simbolo} nodes based on their character values.
   *
   * @param s1 the first Simbolo node to compare
   * @param s2 the second Simbolo node to compare
   * @return a negative integer, zero, or a positive integer if the first Simbolo node is less than,
   *     equal to, or greater than the second Simbolo node, respectively
   */
  private int compareSimbolo(Simbolo s1, Simbolo s2) {
    return Character.compare(s1.x, s2.x);
  }

  /**
   * Compares two internal node types based on their childre node elements.
   *
   * @param n1 the first internal node to compare
   * @param n2 the second internal node to compare
   * @return a negative integer, zero, or a positive integer if the first internal node is less
   *     than, equal to, or greater than the second internal node, respectively
   */
  private int compareNodiInterni(NodoInterno n1, NodoInterno n2) {
    Iterator<Nodo> it1 = n1.iterator();
    Iterator<Nodo> it2 = n2.iterator();

    while (it1.hasNext() && it2.hasNext()) {
      Nodo f1 = it1.next();
      Nodo f2 = it2.next();

      int cmp = compare(f1, f2);
      if (cmp != 0) {
        return cmp;
      }
    }
    // If one iterator has remaining elements
    if (it1.hasNext()) {
      return 1; // a1 is longer than a2
    } else if (it2.hasNext()) {
      return -1; // a2 is longer than a1
    }

    return 0; // Both are equal
  }
}
