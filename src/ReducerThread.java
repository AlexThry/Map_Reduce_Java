import java.util.HashMap;

public class ReducerThread extends Thread {
    private Reducer reducer;
    private HashMap<String, Integer> result;

    public ReducerThread(Reducer reducer) {
        this.reducer = reducer;
    }

    public void run() {
        System.out.println("Reducing...");
        this.result = reducer.process();
    }

    public HashMap<String, Integer> getResult() {
        return result;
    }
}
