import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;


public class Main {

    public static void test(int nbMapperMax, int nbReducerMax, int nTest) throws IOException, InterruptedException {
        String csvFileName = "direct-communication.csv";
        FileWriter fileWriter = new FileWriter(csvFileName);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        bufferedWriter.write("NbMapper,NbReducer,TempsEcoule(ms),nTest\n");

        for (int test = 0; test < nTest; test++) {
            for (int nbMapper = 2; nbMapper <= nbMapperMax; nbMapper++) {
                for (int nbReducer = 1; nbReducer <= nbReducerMax; nbReducer++) {
                    long debut = System.currentTimeMillis();
                    Processer processer = new Processer("../Files", nbMapper, nbReducer);

                    processer.mergeFiles();
                    processer.splitString();
                    processer.executeMappers();
                    processer.executeReducer();
                    HashMap<String, Integer> results = processer.getFullHashMap();

                    long fin = System.currentTimeMillis();
                    long tempsEcoule = (fin - debut);
                    System.out.println("Test: " + (test + 1) + ", mappers: " + nbMapper + ", reducers: " + nbReducer + ", temps écoulé: " + tempsEcoule);
                    bufferedWriter.write(nbMapper + "," + nbReducer + "," + tempsEcoule + "," + (test + 1) + "\n");
                }
            }
        }

        bufferedWriter.close();
        fileWriter.close();
    }

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, InterruptedException {
        test(10, 4, 4);
    }
}