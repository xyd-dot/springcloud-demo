package com.cloud.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;


@ConfigurationProperties(prefix = "jwt.auth")
@Component
public class AuthProperties {
    private List<String> includePaths;
    private List<String> excludePaths;

    public List<String> getIncludePaths() {
        return includePaths;
    }

    public List<String> getExcludePaths() {
        return excludePaths;
    }

    public void setIncludePaths(List<String> includePaths) {
        this.includePaths = includePaths;
    }

    public void setExcludePaths(List<String> excludePaths) {
        this.excludePaths = excludePaths;
    }
}
