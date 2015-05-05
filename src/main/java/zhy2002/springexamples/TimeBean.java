package zhy2002.springexamples;


import java.util.Date;

public class TimeBean {

    private Date time;

    public TimeBean(){
        time = new Date();
    }

    public Date getTime(){
        return time;
    }
}
