package distSysLab1.clock;

import distSysLab1.timeStamp.TimeStamp;

public abstract class ClockService {
    protected static final int step = 1;
    
    public enum ClockType {
        LOGICAL, VECTOR, NONE;
    }

    private static ClockService instance;
    protected TimeStamp curTimeStamp;
    protected String localName;

    protected ClockService() {

    };

    public static ClockService getClockSerivce(ClockType type, String localName, int nodeAmount) {
        if(instance != null) {
            return instance;
        }
        
        switch(type) {
        case LOGICAL:
            instance = new LogicalClockService(nodeAmount);
            instance.localName = localName;
            return instance;
        case VECTOR:
            instance = new VectorClockService(nodeAmount);
            instance.localName = localName;
            return instance;
        default:
            return null;
        }
    }
    
    public TimeStamp getCurTimeStamp() {
        return this.curTimeStamp;
    }

    /**
     * Update currentTimeStamp when send a message by using the step length. 
     */
    public abstract void updateTimeStampOnSend();

    /**
     * Update currentTimeStamp when receive a message by comparing to timestamp
     * in the incoming message.
     * @param ts
     */
    public abstract void updateTimeStampOnReceive(TimeStamp ts);
}
