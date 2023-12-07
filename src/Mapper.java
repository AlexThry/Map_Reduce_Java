import java.math.BigInteger;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.security.*;

public class Mapper {

    private ArrayList<String> map = new ArrayList<>();
    ArrayList<String> chunk = new ArrayList<>();
    ArrayList<ArrayList<String>> shuffled = new ArrayList<>();
    ArrayList<Reducer> reducers;

    public Mapper() {}
    public Mapper(ArrayList<Reducer> reducers) {
        this.reducers = reducers;
    }

    public ArrayList<String> process() throws NoSuchAlgorithmException {
        for (String line: chunk) {
            ArrayList<String> words = new ArrayList<>(List.of(line.split("[ -;,./!?\"\t\n\r()'{}”|“’—^>=%*»$€@#§°_…]+")));
            for (String word: words) {
                if (word.length() > 1) {
                    word = word.toLowerCase();
                    map.add(word);
                }
            }
        }
        return map;
    }

    public ArrayList<String> getMap() {
        return map;
    }

    public void setChunk(ArrayList<String> chunk) {
        this.chunk = chunk;
    }
}
