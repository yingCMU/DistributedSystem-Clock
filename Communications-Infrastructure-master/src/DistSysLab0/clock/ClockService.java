package DistSysLab0.clock;



import java.util.HashMap;

import DistSysLab0.Model.*;
import DistSysLab0.Message.*;
import DistSysLab0.TimeStamp.TimeStamp;
public abstract class ClockService {
	
	
	
	
	//factory pattern ok???
	
	//TODO: required because its to be overridden differently by the 2 different clocks
	public abstract TimeStamp getNewTimeStamp(String localName);
	public abstract TimeStamp getCurrentTimeStamp(String localName);
	public abstract TimeStamp updateTimeStampOnReceive(String localName, TimeStampMessage Tsm);
}
