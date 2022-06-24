package com.eazybytes.accounts.controller;

import com.eazybytes.accounts.config.ConfigProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Properties;

@RestController
public class ConfigController {

    private final ConfigProperties configProperties;

    public ConfigController(ConfigProperties configProperties) {
        this.configProperties = configProperties;
    }

    @GetMapping("/configs")
    public Properties getConfigs() {
        Properties properties = new Properties();
        properties.put("msg", configProperties.getMsg());
        properties.put("buildVersion", configProperties.getBuildVersion());
        properties.put("mailDetails", configProperties.getMailDetails());
        properties.put("activeBranches", configProperties.getActiveBranches());
        return properties;
    }
}
