// Copyright (c) 2024 Chris Jackson
// Distributed under the MIT software license
package com.osys.wallet.model;

import java.util.Map;

public class GoogleUserInfo {
	
	private Map<String, Object> attributes;

    public GoogleUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public String getSub() {
        return (String) attributes.get("sub");
    }

    public String getName() {
        return (String) attributes.get("name");
    }

    public String getEmail() {
        return (String) attributes.get("email");
    }

}
