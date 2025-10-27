package clients.manipolazione;

import java.util.Scanner;
import luppolo.Nodo;
import luppolo.costruzione.NotazionePolaccaEspressione;
import luppolo.manipolazione.Semplificazione;


public class EspansioneSemplificazione {

  public static void main(String[] args) {

    try (Scanner scanner = new Scanner(System.in)) {
      while (scanner.hasNextLine()) {
        Nodo espressione = NotazionePolaccaEspressione.fromPolocaa(scanner.nextLine());
        Nodo espressioneEsp = espressione.accept(new luppolo.manipolazione.Espansione());
        System.out.println(espressioneEsp.accept(new Semplificazione()));
      }
    }
  }
}
