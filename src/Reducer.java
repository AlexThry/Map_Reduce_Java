import java.util.ArrayList;
import java.util.HashMap;

public class Reducer {
    final ArrayList<String> map = new ArrayList<>();
    HashMap<String, Integer> wordCount = new HashMap<>();

    public HashMap<String, Integer> process() {
        for (String word: this.map) {
            if (wordCount.containsKey(word)) {
                int count = wordCount.get(word);
                wordCount.put(word, count + 1);
            } else {
                wordCount.put(word, 1);
            }
        }
        return wordCount;
    }

    public void addMap(ArrayList<String> map) {
        synchronized (this.map) {
            this.map.addAll(map);
        }
    }
}
