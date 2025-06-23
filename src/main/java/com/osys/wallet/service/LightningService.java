// Distributed under the MIT software license
package com.osys.wallet.service;

import com.osys.wallet.model.Invoice;

public interface LightningService {
    
    Invoice createInvoice(Invoice invoice);
    
    Invoice getInvoice(Invoice invoice);
    
    String payInvoice(String bolt11);

}