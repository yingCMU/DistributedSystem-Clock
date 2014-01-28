package DistSysLab0.clock;



import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;





import DistSysLab0.Message.*;
import DistSysLab0.Model.*;
import DistSysLab0.TimeStamp.TimeStamp;
import DistSysLab0.TimeStamp.VectorTimeStamp;

public class VectorClock extends ClockService {

	public VectorClock(int numOfNode, HashMap<String, NodeBean> nodelist) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public TimeStamp getNewTimeStamp(String localName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TimeStamp getCurrentTimeStamp(String localName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TimeStamp updateTimeStampOnReceive(String localName,
			TimeStampMessage Tsm) {
		// TODO Auto-generated method stub
		return null;
	}

}
