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
                    Processor processor = new Processor("../Files", nbMapper, nbReducer);

                    processor.mergeFiles();
                    processor.splitString();
                    processor.executeMappers();
                    processor.executeReducer();
                    HashMap<String, Integer> results = processor.getFullHashMap();

                    long fin = System.currentTimeMillis();
                    long tempsEcoule = (fin - debut);
                    System.out.println("Test: " + test + ", mappers: " + nbMapper + ", reducers: " + nbReducer + ", temps écoulé: " + tempsEcoule);
                    bufferedWriter.write(nbMapper + "," + nbReducer + "," + tempsEcoule + "," + (test + 1) + "\n");
                }
            }
        }

        bufferedWriter.close();
        fileWriter.close();
    }

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, InterruptedException {
//        test(10, 4, 4);

        long debut = System.currentTimeMillis();
        Processor processor = new Processor("../Files", 10, 3);

        processor.mergeFiles();
        processor.splitString();
        processor.executeMappers();
        processor.executeReducer();
        HashMap<String, Integer> results = processor.getFullHashMap();

        long fin = System.currentTimeMillis();
        System.out.println(results);
        System.out.println("Temps de traitement: " + (fin-debut));
    }
}