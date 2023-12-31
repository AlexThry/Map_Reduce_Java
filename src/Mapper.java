import java.util.ArrayList;
import java.util.List;
import java.security.*;

public class Mapper {

    private final ArrayList<String> map = new ArrayList<>();
    private ArrayList<String> chunk = new ArrayList<>();
    private final ArrayList<Reducer> reducers;
    private final ArrayList<ArrayList<String>> reducerMaps = new ArrayList<>();


    public Mapper(ArrayList<Reducer> reducers) {
        this.reducers = reducers;
    }

    public void process() throws NoSuchAlgorithmException {
        for (String line: chunk) {
            ArrayList<String> words = new ArrayList<>(List.of(line.split("[ -;,./!?\"\t\n\r()'{}”|“’—^>=%*»$€@#§°_…]+")));
            for (String word: words) {
                if (word.length() > 1) {
                    word = word.toLowerCase();
                    map.add(word);
                }
            }
        }
        this.shuffle();
        this.distribute();
    }

    public void shuffle() {
        for (int i = 0; i < this.reducers.size(); i++) {
            this.reducerMaps.add(new ArrayList<>());
        }
        SimpleHash simpleHash = SimpleHash.getInstance();
        int subDiv = 128 / this.reducers.size();
        for (int i = 0; i < this.reducers.size(); i++) {
            for (String word : this.map) {
                int hash = simpleHash.execute(word);
                if ((i == 0 && hash >= 0 && hash < (i + 1) * subDiv) || (i != 0 && hash > i * subDiv && hash < (i + 1) * subDiv)) {
                    this.reducerMaps.get(i).add(word);
                }
            }
        }
    }

    public void distribute() {
        for (int i = 0; i < reducers.size(); i++) {
            Reducer reducer = this.reducers.get(i);
            ArrayList<String> reducerMap = this.reducerMaps.get(i);
            reducer.addMap(reducerMap);
        }
    }

    public ArrayList<String> getMap() {
        return map;
    }

    public void setChunk(ArrayList<String> chunk) {
        this.chunk = chunk;
    }
}
