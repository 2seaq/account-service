// Distributed under the MIT software license
package com.osys.wallet.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.osys.wallet.model.Account;
import com.osys.wallet.model.BitcoinAddress;
import com.osys.wallet.repository.BitcoinAddressRepository;
import com.osys.wallet.repository.InvoiceRepository;
import com.osys.wallet.repository.PayRepository;
import com.osys.wallet.response.ResponseBlock;
import com.osys.wallet.response.ResponseDecode;
import com.osys.wallet.response.ResponseListfunds;
import com.osys.wallet.response.ResponseListfundsOutput;
import com.osys.wallet.service.LightningServiceImpl;
import com.osys.wallet.service.OnChainServiceImpl;

@RestController
@RequestMapping("/osys/wallet")
public class AccountController {

    private OnChainServiceImpl onChainService;
    private BitcoinAddressRepository bitcoinAddressRepository;
    private InvoiceRepository invoiceRepository;
    private PayRepository payRepository;
    private LightningServiceImpl lightningServiceImpl;

    public AccountController() {

    }

    @Autowired
    public void setLightningServiceImpl(LightningServiceImpl lightningServiceImpl) {
        this.lightningServiceImpl = lightningServiceImpl;
    } 
    
    @Autowired
    public void setBitcoinAddressRepository(BitcoinAddressRepository bitcoinAddressRepository) {
        this.bitcoinAddressRepository = bitcoinAddressRepository;
    }
    
    @Autowired
    public void setInvoiceRepository(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    } 

    @Autowired
    public void setPayRepository(PayRepository payRepository) {
        this.payRepository = payRepository;
    } 

    @Autowired
    public void setOnChainService(OnChainServiceImpl onChainService) {
        this.onChainService = onChainService;
    }

    @GetMapping("/blockinfo")
    public EntityModel<ResponseBlock> getBlockInfo(@RequestParam("block")Long blockHeight) {

        ResponseBlock block = onChainService.getBlockInfo(blockHeight);

    	return EntityModel.of(block);
    }

    @GetMapping("/decode")
    public EntityModel<ResponseDecode> getDecode(@RequestParam("bolt11")String bolt11) {

        ResponseDecode rd = new ResponseDecode();
        lightningServiceImpl.decode(bolt11,rd);  
    	return EntityModel.of(rd);
    }

    @GetMapping("/funds")
    public EntityModel<ResponseListfunds> getFunds() {

        List<BitcoinAddress> allBitcoinAddresses = (List<BitcoinAddress>) bitcoinAddressRepository.findAllByManager();
        String btcAddress = allBitcoinAddresses.get(0).getBtcAddress();

        ResponseListfunds rd = new ResponseListfunds();
        lightningServiceImpl.listFunds(rd);  

        List<ResponseListfundsOutput> rdOutputs = rd.getOutputs();
        ArrayList<ResponseListfundsOutput> sortedOutputs = new ArrayList<ResponseListfundsOutput>();

        for (ResponseListfundsOutput output:rdOutputs ){
            if(output.getAddress().equals(btcAddress)) {

                ResponseBlock block = onChainService.getBlockInfo(output.getBlockheight());
//                System.out.println("OSYS - Block check " + block.getTimestamp());
                output.setBlockheight(block.getTimestamp());
                sortedOutputs.add(output);
            }
        }
        rd.setOutputs(sortedOutputs);

    	return EntityModel.of(rd);
    }

    @GetMapping("/account")
    public EntityModel<Account> getTotal() {
    	
        List<BitcoinAddress> allBitcoinAddresses = (List<BitcoinAddress>) bitcoinAddressRepository.findAllByManager();

        Long depositTotal = 0L;
        Long paysTotal = 0L;
        Long invoicesTotal = 0L;
        Long fetchPaysTotal = payRepository.totalAmount();
        Long fetchInvoicesTotal = invoiceRepository.totalAmount();

        depositTotal = onChainService.getDepositsTotal(allBitcoinAddresses.get(0).getBtcAddress()) * 1000;


        if (fetchPaysTotal == null) {
             paysTotal = 0L;
        } else {
            paysTotal = fetchPaysTotal;
        }

        if (fetchInvoicesTotal == null) {
           invoicesTotal = 0L;
        } else {
            invoicesTotal = fetchInvoicesTotal;
        }

//    	System.out.println("OSYS - Deposit Controller - Totals mSat: " + depositTotal.toString());
//   	System.out.println("OSYS - Pays Controller - Totals mSat: " + paysTotal.toString());
//    	System.out.println("OSYS - Invoices Controller - Totals mSat: " + invoicesTotal.toString());

    	Account myAccount = new Account(allBitcoinAddresses.get(0).getBtcAddress(), paysTotal
        , invoicesTotal, depositTotal);
    	
    	return EntityModel.of(myAccount);
    }    
        
}