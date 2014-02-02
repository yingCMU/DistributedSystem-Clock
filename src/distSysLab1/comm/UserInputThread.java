package distSysLab1.comm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

import distSysLab1.message.TimeStampMessage;

public class UserInputThread implements Runnable {
    private static Logger logger = Logger.getLogger(UserInputThread.class);

    @Override
    public void run() {
        MessagePasser msgPasser = MessagePasser.getInstance();

        try {
            System.out.println("Enter command: (send/receive/status/exit)");
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            String command = "";
            while(!command.equals("quit")) {
                command = in.readLine();
                if(command.equals("send")) {
                    System.out.println("TimeStampMessage dest:");
                    String dest = in.readLine();
                    while(!msgPasser.getNodeList().containsKey(dest)) {
                        System.out.println("Dest not existed, try again:");
                        dest = in.readLine();
                    }

                    System.out.println("TimeStampMessage kind (any string):");
                    String kind = in.readLine();
                    System.out.println("TimeStampMessage data:");
                    String data = in.readLine();
                    
                    msgPasser.getClockServ().updateTimeStampOnSend();
                    TimeStampMessage msg = new TimeStampMessage(dest, kind, data);
                    msg.setTimeStamp(msgPasser.getClockServ().getCurTimeStamp());
                    msgPasser.send(msg);
                    
                    System.out.println("Send Success:");
                    System.out.println(msg.toString());
                }
                else if(command.equals("receive")) {
                    TimeStampMessage msg = msgPasser.receive();
                    if(msg == null) {
                        System.out.println("Nothing received.");
                    }
                    else {
                        System.out.println("Received:");
                        System.out.println(msg.toString());
                    }
                }
                else if(command.equals("status")) {
                    System.out.println(msgPasser.toString());
                }
                else if(command.equals("exit")) {
                    //msgPasser.teminate();
                    System.out.println("Exit.");
                    System.exit(0);
                }
                else {
                    System.out.println("Invalid command");
                }

                System.out.println("Enter command: (send/receive/status/exit)");
            }

            msgPasser.teminate();
            System.out.println("Exit.");
            System.exit(0);
        }
        catch (IOException e) {
            logger.error("ERROR: Reader error");
            System.err.println("ERROR: Reader error");
            e.printStackTrace();
        }
    }

    public static String[] init() throws IOException {
        String[] result = new String[2];

        System.out.println("Enter the name of configuration file:");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        result[0] = in.readLine();

        System.out.println("Enter the name of your machine:");
        result[1] = in.readLine();

        return result;
    }
}
