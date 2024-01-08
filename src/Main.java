import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;


public class Main {


    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, InterruptedException {
        Processer processer = new Processer("../Files");
        processer.mergeFiles();
        processer.count();
    }
}