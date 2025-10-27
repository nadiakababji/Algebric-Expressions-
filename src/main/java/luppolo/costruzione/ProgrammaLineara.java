package luppolo.costruzione;

import java.util.*;
import luppolo.*;

/**
 * A concrete class that provides functionalities to interpret and construct mathematical
 * expressions using linear programming instructions. It supports basic arithmetic operations (+, -,
 * *, /, ^) and manages operands such as integers, single-letter symbols, and complex expressions
 * defined through operand indices.
 */
public class ProgrammaLineara {

  /**
   * Constructs a mathematical expression represented by a linear program instruction.
   *
   * @param instructions The array of instructions defining the linear program.
   * @return The constructed mathematical expression as a Nodo object.
   * @throws IllegalArgumentException If any instruction is invalid or if no expressions are
   *     generated.
   * @throws NullPointerException If any string in instructions is {@code null};
   */
  public static Nodo fromLinearProgram(String[] instructions) {
    ArrayList<Nodo> expressions = new ArrayList<>();
    String firstPart;
    String[] parts;
    for (String instruction : instructions) {
      Objects.requireNonNull(instruction, "input not valid");

      parts = instruction.split(" ");
      firstPart = parts[0];

      if (firstPart.equals(".")) {
        String symbolOrNumber = parts[1];
        if (UtilityClass.isNumeric(symbolOrNumber)) {
          expressions.add(new Razionale(Long.parseLong(symbolOrNumber)));
        } else if (UtilityClass.isSmallLetter(symbolOrNumber)) {
          expressions.add(new Simbolo(symbolOrNumber.charAt(0)));
        } else {
          throw new IllegalArgumentException("Invalid symbol or number: " + symbolOrNumber);
        }
      } else if (UtilityClass.isOperator(firstPart)) {

        // recupero tutte le espressioni menzionate dopo l'operatore
        ArrayList<Nodo> operands = new ArrayList<>();
        for (int i = 1; i < parts.length; i++) {
          int index = Integer.parseInt(parts[i]);
          if (index < 0 || index >= expressions.size()) {
            throw new IllegalArgumentException("Invalid operand index: " + index);
          }
          operands.add(expressions.get(index));
        }

        // creo l'espressione
        Nodo newExpression;
        switch (firstPart) {
          case "+":
            newExpression = new Addizione(operands);
            break;

          case "-":
            for (int i = 1; i < operands.size(); i++) {
              operands.set(
                  i, new Moltiplicazione(Arrays.asList(new Razionale((long) -1), operands.get(i))));
            }
            newExpression = new Addizione(operands);
            break;

          case "*":
            newExpression = new Moltiplicazione(operands);
            break;

          case "/":
            // il numeratore che è il primo nodo di operands rimane lo stesso, gli altri devono
            // diventare nodi potenza ^(x,-1) e poi questi saranno i fattori della moltiplicazione
            for (int i = 1; i < operands.size(); i++) {
              operands.set(i, new Potenza(operands.get(i), new Razionale((long) -1)));
            }
            newExpression = new Moltiplicazione(operands);
            break;

          case "^":
            Potenza c =
                new Potenza(
                    operands.get(operands.size() - 2),
                    (Razionale)
                        (operands
                            .get(operands.size() - 1)
                            .accept(new luppolo.manipolazione.Semplificazione())));
            // calcolo la potenza tra ogni 2 operandi a partire dagli ultimi
            for (int i = operands.size() - 3; i >= 0; i--) {
              c =
                  new Potenza(
                      operands.get(i),
                      (Razionale) (c.accept(new luppolo.manipolazione.Semplificazione())));
            }
            newExpression = c;
            break;

          default:
            throw new IllegalArgumentException("Unknown operator: " + firstPart);
        }
        expressions.add(newExpression);
      } else {
        throw new IllegalArgumentException("Invalid instruction: " + instruction);
      }
    }
    if (expressions.isEmpty()) {
      throw new IllegalArgumentException("No expressions generated from instructions");
    }
    return expressions.get(expressions.size() - 1);
  }
}
