package distSysLab1.message;

import distSysLab1.timeStamp.TimeStamp;

public class TimeStampMessage extends Message {
	private static final long serialVersionUID = 1L;
	
	private TimeStamp timeStamp;

	public TimeStampMessage(String dest, String kind, Object data) {
		super(dest, kind, data);
	}
	
	public void setTimeStamp(TimeStamp timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	public TimeStamp getTimeStamp() {
        return this.timeStamp;
    }
	
	@Override
    public TimeStampMessage copyOf() {
	    TimeStampMessage to = new TimeStampMessage(this.dest, this.kind, this.data);
        to.duplicate = this.duplicate;
        to.seqNum = this.seqNum;
        to.src = this.src;
        to.timeStamp = this.timeStamp;
        
        return to;
    }

    @Override
    public String toString() {
        return "From:" + this.getSrc() + " to:" + this.getDest() +
               " Seq:" + this.getSeqNum() + " Kind:" + this.getKind()
               + " Dup:" + this.getDuplicate() + "TimeStamp: " + this.getTimeStamp().toString()
               + " Data:" + this.getData();
    }

}
