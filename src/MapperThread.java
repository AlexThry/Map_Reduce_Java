import java.security.NoSuchAlgorithmException;

public class MapperThread extends Thread {
    private final Mapper mapper;

    public MapperThread(Mapper mapper) {
        this.mapper = mapper;
    }

    public void run() {
        System.out.println("Mapping...");
        try {
            mapper.process();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }
}
