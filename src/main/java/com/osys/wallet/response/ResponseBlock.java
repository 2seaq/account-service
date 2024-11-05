package com.osys.wallet.response;

public class ResponseBlock {

	private String id;
	private Long height;
	private Long version;
	private Long timestamp;
   	private Long tx_count;
   	private Long size;
   	private Long weight;
	private String merkle_root;
    private String previousblockhash;
   	private Long mediantime;
   	private Long nonce;
   	private Long bits;
   	private Long difficulty;
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public Long getHeight() {
        return height;
    }
    public void setHeight(Long height) {
        this.height = height;
    }
    public Long getVersion() {
        return version;
    }
    public void setVersion(Long version) {
        this.version = version;
    }
    public Long getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
    public Long getTx_count() {
        return tx_count;
    }
    public void setTx_count(Long tx_count) {
        this.tx_count = tx_count;
    }
    public Long getSize() {
        return size;
    }
    public void setSize(Long size) {
        this.size = size;
    }
    public Long getWeight() {
        return weight;
    }
    public void setWeight(Long weight) {
        this.weight = weight;
    }
    public String getMerkle_root() {
        return merkle_root;
    }
    public void setMerkle_root(String merkle_root) {
        this.merkle_root = merkle_root;
    }
    public String getPreviousblockhash() {
        return previousblockhash;
    }
    public void setPreviousblockhash(String previousblockhash) {
        this.previousblockhash = previousblockhash;
    }
    public Long getMediantime() {
        return mediantime;
    }
    public void setMediantime(Long mediantime) {
        this.mediantime = mediantime;
    }
    public Long getNonce() {
        return nonce;
    }
    public void setNonce(Long nonce) {
        this.nonce = nonce;
    }
    public Long getBits() {
        return bits;
    }
    public void setBits(Long bits) {
        this.bits = bits;
    }
    public Long getDifficulty() {
        return difficulty;
    }
    public void setDifficulty(Long difficulty) {
        this.difficulty = difficulty;
    }



    
}
