package luppolo.costruzione;

import java.util.Objects;

/** A utility class with static methods */
public class UtilityClass {
  /**
   * Checks if a given string represents a numeric value.
   *
   * @param str The string to check.
   * @return {@code true} if the string is numeric, {@code false} otherwise.
   * @throws NullPointerException if {@code str} is {@code null}
   */
  public static boolean isNumeric(String str) {
    Objects.requireNonNull(str);
    try {
      Integer.parseInt(str);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  /**
   * Checks if a given string represents a single-small letter symbol.
   *
   * @param str The string to check.
   * @return {@code true} if the string is a single letter, {@code false} otherwise.
   * @throws NullPointerException if {@code str} is {@code null}
   */
  public static boolean isSmallLetter(String str) {
    Objects.requireNonNull(str);
    return str != null
        && str.length() == 1
        && Character.isLetter(str.charAt(0))
        && (str.charAt(0) <= 'z' || str.charAt(0) >= 'a');
  }

  /**
   * Checks if a given string represents a valid operator (+, -, *, /, ^).
   *
   * @param str The string to check.
   * @return {@code true} if the string is a valid operator, {@code false} otherwise.
   * @throws NullPointerException if {@code str} is {@code null}
   */
  public static boolean isOperator(String str) {
    Objects.requireNonNull(str);
    return str.equals("+")
        || str.equals("-")
        || str.equals("*")
        || str.equals("/")
        || str.equals("^");
  }
}
