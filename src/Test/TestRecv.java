package Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;

import distSysLab1.comm.MessagePasser;
import distSysLab1.message.MulticastMessage;
import distSysLab1.message.MulticastType;
import distSysLab1.message.TimeStampMessage;

public class TestRecv {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String src = "bob";
		MessagePasser messagePasser = MessagePasser.
				getInstance("./config.yaml", src,"log");
        messagePasser.startSender();
        messagePasser.startListener();
        int seq=1;
        String [] dest = {"bob","david","alice"};
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        in.readLine();
        messagePasser.receive(true);
        //MulticastMessage msg = new MulticastMessage(seq++,src,dest[0], "kind",MulticastType.SEND," data");
        //messagePasser.multisend(msg, "groupID");
	}

}
