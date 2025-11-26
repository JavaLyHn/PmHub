package com.laigeoffer.pmhub.gateway.predicate;

import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.function.Predicate;

/*
spring:
  cloud:
    gateway:
      routes:
        - id: custom-route
          uri: http://example.com
          predicates:
            - name: MyCustom  # 自动匹配 MyCustomPredicateFactory
              args:
                headerName: X-Custom-Header    # 这些值会注入到Config对象中
                expectedValue: special-value
*/
@Component
public class MyCustomRoutePredicateFactory extends AbstractRoutePredicateFactory<MyCustomRoutePredicateFactory.Config> {
    public MyCustomRoutePredicateFactory() {
        super(MyCustomRoutePredicateFactory.Config.class);
    }

    @Override
    public Predicate<ServerWebExchange> apply(Config config) {
        return serverWebExchange -> {
            String headerValue = serverWebExchange.getRequest().getHeaders().getFirst(config.getHeaderName());
            return headerValue != null && headerValue.equalsIgnoreCase(config.getExpectedValue());
        };
    }


    public static class Config {
        private String headerName;
        private String expectedValue;
        public void setExpectedValue(String expectedValue) {
            this.expectedValue = expectedValue;
        }
        public String getExpectedValue() {
            return expectedValue;
        }
        public void setHeaderName(String headName) {
            this.headerName = headName;
        }

        public String getHeaderName() {
            return headerName;
        }
    }
}
