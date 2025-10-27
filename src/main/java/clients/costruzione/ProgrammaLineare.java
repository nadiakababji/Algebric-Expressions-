package clients.costruzione;

import java.util.*;
import luppolo.costruzione.ProgrammaLineara;


public class ProgrammaLineare {

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    ArrayList<String> inputLines = new ArrayList<>();

    while (scanner.hasNextLine()) {
      inputLines.add(scanner.nextLine());
    }

    scanner.close();
    String[] instructions = inputLines.toArray(new String[0]);
    System.out.println(ProgrammaLineara.fromLinearProgram(instructions));
  }
}
