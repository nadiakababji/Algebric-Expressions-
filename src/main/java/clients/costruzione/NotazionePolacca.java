package clients.costruzione;

import java.util.Scanner;
import luppolo.costruzione.NotazionePolaccaEspressione;

public class NotazionePolacca { 

  public static void main(String[] args) {
    try (Scanner scanner = new Scanner(System.in)) {
      while (scanner.hasNextLine()) {
        System.out.println(NotazionePolaccaEspressione.fromPolocaa(scanner.nextLine()));
      }
    }
  }
}
