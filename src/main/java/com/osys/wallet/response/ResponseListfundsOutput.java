package com.osys.wallet.response;

public class ResponseListfundsOutput {


    private String txid;
    private Long output;
    private Long amount_msat;
    private String scriptpubkey;
    private String address;
    private String status;
    private Long blockheight;
    private Boolean reserved;
    
    public String getTxid() {
        return txid;
    }
    public void setTxid(String txid) {
        this.txid = txid;
    }
    public Long getOutput() {
        return output;
    }
    public void setOutput(Long output) {
        this.output = output;
    }
    public Long getAmount_msat() {
        return amount_msat;
    }
    public void setAmount_msat(Long amount_msat) {
        this.amount_msat = amount_msat;
    }
    public String getScriptpubkey() {
        return scriptpubkey;
    }
    public void setScriptpubkey(String scriptpubkey) {
        this.scriptpubkey = scriptpubkey;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Long getBlockheight() {
        return blockheight;
    }
    public void setBlockheight(Long blockheight) {
        this.blockheight = blockheight;
    }
    public Boolean getReserved() {
        return reserved;
    }
    public void setReserved(Boolean reserved) {
        this.reserved = reserved;
    }

    
	
}
