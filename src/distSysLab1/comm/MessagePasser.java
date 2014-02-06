package distSysLab1.comm;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

import distSysLab1.clock.ClockService;
import distSysLab1.clock.ClockService.ClockType;
import distSysLab1.message.MulticastMessage;
import distSysLab1.message.TimeStampMessage;
import distSysLab1.model.NodeBean;
import distSysLab1.model.RuleBean;
import distSysLab1.model.RuleBean.RuleAction;

public class MessagePasser {
    private static MessagePasser instance;
    private ClockService clockServ;
    private LinkedBlockingDeque<TimeStampMessage> sendQueue = new LinkedBlockingDeque<TimeStampMessage>();
    private LinkedBlockingDeque<TimeStampMessage> sendDelayQueue = new LinkedBlockingDeque<TimeStampMessage>();
    private LinkedBlockingDeque<TimeStampMessage> recvQueue = new LinkedBlockingDeque<TimeStampMessage>();
    private LinkedBlockingDeque<TimeStampMessage> recvDelayQueue = new LinkedBlockingDeque<TimeStampMessage>();
    private HashMap<String, NodeBean> nodeList = new HashMap<String, NodeBean>();
    private ArrayList<RuleBean> sendRules = new ArrayList<RuleBean>();
    private ArrayList<RuleBean> recvRules = new ArrayList<RuleBean>();
/* for multicasting */
    private HashMap<String, AtomicInteger> recvSeqTracker;
	ArrayList<String> group;
	private AtomicInteger lastMultiCastSeq = new AtomicInteger(0);
	private LinkedBlockingDeque<MulticastMessage> recvHoldBackDelayQueue = new LinkedBlockingDeque<MulticastMessage>();
	private LinkedBlockingDeque<MulticastMessage> recvHoldBackQueue = new LinkedBlockingDeque<MulticastMessage>();
    
    
    private String configFile;
    private String localName;
    private String loggerName;
    private String MD5Last;
    private int curSeqNum;

    private ListenerThread listener;
    private SenderThread sender;
    private ClockType clockType;

    /**
     * Actual constructor for MessagePasser
     *
     * @param configFile
     * @param localName
     */
    private MessagePasser(String configFile, String localName, String loggerName)
            throws UnknownHostException {
        this.loggerName = loggerName;
        this.localName = localName;
        this.configFile = configFile;
        this.curSeqNum = 0;

        ConfigParser.configurationFile = configFile;
        String type = ConfigParser.readClock();
        nodeList = ConfigParser.readConfig();
        sendRules = ConfigParser.readSendRules();
        recvRules = ConfigParser.readRecvRules();
        MD5Last = ConfigParser.getMD5Checksum(configFile);

        if(type.equalsIgnoreCase("VECTOR")) {
            clockType = ClockType.VECTOR;
        }
        else if(type.equalsIgnoreCase("LOGICAL")) {
            clockType = ClockType.LOGICAL;
        }

        clockServ = ClockService.getClockSerivce(clockType, localName, nodeList);

        if(nodeList.get(localName) == null) {
            System.err.println("The local name is incorrect.");
            System.exit(0);
        }
        else if (!InetAddress.getLocalHost().getHostAddress().toString().equals(nodeList.get(localName).getIp())) {
            System.err.println("Local ip do not match configuration file.");
            System.exit(0);
        }
        else {
            listener = new ListenerThread(nodeList.get(localName).getPort(), configFile,
                                            recvRules, sendRules, recvQueue, recvDelayQueue,clockServ);
            sender = new SenderThread(sendQueue, sendDelayQueue, nodeList);
        }

        System.out.println("Local status is: " + this.toString());
    }
    

    /**
     * For message that need to be logged, send it to the logger.
     * @param msg
     * @param info
     * @param willLog
     */
    private void sendToLogger(TimeStampMessage msg, String info, boolean willLog) {
        if(willLog == true) {
            if(nodeList.get(loggerName) == null) {
                System.err.println("You have not assigned a valid logger.");
                return;
            }

            // Build a wrapper message to make the original message as its data field.
            TimeStampMessage wrapper = new TimeStampMessage(loggerName, info, msg);
            wrapper.setSrc(localName);
            wrapper.setSeqNum(curSeqNum++);
            wrapper.setTimeStamp(msg.getTimeStamp());

            sendQueue.add(wrapper);
        }
    }

    /**
     * Initialization for receive thread.
     */
    public synchronized void startListener() {
        Thread thread = new Thread(this.listener);
        thread.start();
    }

    /**
     * Initialization for send thread.
     */
    public synchronized void startSender() {
        Thread thread = new Thread(this.sender);
        thread.start();
    }

    /**
     * Singleton constructor for MessagePasser
     *
     * @param configuration_filename
     * @param local_name
     */
    public static synchronized MessagePasser getInstance(String configFileName,
                                                         String localName, String loggerName)
                                                         throws UnknownHostException {
        if (instance == null) {
            instance = new MessagePasser(configFileName, localName, loggerName);
        }
        return instance;
    }

    /**
     * Return existed instance of MessagePasser
     *
     * @return instance
     */
    public static MessagePasser getInstance() {
        return instance;
    }

    /**
     * Send a message.
     *
     * @param message The message need to be sent.
     */
    public void send(TimeStampMessage message, boolean willLog) {
        // Set source and seq of the massage
        message.setSrc(localName);
        message.setSeqNum(curSeqNum++);
        this.getClockServ().updateTimeStampOnSend();
        message.setTimeStamp(clockServ.getCurTimeStamp());

        // Check if the configuration file has been changed.
        String MD5 = ConfigParser.getMD5Checksum(configFile);
        if (!MD5.equals(MD5Last)) {
            sendRules = ConfigParser.readSendRules();
            recvRules = ConfigParser.readRecvRules();
            MD5Last = MD5;
        }

        // Try to match a rule from the send rule list.
        RuleAction action = RuleAction.NONE;
        for (RuleBean rule : sendRules) {
            if (rule.isMatch(message)) {
                action = rule.getAction();
            }
        }

        // Do action according to the matched rule's type.
        switch (action) {
        case DROP:
            // Just drop this message.
            sendToLogger(message, "Sender dropped.", willLog);
            break;

        case DUPLICATE:
            // Add this message into sendQueue.
            sendQueue.add(message);
            sendToLogger(message, "Sender accepted.", willLog);
            // Add a duplicate message into sendQueue.
            TimeStampMessage copy = (TimeStampMessage) message.copyOf();
            copy.setDuplicate(true);
            sendQueue.add(copy);
            sendToLogger(copy, "Sender duplicated.", willLog);
            sendQueue.addAll(sendDelayQueue);
            sendDelayQueue.clear();
            break;

        case DELAY:
            // Add this message into delayQueue
            sendDelayQueue.add(message);
            sendToLogger(message, "Sender delayed.", willLog);
            break;

        case NONE:
        default:
            // Add this message into sendQueue
            sendQueue.add(message);
            sendToLogger(message, "Sender accepted.", willLog);
            sendQueue.addAll(sendDelayQueue);
            sendDelayQueue.clear();
        }
    }

    /**
     * Deliver message from the receive queue
     * @param willLog
     *
     * @return A message
     */
    public TimeStampMessage receive(Boolean willLog) {
        TimeStampMessage message = null;
        synchronized (recvQueue) {
            if (!recvQueue.isEmpty()) {
                message = recvQueue.poll();
            }
        }

        if(message != null) {
           // clockServ.updateTimeStampOnReceive(message.getTimeStamp());
            sendToLogger(message, "Receive accepted.", willLog);
        }

        return message;
    }

    /**
     * Do the termination work.
     */
    public void teminate() throws IOException {
        listener.teminate();

        sender.teminate();
    }

    public HashMap<String, NodeBean> getNodeList() {
        return nodeList;
    }

    public ClockService getClockServ() {
        return clockServ;
    }

    @Override
    public String toString() {
        return "MessagePasser [configFile=" + configFile + ", localName=" + localName
                + ", listenSocket=" + listener.toString() + "]";
    }

	public AtomicInteger getLastMultiCastSeq() {
		return lastMultiCastSeq;
	}

	public void setLastMultiCastSeq(AtomicInteger lastMultiCastSeq) {
		this.lastMultiCastSeq = lastMultiCastSeq;
	}

	public HashMap<String, AtomicInteger> getRecvSeqTracker() {
		return recvSeqTracker;
	}

	public void setRecvSeqTracker(HashMap<String, AtomicInteger> recvSeqTracker) {
		this.recvSeqTracker = recvSeqTracker;
	}
}