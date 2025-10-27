package clients.manipolazione;

import java.util.Scanner;
import luppolo.*;

public class Semplificazione {

  public static void main(String[] args) {
    try (Scanner scanner = new Scanner(System.in)) {
      while (scanner.hasNextLine()) {
        Nodo espressione =
            luppolo.costruzione.NotazionePolaccaEspressione.fromPolocaa(scanner.nextLine());
        System.out.println(espressione.accept(new luppolo.manipolazione.Semplificazione()));
      }
    }
  }
}
