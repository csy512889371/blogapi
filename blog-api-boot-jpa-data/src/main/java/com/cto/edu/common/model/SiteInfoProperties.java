package com.cto.edu.common.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component("siteInfoProperties")
@ConfigurationProperties(prefix = "siteinfo")
public class SiteInfoProperties {

    private String siteName;

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }
}
