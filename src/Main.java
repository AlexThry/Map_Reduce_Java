import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        ArrayList<String> files = new ArrayList<>();

        files.add("/Users/alexisthierry/Documents/Alex/Cours/FI4/S7/Proj731/Map Reduce/src/Files/Bible.txt");
        files.add("/Users/alexisthierry/Documents/Alex/Cours/FI4/S7/Proj731/Map Reduce/src/Files/HP1.txt");
        files.add("/Users/alexisthierry/Documents/Alex/Cours/FI4/S7/Proj731/Map Reduce/src/Files/HP2.txt");
        files.add("/Users/alexisthierry/Documents/Alex/Cours/FI4/S7/Proj731/Map Reduce/src/Files/HP3.txt");
        files.add("/Users/alexisthierry/Documents/Alex/Cours/FI4/S7/Proj731/Map Reduce/src/Files/HP4.txt");

        Mapper mapper1 = new Mapper();
        Mapper mapper2 = new Mapper();
        Mapper mapper3 = new Mapper();
        Mapper mapper4 = new Mapper();
        Mapper mapper5 = new Mapper();

        ArrayList<Mapper> mappers = new ArrayList<>();
        mappers.add(mapper1);
        mappers.add(mapper2);
        mappers.add(mapper3);
        mappers.add(mapper4);
        mappers.add(mapper5);

        PreProcesser preProcesser = new PreProcesser(files, mappers);

        preProcesser.mergeFiles();
        preProcesser.splitString();
        preProcesser.distributeChunks();

        // Il faut créer les récucers avant les mappers.
        // trouver une fonction de hash -> Sha64
        // nombre de possibilités de hash divisées par le nombre de reducer
        // En créant les mappers, on leur donne la liste des reducers
        // quand les reducers ont leur mots, on multithread
    }
}