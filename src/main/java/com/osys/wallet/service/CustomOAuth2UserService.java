package com.osys.wallet.service;

import com.osys.wallet.model.BitcoinAddress;
import com.osys.wallet.model.GitHubUserInfo;
import com.osys.wallet.model.Manager;
import com.osys.wallet.model.Invoice;
import com.osys.wallet.repository.BitcoinAddressRepository;
import com.osys.wallet.repository.ManagerRepository;
import com.osys.wallet.repository.InvoiceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private BitcoinAddressRepository bitcoinAddressRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private LightningServiceImpl lightningServiceImpl;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        if ("github".equals(registrationId)) {
            GitHubUserInfo githubUser = new GitHubUserInfo(oAuth2User.getAttributes());

            Manager existing = managerRepository.findBySub(githubUser.getId());
            if (existing == null) {
                Manager manager = new Manager();
                manager.setSub(githubUser.getId());
                manager.setEmail(githubUser.getEmail());
                manager.setName(githubUser.getName());

                String newAddress = lightningServiceImpl.newAddress();
                managerRepository.save(manager);
                bitcoinAddressRepository.save(new BitcoinAddress(newAddress, manager));

                Invoice invoice = new Invoice("Welcome Funds","Bolt11",10000L,"Welcome to Lightning",1750403130339L,"paid",manager);
                invoiceRepository.save(invoice);
            }
        }

        return oAuth2User;
    }
}
