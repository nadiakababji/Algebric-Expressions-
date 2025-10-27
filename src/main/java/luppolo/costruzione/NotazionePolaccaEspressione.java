package luppolo.costruzione;

import java.util.*;
import luppolo.*;

/**
 * The NotazionePolaccaEspressione class provides functionality to parse and construct mathematical
 * expressions in Reverse Polish Notation (RPN) into expressions (Nodo objects). It supports basic
 * arithmetic operations (+, -, *, ^, /) and handles operands such as integers and single-letter
 * symbols.
 */
public class NotazionePolaccaEspressione {

  /**
   * Parses a mathematical expression in Reverse Polish Notation (RPN) and constructs a
   * corresponding expression tree (Nodo object). The method supports basic arithmetic operations
   * (+, -, *, ^, /) and handles operands such as integers and single-letter symbols.
   *
   * @param input the RPN expression as a string, with elements separated by spaces
   * @return the root Nodo of the constructed expression tree
   * @throws NullPointerException if any element in the input array is {@code null}
   * @throws IllegalStateException if there are not enough operands for an operator
   * @throws IllegalArgumentException if the input contains an unrecognized operator or operand
   */
  public static Nodo fromPolocaa(String input) {
    Stack<Nodo> stack = new Stack<>();
    // Split the string by spaces
    String[] inputArray = input.split(" ");
    Nodo second;
    Nodo first;

    // Use a for-each loop to iterate over the input array
    for (int i = inputArray.length - 1; i >= 0; i--) {

      String elem = Objects.requireNonNull(inputArray[i], "input cannot contian null");
      if (UtilityClass.isNumeric(elem)) {
        stack.push(new Razionale(Long.parseLong(elem)));

      } else if (UtilityClass.isSmallLetter(elem)) {
        stack.push(new Simbolo(elem.charAt(0)));

      } else if (UtilityClass.isOperator(elem)) {
        if (stack.size() >= 2) {
          second = stack.pop();
          first = stack.pop();
        } else {
          throw new IllegalStateException("Not enought operands");
        }

        switch (elem) {
          case "+":
            stack.push(new Addizione(Arrays.asList(second, first)));
            break;

          case "*":
            stack.push(new Moltiplicazione(Arrays.asList(second, first)));
            break;

          case "^":
            // caso in cui l'esponente non è razionale
            if (!(first instanceof Razionale)) {
              Razionale e = (Razionale) (first.accept(new luppolo.manipolazione.Semplificazione()));
              stack.push(new Potenza(second, e));
            } else {
              // caso esponente razionale
              stack.push(new Potenza(second, (Razionale) first));
            }
            break;

          case "-":
            stack.push(
                new Addizione(
                    Arrays.asList(
                        second, new Moltiplicazione(Arrays.asList(new Razionale(-1), first)))));
            break;

          case "/":
            stack.push(
                new Moltiplicazione(Arrays.asList(second, new Potenza(first, new Razionale(-1)))));
            break;
        }
      } else {
        throw new IllegalArgumentException("input not identified");
      }
    }
    return stack.firstElement();
  }
}
