package com.andriikichmarenko.attendancecheckup.model;

import android.support.annotation.NonNull;

public class Member implements Comparable<Member>{

    private String mInfo;
    private String mName;

    public Member(String name, String info) {
        this.mName = name;
        this.mInfo = info;
    }

    public String getmInfo() {
        return mInfo;
    }

    public void setmInfo(String mInfo) {
        this.mInfo = mInfo;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    @Override
    public int compareTo(@NonNull Member o) {
        if(mInfo.equals(o.getmInfo()) && mName.equals(o.getmName())){
            return 1;
        }
        return 0;
    }
}
