// Copyright (c) 2024 Chris Jackson
// Distributed under the MIT software license
package com.osys.wallet.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.osys.wallet.model.Invoice;
import com.osys.wallet.model.Manager;
import com.osys.wallet.model.Pay;
import com.osys.wallet.repository.InvoiceRepository;
import com.osys.wallet.repository.ManagerRepository;
import com.osys.wallet.repository.PayRepository;
import com.osys.wallet.service.LightningServiceImpl;

import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.context.SecurityContextHolder;

@Component
@RepositoryEventHandler(Pay.class) // <1>
public class SpringDataRestEventHandlerPay {

	private final ManagerRepository managerRepository;
    private final SimpMessageSendingOperations messagingTemplate;    
	private InvoiceRepository invoiceRepository;
	private PayRepository payRepository;

    @Autowired
    private LightningServiceImpl lightningServiceImpl;

	@Autowired
	public SpringDataRestEventHandlerPay(ManagerRepository managerRepository, SimpMessageSendingOperations messagingTemplate) {
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

	@HandleBeforeCreate
	@HandleBeforeSave
	public void applyUserInformationUsingSecurityContext(Pay pay) {

		String name = SecurityContextHolder.getContext().getAuthentication().getName();

		System.out.println("Attempting to Pay applyUserInformationUsingSecurityContext name value is : " + name);
		String payStatus;

		//String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().getEmail();

		// Set The Manager for the Pay

		Manager manager = this.managerRepository.findBySub(name);
		if (manager == null) {
		//	Manager newManager = new Manager();
		//	newManager.setName(name);
		//	manager = this.managerRepository.save(newManager);
			System.out.println("SpringDataRestEventHandlerPay - applyUserInformationUsingSecurityContext NOT found the Manager with that sub " + name);
		} else {
			System.out.println("I found the Manager with that sub " + name);
		}
		pay.setManager(manager);



		// Determine if intercentre payment
		if(pay.getBolt11Payee().equals("027dae933ef842080ae42c9c2eb76d12e340a2e50b70a66d7a12c3e6febd3f1562")) {
			System.out.println("MAKE INTERPAYMENT  - invoiceRepo.findbyBollt - set to Paid");

			Invoice interInvoice = invoiceRepository.findByBolt11(pay.getBolt11()).orElse(null);
			interInvoice.setStatus("internalpaid");
			invoiceRepository.save(interInvoice);
			pay.setStatus("internalsuccess");
			Manager manager2 = this.managerRepository.findById(interInvoice.getManager().getId());
			messagingTemplate.convertAndSendToUser(manager2.getName(), "/queue/invoicePaid", "invoicePaid");

		} else {
			payStatus = lightningServiceImpl.payInvoice(pay.getBolt11());

			System.out.println("Event Handler lightningServiceImpl.payInvoice payStatus: " + payStatus);
			if(payStatus.equals("Resp Paycomplete")){
				pay.setStatus("success");
			} else {
				pay.setStatus("failure");
			}
		}
		// Determine if invoice already paid.
		Pay existingPay = payRepository.findByBolt11(pay.getBolt11()).orElse(null);
		if(existingPay!=null){
			pay.setStatus("duplicate");
		}
	}

	@HandleAfterCreate
	public void sendWebMessageNew(Pay pay) {
	
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		messagingTemplate.convertAndSendToUser(name, "/queue/newPayCreated", "newPayCreated");
	}

}

