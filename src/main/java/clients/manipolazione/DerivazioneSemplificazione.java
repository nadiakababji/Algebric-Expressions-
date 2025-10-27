package clients.manipolazione;

import java.util.Scanner;
import luppolo.Nodo;
import luppolo.costruzione.NotazionePolaccaEspressione;
import luppolo.manipolazione.Derivazione;

public class DerivazioneSemplificazione {

  public static void main(String[] args) {

    try (Scanner scanner = new Scanner(System.in)) {
      char c = args[0].charAt(0);
      while (scanner.hasNextLine()) {
        Nodo espressione = NotazionePolaccaEspressione.fromPolocaa(scanner.nextLine());
        Nodo espressioneSemplificata = espressione.accept(new Derivazione(c));
        System.out.println(
            espressioneSemplificata.accept(new luppolo.manipolazione.Semplificazione()));
      }
    }
  }
}
