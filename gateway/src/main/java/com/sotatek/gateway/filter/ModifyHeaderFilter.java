package com.sotatek.gateway.filter;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.server.ServerWebExchange;

import com.alibaba.fastjson.JSONObject;
import com.sotatek.gateway.model.User;
import com.sotatek.gateway.util.GatewayConst;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class ModifyHeaderFilter extends AbstractGatewayFilterFactory<ModifyHeaderFilter.Config> implements Serializable{
	private final java.util.List<HttpMessageReader<?>> messageReaders;
	
	@Autowired
    private RedisTemplate<Object, Object> redisTemplate;
	
	public ModifyHeaderFilter() {
        super(Config.class);
        this.messageReaders = HandlerStrategies.withDefaults().messageReaders();
    }
	
	public Boolean should(ServerWebExchange exchange) {
        if(exchange.getAttribute(GatewayConst.IS_ADMIN) == null) {
            return false;
        }
        return true;
    }
	
	public GatewayFilter apply(final Config config) {
        return (exchange, chain) -> {
        	if(!should(exchange)) {
                return chain.filter(exchange);
            }
        	log.info("ModifyHeaderFilter");
        	ServerHttpRequest request = exchange.getRequest()
            .mutate()
            .header(GatewayConst.USER_ID_OBJECT, exchange.getAttribute(GatewayConst.USER_ID).toString())
            .build();
        	
        	return chain.filter(exchange.mutate().request(request).build());
        };
    }
	
	public static class Config {
        private Class<?> bodyClass;

        public Class<?> getBodyClass() {
            return bodyClass;
        }

        public void setBodyClass(Class<?> bodyClass) {
            this.bodyClass = bodyClass;
        }
    }
}
