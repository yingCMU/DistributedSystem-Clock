package DistSysLab0.clock;



import java.util.HashMap;
import DistSysLab0.Model.*;
import DistSysLab0.Message.*;
public abstract class ClockService {
	private static final int LOGICAL_ID = 0;
	private static final int VECTOR_ID = 1;
	
	private static ClockService clock = null;
	protected TimeStamp myTimeStamp;
	
	//TODO factory pattern ok???
	
	//TODO: required because its to be overridden differently by the 2 different clocks
	public abstract TimeStamp getNewTimeStamp(String localName);
	public abstract TimeStamp getCurrentTimeStamp(String localName);
	public abstract TimeStamp updateTimeStampOnReceive(String localName, TimeStampMessage Tsm);
}
