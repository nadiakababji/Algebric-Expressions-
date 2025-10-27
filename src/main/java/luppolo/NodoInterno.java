package luppolo;

import java.util.Iterator;
import luppolo.rappresentazione.FormaLineare;

/** Abstract class representing a generic internal node. */
public abstract class NodoInterno implements Nodo, Iterable<Nodo> {

  /**
   * Returns the operation type associated with the tree node for example: "+ " for nodes of type
   * Addizione
   *
   * @return operation type
   */
  public abstract String tipo();

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + tipo().hashCode();

    Iterator<Nodo> iterator = this.iterator();
    while (iterator.hasNext()) {
      result = prime * result + iterator.next().hashCode();
    }
    return result;
  }

  @Override
  public boolean equals(Object other) {
    if (other == null) return false;
    if (!(other instanceof NodoInterno)) return false;
    NodoInterno otherNodo = (NodoInterno) other;
    if (!this.tipo().equals(otherNodo.tipo())) return false;

    Iterator<Nodo> thisIterator = this.iterator();
    Iterator<Nodo> otherIterator = otherNodo.iterator();
    while (thisIterator.hasNext() && otherIterator.hasNext()) {
      if (!thisIterator.next().equals(otherIterator.next())) {
        return false;
      }
    }
    return true;
  }

  @Override
  public String toString() {
    return this.accept(new FormaLineare());
  }
}
