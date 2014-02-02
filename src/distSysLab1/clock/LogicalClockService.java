package distSysLab1.clock;

import java.util.concurrent.atomic.AtomicInteger;

import distSysLab1.timeStamp.LogicalTimeStamp;
import distSysLab1.timeStamp.TimeStamp;

public class LogicalClockService extends ClockService {
    public LogicalClockService(int nodeAmount) {
        this.curTimeStamp = new LogicalTimeStamp();
    }
    
    @Override
    public void updateTimeStampOnSend() {
        AtomicInteger cur = (AtomicInteger)this.getCurTimeStamp().getTimeStamp();
        cur.addAndGet(step);
        this.getCurTimeStamp().setTimeStamp(cur);
    }

    @Override
    public void updateTimeStampOnReceive(TimeStamp ts) {
        LogicalTimeStamp localTS = (LogicalTimeStamp)this.getCurTimeStamp();
        LogicalTimeStamp remoteTS = (LogicalTimeStamp)ts.getTimeStamp();

        int localVal = localTS.getTimeStamp().get();
        int remoteVal = remoteTS.getTimeStamp().get();
        
        AtomicInteger newVal = new AtomicInteger(Math.max(localVal, remoteVal) + 1);
        this.curTimeStamp.setTimeStamp(newVal);
    }
}
