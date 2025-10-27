package clients.manipolazione;

import java.util.Scanner;
import luppolo.Nodo;
import luppolo.costruzione.NotazionePolaccaEspressione;

public class Espansione {

  public static void main(String[] args) {

    try (Scanner scanner = new Scanner(System.in)) {
      while (scanner.hasNextLine()) {
        Nodo espressione = NotazionePolaccaEspressione.fromPolocaa(scanner.nextLine());
        System.out.println(espressione.accept(new luppolo.manipolazione.Espansione()));
      }
    }
  }
}
