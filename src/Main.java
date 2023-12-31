import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;


public class Main {

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, InterruptedException {


        long debut = System.currentTimeMillis();
        Processer processer = new Processer("src/Files", 10, 2);

        System.out.println(processer.mergedSize);
        processer.mergeFiles();
        processer.splitString();
        processer.executeMappers();
        processer.executeReducer();
        HashMap<String, Integer> results = processer.getFullHashMap();

        long fin = System.currentTimeMillis();
        long tempsEcoule = (fin - debut);
        System.out.println("Temps de calcul : " + tempsEcoule + " ms");
    }
}