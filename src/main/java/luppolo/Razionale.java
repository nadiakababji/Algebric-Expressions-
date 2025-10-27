package luppolo;

/**
 * An immutable concrete class that represents a rational number node. This class ensures that
 * rational numbers are manipulated in their simplest form.
 */
public class Razionale implements Nodo {

  /*
   * AF: A rational number represented by the fraction `num / den`, where:
   *  -num is the numerator
   *  -den is the denominator
   *  -The fraction is in its most simplified form
   * A negative Razionale is one that has a negative num
   *
   * RI:
   * - den>0
   * -The fraction `num / den` is in its simplest form
   */

  /** The numerator of <em>Razionale</em> */
  public final long num;

  /** The numerator of <em>Razionale</em> */
  public final long den;

  /** Constructs a {@code Razionale} that rappresents 0 */
  public Razionale() {
    num = 0;
    den = 1;
  }

  /**
   * Constructs a {@code Razionale} with only the numerator
   *
   * @param n the numerator to be rapresented
   */
  public Razionale(long n) {
    num = n;
    den = 1;
  }

  /**
   * Constructs a {@code Razionale} that represents a fraction.
   *
   * @param n the numerator
   * @param d the denominator
   * @throws IllegalArgumentException if the denominator is zero
   */
  public Razionale(long n, long d) {
    if (d == 0) throw new IllegalArgumentException("Denominator cannot be zero");
    long mcd = mcd(Math.abs(n), Math.abs(d));
    if (d < 0) {
      num = -n / mcd;
      den = -d / mcd;
    } else {
      num = n / mcd;
      den = d / mcd;
    }
  }

  /**
   * Calculates the greatest common divisor of two numbers.
   *
   * @param a the first number
   * @param b the second number
   * @return the GCD of {@code a} and {@code b}
   */
  private long mcd(long a, long b) {
    if (b == 0) return a;
    return mcd(b, a % b);
  }

  /**
   * Multiplies this {@code Razionale} with another {@code Razionale}.
   *
   * @param otherF the other {@code Razionale} to multiply with
   * @return the moltiplication of the two {@code Razionale} numbers
   */
  public Razionale moltRazionale(Razionale otherF) {
    if (this.num == 0 || otherF.num == 0) return new Razionale();
    if (this.num == this.den) return otherF;
    if (otherF.num == otherF.den) return this;
    long mn = this.num * otherF.num;
    long md = this.den * otherF.den;
    return new Razionale(mn, md);
  }

  /**
   * Adds this {@code Razionale} to another {@code Razionale}
   *
   * @param otherF the other {@code Razionale} to add
   * @return the sum of the two {@code Razionale}
   */
  public Razionale somma(Razionale otherF) {
    if (this.num == 0) return new Razionale(otherF.num, otherF.den * this.den);
    if (otherF.num == 0) return new Razionale(this.num, otherF.den * this.den);
    long sd = otherF.den * this.den;
    long sn = (this.num * otherF.den) + (this.den * otherF.num);
    return new Razionale(sn, sd);
  }

  /**
   * Calculates the r-th root of the rational number.
   *
   * @param r The root to calculate.
   * @return The r-th root of the {@code Razionale} number if it exists, otherwise {@code null}.
   * @throws IllegalArgumentException If attempting to find an even root of a negative number.
   */
  public Razionale radice(long r) {
    if (this.num < 0 && r % 2 == 0) {
      throw new IllegalArgumentException("Non esiste una radice reale di un numero negativo");
    }

    double rootNum = Math.pow(Math.abs(this.num), 1.0 / r);
    double rootDen = Math.pow(this.den, 1.0 / r);

    long intRootNum = Math.round(rootNum);
    long intRootDen = Math.round(rootDen);

    double epsilon = 1e-9; // tolleranza per il confronto delle radici

    if (Math.abs(Math.pow(intRootNum, r) - Math.abs(this.num)) < epsilon
        && Math.abs(Math.pow(intRootDen, r) - this.den) < epsilon) {
      if (this.num < 0 && r % 2 != 0) {
        intRootNum = -intRootNum; // radice dispari di un numero negativo
      }
      return new Razionale(intRootNum, intRootDen);
    }
    return null; // Indica che non esiste una radice intera
  }

  /**
   * Raises the {@code Razionale} number to the power of the given exponent.
   *
   * @param exponent Exponent to raise the rational number to.
   * @return Result of raising the {@code Razionale} number to the exponent as a simplified rational
   *     number.
   */
  public Razionale power(Razionale exponent) {
    long oldNum = this.num;
    long oldDen = this.den;

    if (exponent.num < 0) {
      oldNum = this.den;
      oldDen = this.num;
    }

    long newNum = (long) Math.pow(oldNum, Math.abs(exponent.num / exponent.den));
    long newDen = (long) Math.pow(oldDen, Math.abs(exponent.num / exponent.den));
    return new Razionale(newNum, newDen);
  }

  /**
   * Returns the priority of this node.
   *
   * @return 0
   */
  @Override
  public int priority() {
    return 0;
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
  public int hashCode() {
    final int prime = 35;
    int result = 1;
    result = prime * result + Long.hashCode(num);
    result = prime * result + Long.hashCode(den);
    return result;
  }

  @Override
  public boolean equals(Object other) {
    if (other == null) return false;
    if (!(other instanceof Razionale)) return false;
    Razionale otherNodo = (Razionale) other;
    if (num != otherNodo.num || den != otherNodo.den) return false;
    return true;
  }

  @Override
  public String toString() {
    return String.valueOf(num) + (den == 1 ? "" : "/" + String.valueOf(den));
  }
}
