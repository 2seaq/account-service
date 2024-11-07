// Distributed under the MIT software license
package com.osys.wallet.scheduled;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.osys.wallet.model.Invoice;
import com.osys.wallet.model.Manager;
import com.osys.wallet.repository.InvoiceRepository;
import com.osys.wallet.repository.ManagerRepository;
import com.osys.wallet.repository.PayRepository;
import com.osys.wallet.service.LightningServiceImpl;

@Component
@EnableScheduling
public class APIScheduler {

	private InvoiceRepository invoiceRepository;
    private PayRepository payRepository;
    private final ManagerRepository managerRepository;
    private final SimpMessageSendingOperations messagingTemplate;

	@Autowired
    private LightningServiceImpl lightningServiceImpl;

    public APIScheduler(ManagerRepository managerRepository, 
    SimpMessageSendingOperations messagingTemplate) {
		this.managerRepository = managerRepository;
        this.messagingTemplate = messagingTemplate;
    }

    @Autowired
    public void setInvoiceRepository(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    } 

    @Autowired
    public void setPayRepository(PayRepository payRepository) {
        this.payRepository = payRepository;
    } 

    @Scheduled(fixedRate = 10000)
    public void callAPIPeriodically() {

        List<Invoice> allPendingInvoices = (List<Invoice>) invoiceRepository.findAllPending();

		for (Invoice item : allPendingInvoices) {
			 lightningServiceImpl.getInvoice(item);
			 if(item.getStatus().equalsIgnoreCase("paid")){
                Manager manager = this.managerRepository.findById(item.getManager().getId());
                 invoiceRepository.save(item);
                 messagingTemplate.convertAndSendToUser(manager.getSub(), "/queue/invoicePaid", "invoicePaid");
			 }

 			 if(item.getStatus().equalsIgnoreCase("expired") ){
                invoiceRepository.delete(item);
                Manager manager = this.managerRepository.findById(item.getManager().getId());
                messagingTemplate.convertAndSendToUser(manager.getSub(), "/queue/invoicePaid", "invoiceDeleted");
		    }		
        }
    }
}