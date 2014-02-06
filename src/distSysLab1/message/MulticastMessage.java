package distSysLab1.message;



public class MulticastMessage extends TimeStampMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int multicastSeq;
	private String groupID;
	private String source;
	private MulticastType type;

	
	public MulticastMessage(String source,  String dest, String kind, MulticastType type, Object data) {
		super( dest, kind, data);
		this.source = source;
		this.type = type;
//		this.setTimeStamp(ts);
	}

	public MulticastMessage(int multicastSeq, String source, String dest, String kind, MulticastType type, Object data) {
		this(source,dest, kind , type, data);
		this.multicastSeq = multicastSeq;
	}

	public int getmulticastSeq() {
		return multicastSeq;
	}

	public void setmulticastSeq(int multicastSeq) {
		this.multicastSeq = multicastSeq;
	}

	public String getsource() {
		return source;
	}

	public void setsource(String source) {
		this.source = source;
	}

	public MulticastType getType() {
		return type;
	}

	public void setType(MulticastType type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return this.multicastSeq + " | " + this.type + "| " + this.getsource() + "->" +this.getSrc() + "->" + this.getDest();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((source == null) ? 0 : source.hashCode());
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
			return this.getmulticastSeq() == other.getmulticastSeq() && this.getsource().equals(other.getsource());//<src, id> unique
	
	}

	@Override
	public MulticastMessage clone() throws CloneNotSupportedException {
		MulticastMessage msg = new MulticastMessage(getmulticastSeq(), getsource(), getSrc(), getDest(), getKind(), type, data);
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

	
	
}
