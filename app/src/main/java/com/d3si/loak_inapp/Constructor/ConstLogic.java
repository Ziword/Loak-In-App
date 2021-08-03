package com.d3si.loak_inapp.Constructor;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConstLogic
{
    @SerializedName("MESSAGE")
    @Expose
    private String MESSAGE;

    public String getMESSAGE() {
        return MESSAGE;
    }

    public void setMESSAGE(String MESSAGE) {
        this.MESSAGE = MESSAGE;
    }
}
