package guru.springframework.msscbreweryclient.web.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.nio.reactor.IOReactorException;
import org.apache.http.nio.reactor.IOReactorExceptionHandler;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsAsyncClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;

//@Component
public class ApacheAsyncRestTemplateConfig implements RestTemplateCustomizer {


    @Override
    public void customize(RestTemplate restTemplate) {
        restTemplate.setRequestFactory(this.getFactory());
    }

    private ClientHttpRequestFactory getFactory() {

        try {
            DefaultConnectingIOReactor defaultConnectingIOReactor = new DefaultConnectingIOReactor();
            PoolingNHttpClientConnectionManager manager = new PoolingNHttpClientConnectionManager(
                    defaultConnectingIOReactor);
            manager.setMaxTotal(100);
            manager.setDefaultMaxPerRoute(10);

            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(30000)
                    .setSocketTimeout(20000)
                    .build();

            CloseableHttpAsyncClient client = HttpAsyncClients.custom()
                    .setConnectionManager(manager)
                    .setDefaultRequestConfig(requestConfig)
                    .setKeepAliveStrategy(DefaultConnectionKeepAliveStrategy.INSTANCE)
                    .build();

            return new HttpComponentsAsyncClientHttpRequestFactory(client);

        } catch (IOReactorException e) {
            throw new RuntimeException(e);
        }
    }
}
