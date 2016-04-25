package px500.pipoask.com;

public class ChatConfig {


    public static String FIREBASE_URL;
    // Singleton
    private static ChatConfig instance = null;
    /**
     * REMEMBER CHANGE IT BEFORE DEPLOY
     */
    private static Mode mode = Mode.DEV;


    public ChatConfig(Mode mode) {
        if (mode == Mode.DEV) {
            FIREBASE_URL = "https://pipo-500px.firebaseio.com";

        } else if (mode == Mode.PROD) {
            FIREBASE_URL = "https://pipo-500px.firebaseio.com";
        }
    }

    /**
     * Get instance about this AppConfig (Singleton)
     *
     * @return Current instance
     */
    public static ChatConfig getInstance() {
        if (instance == null) {
            instance = new ChatConfig(mode);
        }
        return instance;
    }

    // Define run mode
    public enum Mode {
        DEV(1), PROD(2);
        private int value;

        Mode(int value) {
            this.value = value;
        }
    }

}