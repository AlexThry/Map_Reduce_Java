import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

public class Processer {
    ArrayList<String> files = new ArrayList<>();
    ArrayList<String> mergedText = new ArrayList<>();
    ArrayList<ArrayList<String>> chunks = new ArrayList<>();
    Integer mergedSize = 0;
    ArrayList<Mapper> mappers = new ArrayList<>();
    ArrayList<Reducer> reducers = new ArrayList<>();
    ArrayList<String> fullMap = new ArrayList<>();
    ArrayList<ArrayList<String>> reducerMaps = new ArrayList<>();
    HashMap<String, Integer> fullHashMap = new HashMap<>();

    public Processer(ArrayList<String> files, ArrayList<Mapper> mappers) {
        this.files = files;
        this.mappers = mappers;
    }

    public Processer(String filesPath, Integer nbMappers, Integer nbReducers) {
        this.files = files;
        for (int i = 0; i < nbReducers; i++) {
            this.reducers.add(new Reducer());
        }
        for (int i = 0; i < nbMappers; i++) {
            this.mappers.add(new Mapper(this.reducers));
        }
        this.files = this.getFiles(filesPath);
    }
    public ArrayList<String> getFiles(String path) {
        String folderPath = path;
        ArrayList<String> filesArray = new ArrayList<>();

        File folder = new File(folderPath);

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
        Integer chunkSize = Integer.valueOf(mergedSize / nbMappers);
        ArrayList<ArrayList<String>> chunks = new ArrayList<>();
        for (Integer i = 0; i < nbMappers; i++) {
            int start;
            int end;
            if (i == 0) {
                start = i * chunkSize;
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

    public void executeMappers() throws NoSuchAlgorithmException, InterruptedException {
        ArrayList<MapperThread> threads = new ArrayList<>();
        for (Integer i = 0; i < mappers.size(); i++) {
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
    }

    public void shuffle() {
        for (Integer i = 0; i < this.reducers.size(); i++) {
            this.reducerMaps.add(new ArrayList<>());
        }
        SimpleHash simpleHash = SimpleHash.getInstance();
        Integer subDiv = (int) 128 / this.reducers.size();
        for (int i = 0; i < this.reducers.size(); i++) {
            for (Integer j = 0; j < this.fullMap.size(); j++) {
                String word = this.fullMap.get(j);
                Integer hash = simpleHash.execute(word);
                if ((i == 0 && hash >= i * subDiv && hash < (i + 1) * subDiv) || (i != 0 && hash > i * subDiv && hash < (i + 1) * subDiv)) {
                    this.reducerMaps.get(i).add(word);
                }
            }
        }
    }

    public void executeReducer() throws InterruptedException {
        ArrayList<ReducerThread> threads = new ArrayList<>();
        for (Integer i = 0; i < reducers.size(); i++) {
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

    public ArrayList<String> getMergedText() {
        return mergedText;
    }

    public Integer getMergedSize() {
        return mergedSize;
    }

    public void setMappers(ArrayList<Mapper> mappers) {
        this.mappers = mappers;
    }

    public ArrayList<String> getFullMap() {
        return fullMap;
    }

    public HashMap<String, Integer> getFullHashMap() {
        return fullHashMap;
    }
}
