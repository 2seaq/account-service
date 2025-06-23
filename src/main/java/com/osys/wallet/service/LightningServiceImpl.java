// Distributed under the MIT software license
package com.osys.wallet.service;

import jakarta.annotation.PostConstruct;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;
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

	@Value("${app.clnrest.address}")
	private String clnrestaddress;

	@Value("${app.clnrest.rune}")
	private String clnrestrune;

	@PostConstruct
	private void init() {

		this.webClient = WebClient.builder()
				.baseUrl(clnrestaddress)
				.defaultHeaders(
						headers -> {
							headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
							headers.add("Rune", clnrestrune);
						})
				.build();
	}

	@Override
	public Invoice createInvoice(Invoice invoice) {
		// Create a JSON-compatible map to represent the invoice details
		Map<String, Object> bodyValues = new HashMap<>();
		bodyValues.put("amount_msat", invoice.getAmountMsat());
		bodyValues.put("label", invoice.getLabel());
		bodyValues.put("description", invoice.getDescription());
		bodyValues.put("expiry", 1800); // Send expiry as a number

		// Make the POST request with the JSON body
		Mono<ResponseInvoice> respinvoice = webClient.post()
				.uri("/v1/invoice")
				.contentType(MediaType.APPLICATION_JSON) // Set Content-Type to application/json
				.bodyValue(bodyValues) // Use bodyValue to directly send the map as JSON
				.retrieve()
				.bodyToMono(ResponseInvoice.class)
				.log();

		// Block and get the response
		ResponseInvoice ri = respinvoice.block();

		// Update the invoice with details from the response
		invoice.setBolt11(ri.getBolt11());
		invoice.setPayment_hash(ri.getPayment_hash());
		invoice.setStatus("pending");
		invoice.setTimestamp(System.currentTimeMillis());

		return invoice; // Return the updated invoice
	}

	@Override
	public Invoice getInvoice(Invoice invoice) {

		Map<String, Object> bodyValues = new HashMap<>();
		bodyValues.put("label", invoice.getLabel());

		Mono<ResponseInvoices> respinvoice = webClient.post()
				.uri("/v1/listinvoices")
				.contentType(MediaType.APPLICATION_JSON) // Set Content-Type to application/json
				.bodyValue(bodyValues) // Use bodyValue to directly send the map as JSON
				.retrieve()
				.bodyToMono(ResponseInvoices.class).log();

		ResponseInvoices ri = respinvoice.block();
		invoice.setStatus(ri.getInvoices().get(0).getStatus());

		return null;
	}

	public ResponseListfunds listFunds(ResponseListfunds responseListfunds) {

		Mono<ResponseListfunds> respfunds = webClient.post()
				.uri("/v1/listfunds")
				.retrieve()
				.bodyToMono(ResponseListfunds.class).log();

		ResponseListfunds ri = respfunds.block();

		responseListfunds.setOutputs(ri.getOutputs());
		return null;
	}

	public Pay decode(Pay pay) {

		Map<String, Object> bodyValues = new HashMap<>();
		bodyValues.put("bolt11", pay.getBolt11());

		Mono<ResponseDecode> respdecode = webClient.post()
				.uri("/v1/decodepay")
				.contentType(MediaType.APPLICATION_JSON) // Set Content-Type to application/json
				.bodyValue(bodyValues) // Use bodyValue to directly send the map as JSON
				.retrieve()
				.bodyToMono(ResponseDecode.class).log();

		ResponseDecode rd = respdecode.block();
		pay.setAmountMsat(rd.getAmount_msat());
		return null;
	}

	public ResponseDecode decode(String bolt11, ResponseDecode responseDecodeIn) {

		Map<String, Object> bodyValues = new HashMap<>();
		bodyValues.put("bolt11", bolt11);

		Mono<ResponseDecode> respdecode = webClient.post()
				.uri("/v1/decodepay")
				.contentType(MediaType.APPLICATION_JSON) // Set Content-Type to application/json
				.bodyValue(bodyValues) // Use bodyValue to directly send the map as JSON
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
		System.out.println("OSYS - payInvoice");

		Map<String, Object> bodyValues = new HashMap<>();
		bodyValues.put("bolt11", bolt11);
		bodyValues.put("label", "Osys wallet Payment");

		String resppay = webClient.post()
				.uri("/v1/pay")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(bodyValues)
				.retrieve()
				.bodyToMono(ResponsePay.class)
				.doOnNext(rp -> {
					System.out.println("OSYS - ResponsePay status: " + rp.getStatus());
				})
				.map(rp -> "Resp Pay: " + rp.getStatus())
				.onErrorResume(e -> {
					System.err.println("OSYS - Error paying invoice: " + e.getMessage());
//					e.printStackTrace(); // Print full stack trace
					return Mono.just("failure");
				})
				.log()
				.block();

		return resppay;
	}

	public String newAddress() {
//		System.out.println("OSYS - newAddress: ");

		Mono<ResponseNewAddress> respMono = webClient.post()
				.uri("/v1/newaddr")
				.retrieve()
				.bodyToMono(ResponseNewAddress.class)
				.log();

		ResponseNewAddress response = respMono.block();
		if (response == null || response.getAddress() == null) {
			throw new RuntimeException("Failed to get a valid address from the response");
		}

//		System.out.println("OSYS - rd.getAddress(): " + response.getAddress());
		return response.getAddress();
	}

}