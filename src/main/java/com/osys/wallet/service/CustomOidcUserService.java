// Distributed under the MIT software license
package com.osys.wallet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import com.osys.wallet.model.BitcoinAddress;
import com.osys.wallet.model.GoogleUserInfo;
import com.osys.wallet.model.Manager;
import com.osys.wallet.repository.BitcoinAddressRepository;
import com.osys.wallet.repository.ManagerRepository;


@Service
public class CustomOidcUserService extends OidcUserService {

    @Autowired
    private ManagerRepository managerRepository; 

    @Autowired
    private BitcoinAddressRepository bitcoinAddressRepository; 

    @Autowired
    private LightningServiceImpl lightningServiceImpl;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);

        try {
             return processOidcUser(userRequest, oidcUser);
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

     private OidcUser processOidcUser(OidcUserRequest userRequest, OidcUser oidcUser) {
        GoogleUserInfo googleUserInfo = new GoogleUserInfo(oidcUser.getAttributes());

        Manager managerOptional = managerRepository.findBySub(googleUserInfo.getSub());
        if (managerOptional == null) {
            Manager manager = new Manager();
            manager.setSub(googleUserInfo.getSub());
            manager.setEmail(googleUserInfo.getEmail());
            manager.setName(googleUserInfo.getName());

            String newAddress = lightningServiceImpl.newAddress();
            managerRepository.save(manager);
            BitcoinAddress bitcoinAddress = new BitcoinAddress(newAddress,manager);
            bitcoinAddressRepository.save(bitcoinAddress);
        }   

        return oidcUser;
    }
}