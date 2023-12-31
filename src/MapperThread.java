import java.security.NoSuchAlgorithmException;

public class MapperThread extends Thread {
    private final Mapper mapper;

    public MapperThread(Mapper mapper) {
        this.mapper = mapper;
    }

    public void run() {
        try {
            mapper.process();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }
}
