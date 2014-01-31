package distSysLab1.timeStamp;

import java.io.Serializable;

public abstract class TimeStamp implements Serializable {

    private static final long serialVersionUID = 1L;

    abstract public void setTimeStamp(Object o);
    
    abstract public Object getTimeStamp();
}
