// Copyright (c) 2024 Chris Jackson
// Distributed under the MIT software license
package com.osys.wallet.service;
import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.osys.wallet.model.Invoice;
import com.osys.wallet.model.Pay;
import com.osys.wallet.response.ResponseDecode;
import com.osys.wallet.response.ResponseInvoice;
import com.osys.wallet.response.ResponseInvoices;
import com.osys.wallet.response.ResponseListfunds;
import com.osys.wallet.response.ResponseNewAddress;
import com.osys.wallet.response.ResponsePay;

import reactor.core.publisher.Mono;

@Service
public class LightningServiceImpl implements LightningService {

	private WebClient webClient;

	@Value("${app.chain}")
	private String chain;
	
	@Value("${app.clnserver.address}")
	private String address;

	@Value("${app.clnserver.macaroon}")
	private String macaroon;

	@PostConstruct
	private void init() {

		this.webClient = WebClient.builder()
				  .baseUrl(address)
				  .defaultHeaders(
						    headers -> {
						      headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
						      headers.add("macaroon", macaroon);
						    })				  
				  .build();	
	}
	
	@Override
	public Invoice createInvoice(Invoice invoice) {
				
		MultiValueMap<String, String> bodyValues = new LinkedMultiValueMap<>();
		bodyValues.add("amount", invoice.getAmountMsat().toString());
		bodyValues.add("label", invoice.getLabel());
		bodyValues.add("description", invoice.getDescription());		
		bodyValues.add("expiry", "1800");

		Mono<ResponseInvoice> respinvoice = webClient.post()
	      .uri("/v1/invoice/genInvoice")
	      .body(BodyInserters.fromFormData(bodyValues))
	      .retrieve()
	      .bodyToMono(ResponseInvoice.class).log();
		
		ResponseInvoice ri = respinvoice.block();
		
		invoice.setBolt11(ri.getBolt11());
		invoice.setPayment_hash(ri.getPayment_hash());
		invoice.setStatus("pending");
		invoice.setTimestamp(System.currentTimeMillis());
		return null;
	}

	@Override
	public Invoice getInvoice(Invoice invoice) {

		Mono<ResponseInvoices> respinvoice = webClient.get()
			      .uri("/v1/invoice/listInvoices?label=" + invoice.getLabel())
			      .retrieve()
			      .bodyToMono(ResponseInvoices.class).log();
				
		ResponseInvoices ri = respinvoice.block();
		invoice.setStatus(ri.getInvoices().get(0).getStatus());

		return null;
	}

	public ResponseListfunds listFunds(ResponseListfunds responseListfunds) {

		Mono<ResponseListfunds> respfunds= webClient.get()
			      .uri("/v1/listFunds")
			      .retrieve()
			      .bodyToMono(ResponseListfunds.class).log();
				
		ResponseListfunds ri = respfunds.block();

		responseListfunds.setOutputs(ri.getOutputs());
		return null;
	}

	public Pay decode(Pay pay) {

		Mono<ResponseDecode> respdecode = webClient.get()
			      .uri("/v1/utility/decode/"+ pay.getBolt11())
			      .retrieve()
			      .bodyToMono(ResponseDecode.class).log();
				
		ResponseDecode rd = respdecode.block();
		pay.setAmountMsat(rd.getAmount_msat());
		return null;
	}


	public ResponseDecode decode(String bolt11, ResponseDecode responseDecodeIn) {

		Mono<ResponseDecode> respdecode = webClient.get()
			      .uri("/v1/utility/decode/"+ bolt11)
			      .retrieve()
			      .bodyToMono(ResponseDecode.class).log();
				
		ResponseDecode rd = respdecode.block();		  

		responseDecodeIn.setDescription(rd.getDescription());
		responseDecodeIn.setAmount_msat(rd.getAmount_msat());
		responseDecodeIn.setPayee(rd.getPayee());

		return null;
	}

	@Override
    @ResponseBody
	public String payInvoice(String bolt11) {
		
		MultiValueMap<String, String> bodyValues = new LinkedMultiValueMap<>();
		bodyValues.add("invoice", bolt11);	
		bodyValues.add("label", "Osys wallet Payment");	

		String resppay = webClient.post()
	      .uri("/v1/pay")
	      .body(BodyInserters.fromFormData(bodyValues))
	      .retrieve()
	      .bodyToMono(ResponsePay.class)
		  .flatMap(rp -> {return Mono.just("Resp Pay" + rp.getStatus()) ;})
		  .onErrorReturn("failure")
		  .block();

		return resppay;
	}

	public String newAddress() {

		Mono<ResponseNewAddress> respnewaddr = webClient.get()
			      .uri("/v1/newaddr")
			      .retrieve()
			      .bodyToMono(ResponseNewAddress.class).log();
				
		ResponseNewAddress rd = respnewaddr.block();		  
		return rd.getAddress();
	}

}