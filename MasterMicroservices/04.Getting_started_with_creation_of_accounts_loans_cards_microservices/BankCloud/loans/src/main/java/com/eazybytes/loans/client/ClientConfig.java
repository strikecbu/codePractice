package com.eazybytes.loans.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactivefeign.cloud2.CloudReactiveFeign;
import reactivefeign.webclient.WebReactiveFeign;

@Configuration
public class ClientConfig {

    /**
     * 不依靠Eureka server時，自行指定url
     */
//    @Bean
//    public AccountClient accountClient() {
//        return WebReactiveFeign  //WebClient based reactive feign
//                //JettyReactiveFeign //Jetty http client based
//                //Java11ReactiveFeign //Java 11 http client based
//                .<AccountClient>builder()
//                .target(AccountClient.class, "http://host:port");
//    }

}
