package com.youngbro.mytransitlive;

import android.support.annotation.NonNull;

/**
 * Created by Goutham on 2017-03-07.
 */

public class DbData implements Comparable<DbData>{
    private String _stopNo;
    private String _stopName;

    public DbData() {
    }

    public DbData(String _stopName, String _stopNo) {
        this._stopName = _stopName;
        this._stopNo = _stopNo;
    }

    public String get_stopNo() {
        return _stopNo;
    }

    public String get_stopName() {
        return _stopName;
    }

    public void set_stopNo(String _stopNo) {
        this._stopNo = _stopNo;
    }

    public void set_stopName(String _stopName) {
        this._stopName = _stopName;
    }


    @Override
    public int compareTo(@NonNull DbData o) {
        return this.get_stopNo().compareTo(o.get_stopNo());
    }
}
