package distSysLab1.message;

import java.util.HashMap;



public class MulticastMessage extends TimeStampMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int multicastSeq;
	private String groupID;
	private MulticastType type;
	private HashMap<String,Integer> ACKs = new HashMap<String,Integer>();

	
	public MulticastMessage(String src,  String dest, String kind, MulticastType type, Object data) {
		super( dest, kind, data);
		this.src = src;
		this.type = type;
//		this.setTimeStamp(ts);
	}

	public MulticastMessage(int multicastSeq, String src, String dest, String kind, MulticastType type, Object data) {
		this(src,dest, kind , type, data);
		this.multicastSeq = multicastSeq;
	}

	public int getmulticastSeq() {
		return multicastSeq;
	}

	public void setmulticastSeq(int multicastSeq) {
		this.multicastSeq = multicastSeq;
	}

	

	public MulticastType getType() {
		return type;
	}

	public void setType(MulticastType type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return this.multicastSeq + " | " + this.type + "| " + this.getSrc() + "->" +this.getSrc() + "->" + this.getDest();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((this.src == null) ? 0 : this.src.hashCode());
		result = prime * result + multicastSeq;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}
/*
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		MulticastMessage other = (MulticastMessage) obj;
		if (this.myDup != null)
			return this.myDup == other;
		else
			return this.getmulticastSeq() == other.getmulticastSeq() && this.getthis.src().equals(other.getthis.src());//<src, id> unique
	
	}

	@Override
	public MulticastMessage clone() throws CloneNotSupportedException {
		MulticastMessage msg = new MulticastMessage(getmulticastSeq(), getthis.src(), getSrc(), getDest(), getKind(), type, data);
		msg.setTimeStamp(getTimeStamp());
		return msg;
	}
*/
	public String getGroupID() {
		return groupID;
	}

	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}

	public HashMap<String,Integer> getACKs() {
		return ACKs;
	}

	public void setACKs(HashMap<String,Integer> aCKs) {
		ACKs = aCKs;
	}

	
	@Override
    public String toString() {
        return "Multicast: Multiseq: "+this.getmulticastSeq()
        		"\nFrom:" + this.getSrc() + " to:" + this.getDest() +
               "\nSeq:" + this.getSeqNum() + " Kind:" + this.getKind()
               + " Dup:" + this.getDuplicate() + " TimeStamp: " + this.getTimeStamp().toString()
               + " [Data:" + this.getData() + " ]";
    }
}
