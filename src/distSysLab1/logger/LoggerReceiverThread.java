package distSysLab1.logger;

import java.io.BufferedInputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

import distSysLab1.message.TimeStampMessage;

public class LoggerReceiverThread implements Runnable {
    private Socket socket;
    private ObjectInputStream in;

    public LoggerReceiverThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        Logger logger = Logger.getInstance();
        try {
            in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            while(true) {
                TimeStampMessage message = (TimeStampMessage) in.readObject();

                logger.getRecvQueue().offer(message);
            }
        }
        catch (Exception e) {
            //logger.error("ERROR: PairListenThread corrupt");
            e.printStackTrace();
        }
    }
}
