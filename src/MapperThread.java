import java.util.ArrayList;
import java.util.HashMap;

public class MapperThread extends Thread {
    Mapper mapper;

    public MapperThread(Mapper mapper) {
        this.mapper = mapper;
    }

    public void run() {
        mapper.process();
        System.out.println("je suis un thread");
    }
}
