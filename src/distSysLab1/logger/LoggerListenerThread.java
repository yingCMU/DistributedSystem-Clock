package distSysLab1.logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class LoggerListenerThread implements Runnable {
    private int port;
    private ServerSocket listenSocket;
    private Thread thread;

    public LoggerListenerThread(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try {
            listenSocket = new ServerSocket(this.port);
            while(true) {
                // Listening for new incoming connection.
                Socket socket = listenSocket.accept();
                //logger.info("Handling client at " + socket.getRemoteSocketAddress());

                // Create a new thread for new incoming connection.
                thread = new Thread(new LoggerReceiverThread(socket));
                thread.start();
            }
        }
        catch (IOException e) {
            //logger.error("ERROR: Server socket error");
            System.err.println("ERROR: server Socket error");
        }
    }

    public void teminate() throws IOException {
        thread.interrupt();
        listenSocket.close();
    }

    @Override
    public String toString() {
        return "Listener [port=" + port + "]";
    }
}
