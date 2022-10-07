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

import com.alibaba.fastjson.JSONObject;
import com.sotatek.gateway.model.User;
import com.sotatek.gateway.util.GatewayConst;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class AuthenUserFilter extends AbstractGatewayFilterFactory<AuthenUserFilter.Config> implements Serializable{
	private final java.util.List<HttpMessageReader<?>> messageReaders;
	
	@Autowired
    private RedisTemplate<Object, Object> redisTemplate;
	
	public AuthenUserFilter() {
        super(Config.class);
        this.messageReaders = HandlerStrategies.withDefaults().messageReaders();
    }
	
	public GatewayFilter apply(final Config config) {
        return (exchange, chain) -> {
        	log.info("AuthenFilter");
        	ServerHttpRequest request = exchange.getRequest();
        	String token = request.getHeaders().getFirst("Authorization");
        	if(StringUtils.isEmpty(token)) {
        		ServerWebExchangeUtils.setResponseStatus(exchange, HttpStatus.UNAUTHORIZED);
                ServerWebExchangeUtils.setAlreadyRouted(exchange);
                return exchange.getResponse().setComplete();
        	}
        	token = token.replace("Bearer ", "");
        	if(token.equals(GatewayConst.TOKEN_ADMIN)) {
        		
        	} else {
        		Object userObject = redisTemplate.opsForValue().get(token);
        		if(userObject == null) {
        			ServerWebExchangeUtils.setResponseStatus(exchange, HttpStatus.NO_CONTENT);
                    ServerWebExchangeUtils.setAlreadyRouted(exchange);
                    return exchange.getResponse().setComplete();
        		}
        		User user = JSONObject.parseObject(userObject.toString(), User.class);
        		exchange.getAttributes().put(GatewayConst.IS_ADMIN, false);
        		String serviceId = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_PREDICATE_MATCHED_PATH_ROUTE_ID_ATTR).toString();
        		// TODO authen service
        		
        		exchange.getAttributes().put(GatewayConst.USER_ID, user.userId);
        	}
        	
        	return chain.filter(exchange);
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
