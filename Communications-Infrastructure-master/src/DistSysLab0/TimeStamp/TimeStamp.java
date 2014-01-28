package DistSysLab0.TimeStamp;

import java.io.Serializable;

public abstract class TimeStamp<E> implements Comparable<E>, Serializable{
	
	/**
	 * 
	 */
	private int val=-1;
	private static final long serialVersionUID = 3L;

	public TimeStamp(){}
	public int getVal(){
		return val;
	}
}
