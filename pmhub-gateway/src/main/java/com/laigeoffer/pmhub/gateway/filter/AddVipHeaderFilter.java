package com.laigeoffer.pmhub.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
@Component
public class AddVipHeaderFilter extends AbstractGatewayFilterFactory<AddVipHeaderFilter.Config>{
    public AddVipHeaderFilter() {
        super(AddVipHeaderFilter.Config.class);//置顶配置类
    }

    @Override
    public GatewayFilter apply(Config config) {
        //返回过滤器逻辑
        return (exchange, chain) -> {
            //在这里修改请求
            ServerHttpRequest request = exchange.getRequest()
                    .mutate()
                    .header("X-VIP-Customer", config.getVipLevel())
                    .build();
            System.out.println("VIP请求");
            return chain.filter(exchange.mutate().request(request).build());
        };
    }

    //配置类 用于传递自定义参数
    public static class Config{

        private String vipLevel = "Gold"; //默认是金牌vip

        public void setVipLevel(String vipLevel) {
            this.vipLevel = vipLevel;
        }

        public String getVipLevel() {
            return vipLevel;
        }
    }
}
