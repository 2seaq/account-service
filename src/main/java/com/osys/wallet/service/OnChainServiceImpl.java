// Copyright (c) 2024 Chris Jackson
// Distributed under the MIT software license
package com.osys.wallet.service;
import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.osys.wallet.response.ResponseAddress;
import com.osys.wallet.response.ResponseBlock;

import reactor.core.publisher.Mono;

@Service
public class OnChainServiceImpl implements OnChainService {

	private WebClient webClient;

	@Value("${app.chain}")
	private String chain;

	@Value("${app.blockexplorer}")
	private String blockexplorer;
	
	@PostConstruct
	private void init() {

		this.webClient = WebClient.builder()
				  .baseUrl(blockexplorer)
				  .defaultHeaders(
						    headers -> {
						      headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
						    })				  
				  .build();	
	}

    @Override
    public Long getDepositsTotal(String addressIn) {

		Mono<ResponseAddress> respaddress = webClient.get()
			      .uri("/api/address/" + addressIn)
			      .retrieve()
			      .bodyToMono(ResponseAddress.class).log();
				
		ResponseAddress ri = respaddress.block();
		
		return ri.getChain_stats().getFunded_txo_sum();
    }

    public ResponseBlock getBlockInfo(Long block) {

		Mono<String> resphash = webClient.get()
			      .uri("/api/block-height/" + block)
			      .retrieve()
			      .bodyToMono(String.class).log();

		String blockhash = resphash.block();

		Mono<ResponseBlock> respblock = webClient.get()
			      .uri("/api/block/" + blockhash)
			      .retrieve()
			      .bodyToMono(ResponseBlock.class).log();	

		ResponseBlock ri = respblock.block();
		return ri;
    }

}