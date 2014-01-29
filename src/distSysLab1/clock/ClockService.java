package distSysLab1.clock;

import distSysLab1.message.TimeStampMessage;
import distSysLab1.timeStamp.TimeStamp;

public abstract class ClockService {
	//factory pattern ok???
	
	//TODO: required because its to be overridden differently by the 2 different clocks
	public abstract TimeStamp getNewTimeStamp(String localName);
	public abstract TimeStamp getCurrentTimeStamp(String localName);
	public abstract TimeStamp updateTimeStampOnReceive(String localName, TimeStampMessage Tsm);
}
