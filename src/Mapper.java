import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Mapper {
 
    private HashMap<String, Integer> map = new HashMap<>();
    ArrayList<String> chunk = new ArrayList<>();

    public Mapper() {}

    public Mapper(ArrayList<String> chunk) {
        this.chunk = chunk;
    }

    public void process() {
        for (String line: chunk) {
            ArrayList<String> words = new ArrayList<>(List.of(line.split("[ -;,./!?\"\t\n\r()'{}]+")));
            for (String word: words) {
                word = word.toLowerCase();
                if (map.containsKey(word) && word != "" && word.length() != 0) {
                    Integer newValue = map.get(word) + 1;
                    map.put(word, newValue);
                } else if (!map.containsKey(word) && word != "" && word.length() != 0) {
                    map.put(word, 1);
                }
            }
        }
        System.out.println(map);
    }

    public HashMap<String, Integer> getMap() {
        return map;
    }

    public void setChunk(ArrayList<String> chunk) {
        this.chunk = chunk;
    }
}
