package com.osys.wallet.model;

import java.util.Map;

public class GitHubUserInfo {
    private Map<String, Object> attributes;

    public GitHubUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public String getId() {
        return String.valueOf(attributes.get("id"));
    }

    public String getName() {
        return (String) attributes.get("name");
    }

    public String getEmail() {
        return (String) attributes.get("email");
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }
}
