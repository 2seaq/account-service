package com.osys.wallet.response;

import java.util.List;

public class ResponseListfunds {
	
	List<ResponseListfundsOutput> outputs;
    List<ResponseListfundsChannel> channels;

	public List<ResponseListfundsOutput> getOutputs() {
		return outputs;
	}

	public void setOutputs(List<ResponseListfundsOutput> outputs) {
		this.outputs = outputs;
	}

    public List<ResponseListfundsChannel> getChannels() {
        return channels;
    }

    public void setChannels(List<ResponseListfundsChannel> channels) {
        this.channels = channels;
    }

}

