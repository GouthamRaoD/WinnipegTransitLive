package com.youngbro.mytransitlive;


import android.support.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


class Bus implements Comparable<Bus> {
    String sdeparture;
    String edeparture;
    String qTime;
    String route;
    String arrival;
    SimpleDateFormat sdf;
    SimpleDateFormat ssd;
    SimpleDateFormat s;
    SimpleDateFormat sdr;
    String format;
    String rd;
    Date etime;
    Date stime;
    Date r;
    private String rname;
    public Bus(String sd, String ed, String varient, String route,String qtime){
        this.edeparture=ed;
        this.sdeparture=sd;
        this.qTime = qtime;
        String dat = edeparture.substring(0, 10);
        arrival = edeparture.substring(11,19);
        this.route = route;
        format = dat +" "+arrival;
        this.rname = varient;
        sdf = new SimpleDateFormat("HH:mm:ss");
        s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ssd = new SimpleDateFormat("hh:mm");
        rd = qtime.replace("T"," ");
    }
    public String getRname(){return rname;}
    public String getEarrive(){
        try {
            etime = s.parse(format);
            stime = s.parse(rd);
            if(etime.getTime()-stime.getTime() < 900000)
            {
                if((etime.getTime()-stime.getTime())/60000 == 0)
                {
                    return "Due";
                }
                else
                    return (etime.getTime()-stime.getTime())/60000 + " min";
            }
            else {
                if (sdf.format(etime).compareTo("12:00:00") > 0) {
                    return ssd.format(etime) + "p";
                } else {
                    return ssd.format(etime) + "a";
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }
    public String getSdeparture(){return sdeparture;}
    public String getEdeparture(){return edeparture;}
    public String getName(){return rname;}
    public String getRoute(){return route;}
    public String getStatus() throws ParseException {
        etime = sdf.parse(arrival);
        stime = sdf.parse(sdeparture);
        String status ="Ok";
        if(etime.getTime()-stime.getTime() > 3000)
        {
            status = "Late";
        }
        else if(etime.getTime()-stime.getTime() < -300){
            status = "Early";
        }
        return status;
    }
    @Override
    public int compareTo(@NonNull Bus o) {
        return this.getEdeparture().compareTo(o.getEdeparture());
    }

}
