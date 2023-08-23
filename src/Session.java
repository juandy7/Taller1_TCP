import java.io.IOException;
import java.net.Socket;

public class Session implements Runnable {
    private Socket socket;

    public Session(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            while (true) {
                byte[] bf = new byte[30000];
                socket.getInputStream().read(bf);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
