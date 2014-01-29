package distSysLab1.timeStamp;

import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

public class LogicalTimeStamp extends TimeStamp<LogicalTimeStamp> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3L;
	private int val=-1;
	public LogicalTimeStamp() {
		}
	public int getVal(){
		return val;
	}

	public LogicalTimeStamp(int in) {
		this.val = in;
	}



	
	@Override
	public synchronized int compareTo(LogicalTimeStamp o) {
		
		return val-o.val;
	}

	@Override
	public String toString() {
		return "current timstamp value is "+val;
	}

}
