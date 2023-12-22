import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Processer {
    ArrayList<String> files;
    ArrayList<String> mergedText = new ArrayList<>();
    ArrayList<ArrayList<String>> chunks = new ArrayList<>();
    int mergedSize = 0;
    ArrayList<Mapper> mappers = new ArrayList<>();
    ArrayList<Reducer> reducers = new ArrayList<>();
    ArrayList<String> fullMap = new ArrayList<>();
    ArrayList<ArrayList<String>> reducerMaps = new ArrayList<>();
    HashMap<String, Integer> fullHashMap = new HashMap<>();

    public Processer(String filesPath, int nbMappers, int nbReducers) {
        for (int i = 0; i < nbReducers; i++) {
            this.reducers.add(new Reducer());
        }
        for (int i = 0; i < nbMappers; i++) {
            this.mappers.add(new Mapper(this.reducers));
        }
        this.files = this.getFiles(filesPath);
    }
    public ArrayList<String> getFiles(String path) {
        ArrayList<String> filesArray = new ArrayList<>();

        File folder = new File(path);

        if (folder.isDirectory()) {
            File[] files = folder.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        String fileName = file.getName();
                        String[] splitted = fileName.split("[.]+");
                        if (splitted[splitted.length - 1].equals("txt")) {
                            filesArray.add(path + "/" + fileName);
                        }
                    }
                }
            }
        }
        return filesArray;
    }

    public void mergeFiles() throws IOException {
        for (String file: files) {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                this.mergedText.add(line);
            }
        }
        this.mergedSize = mergedText.size();
    }

    public void splitString() {
        int nbMappers = mappers.size();
        int chunkSize = mergedSize / nbMappers;
        ArrayList<ArrayList<String>> chunks = new ArrayList<>();
        for (int i = 0; i < nbMappers; i++) {
            int start;
            int end;
            if (i == 0) {
                start = 0;
            } else {
                start = i * chunkSize + 1;
            }
            if (i == nbMappers - 1) {
                end = mergedSize - 1;
            } else {
                end = (i + 1) * chunkSize;
            }
            chunks.add(new ArrayList<>(this.mergedText.subList(start, end)));
        }
        this.chunks = chunks;
    }

    public void executeMappers() throws InterruptedException {
        ArrayList<MapperThread> threads = new ArrayList<>();
        for (int i = 0; i < mappers.size(); i++) {
            Mapper mapper = mappers.get(i);
            ArrayList<String> chunk = chunks.get(i);
            mapper.setChunk(chunk);
            threads.add(new MapperThread(mapper));

        }
        for (MapperThread thread: threads) {
            thread.start();
        }
        for (MapperThread thread: threads) {
            thread.join();
            this.fullMap.addAll(thread.getResult());
        }
        System.out.println("Mapping finished");
    }

    public void shuffle() {
        for (int i = 0; i < this.reducers.size(); i++) {
            this.reducerMaps.add(new ArrayList<>());
        }
        SimpleHash simpleHash = SimpleHash.getInstance();
        int subDiv = 128 / this.reducers.size();
        for (int i = 0; i < this.reducers.size(); i++) {
            for (String word : this.fullMap) {
                int hash = simpleHash.execute(word);
                if ((i == 0 && hash >= 0 && hash < (i + 1) * subDiv) || (i != 0 && hash > i * subDiv && hash < (i + 1) * subDiv)) {
                    this.reducerMaps.get(i).add(word);
                }
            }
        }
    }

    public void executeReducer() throws InterruptedException {
        ArrayList<ReducerThread> threads = new ArrayList<>();
        for (int i = 0; i < reducers.size(); i++) {
            Reducer reducer = reducers.get(i);
            ArrayList<String> reducerMap = reducerMaps.get(i);
            reducer.setMap(reducerMap);
            threads.add(new ReducerThread(reducer));

        }
        for (ReducerThread thread: threads) {
            thread.start();
        }
        for (ReducerThread thread: threads) {
            thread.join();
            this.fullHashMap.putAll(thread.getResult());
        }
    }

    public HashMap<String, Integer> getFullHashMap() {
        return fullHashMap;
    }
}
