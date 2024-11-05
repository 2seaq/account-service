// Copyright (c) 2024 Chris Jackson
// Distributed under the MIT software license
package com.osys.wallet.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.osys.wallet.model.Invoice;
import com.osys.wallet.model.Manager;
import com.osys.wallet.repository.ManagerRepository;
import com.osys.wallet.service.LightningServiceImpl;

import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.context.SecurityContextHolder;


@Component
@RepositoryEventHandler(Invoice.class) // <1>
public class SpringDataRestEventHandlerInvoice {

	private final ManagerRepository managerRepository;


    private final SimpMessageSendingOperations messagingTemplate;

	
    @Autowired
    private LightningServiceImpl lightningServiceImpl;

	@Autowired
	public SpringDataRestEventHandlerInvoice(ManagerRepository managerRepository, SimpMessageSendingOperations messagingTemplate) {
		this.managerRepository = managerRepository;
		this.messagingTemplate = messagingTemplate;
	}

	@HandleBeforeCreate
	@HandleBeforeSave
	public void applyUserInformationUsingSecurityContext(Invoice invoice) {

		System.out.println("SpringDataRestEventHandlerInvoice  - applyUserInformationUsingSecurityContext " + invoice.getBolt11());

		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		Manager manager = this.managerRepository.findBySub(name);
		if (manager == null) {
	//		Manager newManager = new Manager();
		//	newManager.setName(name);
			//manager = this.managerRepository.save(newManager);
			System.out.println("SpringDataRestEventHandlerInvoice - applyUserInformationUsingSecurityContext NOT found the Manager with that sub " + name);
		}
		invoice.setManager(manager);
		lightningServiceImpl.createInvoice(invoice);
	}

	//@HandleAfterSave
	public void sendWebMessage(Invoice invoice) {

		System.out.println("SpringDataRestEventHandlerInvoice  - sendWebMessage " + invoice.getBolt11());
	
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		messagingTemplate.convertAndSendToUser(name, "/queue/messages", "invoice has been created by cj");
	}

	@HandleAfterSave
	public void sendWebMessageNew(Invoice invoice) {

		System.out.println("SpringDataRestEventHandlerInvoice  - sendWebMessage " + invoice.getBolt11());
	
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		messagingTemplate.convertAndSendToUser(name, "/queue/invoicePaid", "invoicePaid");
	}


	@HandleAfterCreate
	public void sendWebMessageAfterCreate(Invoice invoice) {

		System.out.println("SpringDataRestEventHandlerInvoice  - sendWebMessage " + invoice.getBolt11());
	
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		messagingTemplate.convertAndSendToUser(name, "/queue/newInvoiceCreated", "newInvoiceCreated");
	}

}

