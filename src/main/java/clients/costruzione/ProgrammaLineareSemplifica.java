package clients.costruzione;

import java.util.*;
import luppolo.Nodo;
import luppolo.costruzione.ProgrammaLineara;
import luppolo.manipolazione.Semplificazione;

public class ProgrammaLineareSemplifica {

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    ArrayList<String> inputLines = new ArrayList<>();

    while (scanner.hasNextLine()) {
      inputLines.add(scanner.nextLine());
    }

    scanner.close();

    String[] instructions = inputLines.toArray(new String[0]);
    Nodo espressioneAlgebrica = ProgrammaLineara.fromLinearProgram(instructions);

    System.out.println(espressioneAlgebrica.accept(new Semplificazione()));
  }
}
