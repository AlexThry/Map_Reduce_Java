import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;


public class Main {

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, InterruptedException {
        SimpleHash simpleHash = SimpleHash.getInstance();

        Processer processer = new Processer("/Users/alexisthierry/Documents/Alex/Cours/FI4/S7/Proj731/Map Reduce/src/Files", 4, 2);

        processer.mergeFiles();
        processer.splitString();
        processer.executeMappers();
        processer.shuffle();
        processer.executeReducer();
        HashMap<String, Integer> results = processer.getFullHashMap();
        System.out.println(results);

    }
}