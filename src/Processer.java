import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Processer {
    ArrayList<String> files;
    ArrayList<String> chunk = new ArrayList<>();

    public Processer(String filesPath) {
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
                this.chunk.add(line);
            }
        }
    }

    public HashMap<String, Integer> count() {
        long debut = System.currentTimeMillis();
        HashMap<String, Integer> map = new HashMap<>();
        for (String line : chunk) {
            ArrayList<String> words = new ArrayList<>(List.of(line.split("[ -;,./!?\"\t\n\r()'{}”|“’—^>=%*»$€@#§°_…]+")));
            for (String word : words) {
                if (map.containsKey(word)) {
                    int count = map.get(word);
                    map.put(word, count + 1);
                } else {
                    map.put(word, 1);
                }
            }
        }

        long fin = System.currentTimeMillis();

        System.out.println("temps écoulé: " + (fin - debut));
        return map;
    }

}
