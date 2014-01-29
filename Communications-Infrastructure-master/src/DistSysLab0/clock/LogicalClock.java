package DistSysLab0.clock;



import java.util.concurrent.atomic.AtomicInteger;

import DistSysLab0.Message.TimeStampMessage;
import DistSysLab0.Model.*;

public class LogicalClock extends ClockService {

	//private AtomicInteger currentTimeStamp=null;
	private LogicalTimeStamp ts;
	
	//TODO constructor
	public LogicalClock(String localName)
	{
		ts = new LogicalTimeStamp();
		//initialising the logical clock for the thread
		ts.getVector().put(localName, new AtomicInteger(-1));//Init timestamp of this thread with -1
		ts.getVector().get(localName).incrementAndGet();//Start timestamp of this thread with 0
		System.out.println("Logical clock started for "+localName+" with init value"+ts.getVector().get(localName).get());
	}
	
	@Override
	public LogicalTimeStamp getNewTimeStamp(String localName) {
		// TODO Auto-generated method stub
		ts.getVector().get(localName).incrementAndGet();
		
		return ts;
	}

	@Override
	public LogicalTimeStamp getCurrentTimeStamp(String localName){
		return ts;
	}
	
	public LogicalTimeStamp updateTimeStampOnReceive(String localName, TimeStampMessage Tsm)
	{
		int selfTimeStamp=this.ts.getVector().get(localName).get();
		int senderTimeStamp=((LogicalTimeStamp) Tsm.getTimeStamp()).getVector().get(Tsm.getSrc()).get();
		int updateSelfTimeStamp=((selfTimeStamp>senderTimeStamp)?selfTimeStamp:senderTimeStamp)+1;
		this.ts.getVector().get(localName).set(updateSelfTimeStamp);
		System.err.println("Local time stamp on reception = "+this.ts.getVector().get(localName).get());
		return this.ts;
	}

	
}
