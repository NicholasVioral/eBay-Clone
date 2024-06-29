package edu.sru.cpsc.webshopping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.sru.cpsc.webshopping.domain.billing.DirectDepositDetails;
import edu.sru.cpsc.webshopping.repository.billing.DirectDepositDetailsRepository;

@Service
public class PaymentService {

    @Autowired
    private DirectDepositDetailsRepository directDepositDetailsRepository;

    public DirectDepositDetails getDirectDepositDetails(long id) {
        return directDepositDetailsRepository.findById(id).orElse(null);
    }
    
}
