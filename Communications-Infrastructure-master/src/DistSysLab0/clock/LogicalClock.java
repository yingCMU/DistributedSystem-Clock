package DistSysLab0.clock;



import java.util.concurrent.atomic.AtomicInteger;

import DistSysLab0.Message.TimeStampMessage;
import DistSysLab0.Model.*;
import DistSysLab0.TimeStamp.LogicalTimeStamp;

public class LogicalClock extends ClockService {

	private AtomicInteger cur=null;
	//pivate LogicalTimeStamp lts;
	
	//initialising the logical clock for the thread
	public LogicalClock(String localName)
	{	
		cur = new AtomicInteger(-1);
		
		System.out.println("Logical clock started for "+localName+" with init value"+cur.get());
	}
	
	@Override
	//used by local process
	public LogicalTimeStamp getNewTimeStamp(String localName) {
		return new LogicalTimeStamp(cur.incrementAndGet());
	}

	@Override
	public LogicalTimeStamp getCurrentTimeStamp(String localName){
		return new LogicalTimeStamp(cur.get());
	}
	
	public LogicalTimeStamp updateTimeStampOnReceive(String localName, TimeStampMessage Tsm)
	{
		int self=cur.get();
		int msg= Tsm.getTimeStamp().getVal();
		int update=(Math.max(self, msg))+1;
		cur = new AtomicInteger(update);
		System.err.println("Local clock time stamp updated on reception -> "+update);
		return new LogicalTimeStamp(cur.incrementAndGet());
	}

	
}
