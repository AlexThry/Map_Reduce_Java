public class SimpleHash {
    private static volatile SimpleHash instance;

    private SimpleHash() {}

    public static SimpleHash getInstance() {
        if (instance == null) {
            synchronized (SimpleHash.class) {
                if (instance == null) {
                    instance = new SimpleHash();
                }
            }
        }
        return instance;
    }

    public int execute(String data) {
        int hash = 0;

        for (int i = 0; i < data.length(); i++) {
            hash = 31 * hash + data.charAt(i);
            hash ^= (hash >> 4) | (hash << 4);
        }

        return Math.abs((byte) (hash & 0xFF));
    }
}
