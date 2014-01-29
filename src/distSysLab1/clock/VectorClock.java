package distSysLab1.clock;

import java.util.HashMap;

import distSysLab1.message.TimeStampMessage;
import distSysLab1.model.NodeBean;
import distSysLab1.timeStamp.TimeStamp;

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
    public TimeStamp updateTimeStampOnReceive(String localName, TimeStampMessage Tsm) {
        // TODO Auto-generated method stub
        return null;
    }

}
