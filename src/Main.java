import org.w3c.dom.ls.LSOutput;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;


public class Main {


    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, InterruptedException {
        ArrayList<Long> times = new ArrayList<>();
        try {
            FileWriter fileWriter = new FileWriter("no-map-reduce.csv");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write("TempsEcoule(ms),nTest\n");
            for (int test = 0; test < 5; test++) {
                long debut = System.currentTimeMillis();
                Processer processer = new Processer("../Files");
                processer.mergeFiles();
                processer.count();
                long fin = System.currentTimeMillis();
                bufferedWriter.write((int) (fin-debut) + "," + test + "\n");
                System.out.println("Temps écoulé: " + (fin-debut));
            }
            bufferedWriter.close();
        } catch (IOException e) {
            System.err.println("Erreur lors de l'écriture dans le fichier : " + e.getMessage());
        }
    }
}