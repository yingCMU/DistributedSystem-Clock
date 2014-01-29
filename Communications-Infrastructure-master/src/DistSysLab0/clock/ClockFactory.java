package DistSysLab0.clock;

import java.util.HashMap;

import DistSysLab0.Message.TimeStampMessage;
import DistSysLab0.Model.*;

public abstract class ClockFactory {
	
	
	private static ClockService clock = null;
	protected TimeStamp myTimeStamp;
	
	//TODO factory pattern ok???
	public static ClockService getClock(ClockType clockType, String localName,int numOfNode,HashMap<String,NodeBean> nodelist) {
		switch (clockType) {
		case LOGICAL:
			if (clock == null) 
				 clock = new LogicalClock(localName);
			break;
		case VECTOR:
			if (clock == null)
				clock = new VectorClock(numOfNode,nodelist);
			break;
		default:
			clock = null;
		}
		return clock;
	}
	
}
