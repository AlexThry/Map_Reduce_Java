import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PreProcesser {
    ArrayList<String> files = new ArrayList<>();
    ArrayList<String> mergedText = new ArrayList<>();
    Integer mergedSize = 0;
    ArrayList<Mapper> mappers = new ArrayList<>();
    ArrayList<ArrayList<String>> chunks = new ArrayList<>();

    public PreProcesser(ArrayList<String> files, ArrayList<Mapper> mappers) {
        this.files = files;
        this.mappers = mappers;
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
        System.out.println(chunkSize);
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

    public void distributeChunks() {
        for (Integer i = 0; i < mappers.size(); i++) {
            Mapper mapper = mappers.get(i);
            ArrayList<String> chunk = chunks.get(i);
            mapper.setChunk(chunk);
            mapper.process();
            MapperThread mapperThread = new MapperThread(mapper);

            mapperThread.start();
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
}
