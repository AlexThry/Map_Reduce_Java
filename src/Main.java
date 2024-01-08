import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;


public class Main {


    public static void test(int nbMaxMappers, int nbMaxReducer, int nTest) throws IOException, InterruptedException {
        String csvFileName = "main.csv";
        FileWriter fileWriter = new FileWriter(csvFileName);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        bufferedWriter.write("NbMapper,NbReducer,TempsEcoule(ms),nTest\n");

        for (int test = 0; test < nTest; test++) {
            for (int nbMapper = 2; nbMapper <= nbMaxMappers; nbMapper++) {
                for (int nbReducer = 1; nbReducer <= nbMaxReducer; nbReducer++) {
                    long debut = System.currentTimeMillis();

                    Processer processer = new Processer("../Files", nbMapper, nbReducer);

                    processer.mergeFiles();
                    processer.splitString();
                    processer.executeMappers();
                    processer.shuffle();
                    processer.executeReducer();
                    HashMap<String, Integer> results = processer.getFullHashMap();

                    long fin = System.currentTimeMillis();

                    long tempsEcoule = fin - debut;

                    System.out.println("test: " + test + ", mappers: " + nbMapper + ", reducers: " + nbReducer + ", temps: " + tempsEcoule);
                    bufferedWriter.write(nbMapper + "," + nbReducer + "," + tempsEcoule + "," + test + "\n");
                }
            }
        }

        bufferedWriter.close();
        fileWriter.close();
    }

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, InterruptedException {
        test(10, 4, 1);
    }
}