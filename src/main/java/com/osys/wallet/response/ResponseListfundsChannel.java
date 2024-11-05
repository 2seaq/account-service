package com.osys.wallet.response;

public class ResponseListfundsChannel {

    private String peer_id;
    private Boolean connected;
    private String state;
    private String channel_id;
    private String short_channel_id;
    private Long our_amount_msat;
    private Long amount_msat;
    private String funding_txid;
    private Long funding_output;
    public String getPeer_id() {
        return peer_id;
    }
    public void setPeer_id(String peer_id) {
        this.peer_id = peer_id;
    }
    public Boolean getConnected() {
        return connected;
    }
    public void setConnected(Boolean connected) {
        this.connected = connected;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getChannel_id() {
        return channel_id;
    }
    public void setChannel_id(String channel_id) {
        this.channel_id = channel_id;
    }
    public String getShort_channel_id() {
        return short_channel_id;
    }
    public void setShort_channel_id(String short_channel_id) {
        this.short_channel_id = short_channel_id;
    }
    public Long getOur_amount_msat() {
        return our_amount_msat;
    }
    public void setOur_amount_msat(Long our_amount_msat) {
        this.our_amount_msat = our_amount_msat;
    }
    public Long getAmount_msat() {
        return amount_msat;
    }
    public void setAmount_msat(Long amount_msat) {
        this.amount_msat = amount_msat;
    }
    public String getFunding_txid() {
        return funding_txid;
    }
    public void setFunding_txid(String funding_txid) {
        this.funding_txid = funding_txid;
    }
    public Long getFunding_output() {
        return funding_output;
    }
    public void setFunding_output(Long funding_output) {
        this.funding_output = funding_output;
    }	

    
}
