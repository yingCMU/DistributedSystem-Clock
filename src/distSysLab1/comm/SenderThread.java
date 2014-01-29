package distSysLab1.comm;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.LinkedBlockingDeque;

import org.apache.log4j.Logger;

import distSysLab1.message.TimeStampMessage;
import distSysLab1.model.NodeBean;

public class SenderThread implements Runnable {
    private static Logger logger = Logger.getLogger(SenderThread.class);

    private LinkedBlockingDeque<TimeStampMessage> sendQueue;
    private HashMap<String, NodeBean> nodeList;
    private Socket socket;

    public SenderThread(LinkedBlockingDeque<TimeStampMessage> sendQueue, LinkedBlockingDeque<TimeStampMessage> delayQueue, HashMap<String, NodeBean> nodeList) {
        this.sendQueue = sendQueue;
        this.nodeList = nodeList;
    }

    @Override
    public void run() {
        while(true) {
            // if there is one non-delay message, put all delay message into sendQueue
            while(!sendQueue.isEmpty()) {
                // Send all message in sendQueue
                TimeStampMessage message = sendQueue.pollFirst();
                String serverName = message.getDest();
                String servIp = nodeList.get(serverName).getIp();
                int servPort = nodeList.get(serverName).getPort();
                try {
                    socket = new Socket(servIp, servPort);
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                    objectOutputStream.writeObject(message);

                    objectOutputStream.flush();
                }
                catch (ConnectException e) {
                    logger.error("ERROR: TimeStampMessage send failure, node offline " + message.toString());
                    System.out.println("ERROR: TimeStampMessage send failure, node offline " + message.toString());
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void teminate() throws IOException {
        socket.close();
    }
}
