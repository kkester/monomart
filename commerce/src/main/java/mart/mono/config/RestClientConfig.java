package mart.mono.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {
    @Bean
    public RestClient restClient(ProductApiProperties productApiProperties) {
        return RestClient.builder()
                .baseUrl(productApiProperties.getUrl())
                .build();
    }
}
