import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class MapperThread extends Thread {
    private final Mapper mapper;
    private ArrayList<String> result;

    public MapperThread(Mapper mapper) {
        this.mapper = mapper;
    }

    public void run() {
        try {
            this.result = mapper.process();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }

    public ArrayList<String> getResult() {
        return result;
    }
}
