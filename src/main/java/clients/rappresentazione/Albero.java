package clients.rappresentazione;

import java.util.Scanner;
import luppolo.Nodo;
import luppolo.rappresentazione.Tree;

public class Albero {

  public static void main(String[] args) {
    try (Scanner scanner = new Scanner(System.in)) {
      while (scanner.hasNextLine()) {
        Nodo espressione =
            luppolo.costruzione.NotazionePolaccaEspressione.fromPolocaa(scanner.nextLine());

        System.out.println(espressione.accept(new Tree("")));
      }
    }
  }
}
