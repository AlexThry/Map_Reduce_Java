import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;


public class Main {
//    public static void test(int nbMapperMax, int nbReducerMax) throws IOException, InterruptedException {
//        for (int nbMapper = 1; nbMapper <= nbMapperMax; nbMapper++) {
//            for (int nbReducer = 1; nbReducer <= nbReducerMax; nbReducer++) {
//                long debut = System.currentTimeMillis();
//                Processer processer = new Processer("src/Files", nbMapper, nbReducer);
//
//                System.out.println(processer.mergedSize);
//                processer.mergeFiles();
//                processer.splitString();
//                processer.executeMappers();
//                processer.executeReducer();
//                HashMap<String, Integer> results = processer.getFullHashMap();
//
//                long fin = System.currentTimeMillis();
//                long tempsEcoule = (fin - debut);
//                System.out.println("Mappers: " + nbMapper + "\nReducers: " + nbReducer + "\nTemps de calcul : " + tempsEcoule + " ms");
//            }
//        }
//    }


    public static void test(int nbMapperMax, int nbReducerMax) throws IOException, InterruptedException {
        String csvFileName = "direct-communication.csv";
        FileWriter fileWriter = new FileWriter(csvFileName);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        bufferedWriter.write("NbMapper,NbReducer,TempsEcoule(ms)\n");

        for (int nbMapper = 1; nbMapper <= nbMapperMax; nbMapper++) {
            for (int nbReducer = 1; nbReducer <= nbReducerMax; nbReducer++) {
                long debut = System.currentTimeMillis();
                Processer processer = new Processer("src/Files", nbMapper, nbReducer);

                processer.mergeFiles();
                processer.splitString();
                processer.executeMappers();
                processer.executeReducer();
                HashMap<String, Integer> results = processer.getFullHashMap();

                long fin = System.currentTimeMillis();
                long tempsEcoule = (fin - debut);
                System.out.println("mappers: " + nbMapper + "\nreducers: " + nbReducer + "\ntemps ecoulé: " + tempsEcoule);
                bufferedWriter.write(nbMapper + "," + nbReducer + "," + tempsEcoule + "\n");
            }
        }

        bufferedWriter.close();
        fileWriter.close();
    }
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, InterruptedException {
        test(10, 4);
    }
}