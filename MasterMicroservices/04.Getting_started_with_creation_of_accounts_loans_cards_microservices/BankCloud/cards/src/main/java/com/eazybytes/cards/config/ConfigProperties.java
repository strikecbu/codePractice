package com.eazybytes.cards.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "cards")
@Data
public class ConfigProperties implements Serializable {

    private String msg;

    private String buildVersion;

    private Map<String, String> mailDetails;

    private List<String> activeBranches;
}
