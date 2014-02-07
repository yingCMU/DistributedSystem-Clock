package Test;

import java.net.UnknownHostException;

import distSysLab1.comm.MessagePasser;
import distSysLab1.message.MulticastMessage;
import distSysLab1.message.MulticastType;
import distSysLab1.message.TimeStampMessage;

public class TestSend {

	/**
	 * @param args
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws UnknownHostException {
		// TODO Auto-generated method stub
		String src = "alice";
		MessagePasser messagePasser = MessagePasser.
				getInstance("./config.yaml", src,"log");
        messagePasser.startSender();
        messagePasser.startListener();
        int seq=1;
        String [] dest = {"bob","david",""};
        MulticastMessage msg = new MulticastMessage(seq++,src,dest[0], "kind",MulticastType.SEND," data");
        messagePasser.multisend(msg, "groupID");
	}

}
