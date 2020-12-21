package com.example.messengerb.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//Model for JSON for One Message
public class Message {
    @Expose
    @SerializedName("id")
    private String id;

    @Expose
    @SerializedName("senderid")
    private String senderid;

    @Expose
    @SerializedName("recieverid")
    private String recieverid;

    @Expose
    @SerializedName("encode")
    private int encode;

    @Expose
    @SerializedName("msg")
    private String msg;

    public Message(String id, String senderid, String recieverid, int encode, String msg) {
        this.id = id;
        this.senderid = senderid;
        this.recieverid = recieverid;
        this.encode = encode;
        this.msg = msg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSenderid() {
        return senderid;
    }

    public void setSenderid(String senderid) {
        this.senderid = senderid;
    }

    public String getRecieverid() {
        return recieverid;
    }

    public void setRecieverid(String recieverid) {
        this.recieverid = recieverid;
    }

    public int getEncode() {
        return encode;
    }

    public void setEncode(int encode) {
        this.encode = encode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
