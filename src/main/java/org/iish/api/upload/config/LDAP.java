package org.iish.api.upload.config;

import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Component
@ConfigurationProperties(prefix = "ldap")
public class LDAP {
    private @Valid @NotNull String url;
    private @Valid @NotNull String managerDn;
    private @Valid @NotNull String managerPassword;
    private @Valid @NotNull String searchBase;
    private @Valid @NotNull String searchFilter;
    private @Valid @NotNull Set<String> authorized;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getManagerDn() {
        return managerDn;
    }

    public void setManagerDn(String managerDn) {
        this.managerDn = managerDn;
    }

    public String getManagerPassword() {
        return managerPassword;
    }

    public void setManagerPassword(String managerPassword) {
        this.managerPassword = managerPassword;
    }

    public String getSearchBase() {
        return searchBase;
    }

    public void setSearchBase(String searchBase) {
        this.searchBase = searchBase;
    }

    public String getSearchFilter() {
        return searchFilter;
    }

    public void setSearchFilter(String searchFilter) {
        this.searchFilter = searchFilter;
    }

    public Set<String> getAuthorized() {
        return authorized;
    }

    public void setAuthorized(Set<String> authorized) {
        this.authorized = authorized;
    }
}
