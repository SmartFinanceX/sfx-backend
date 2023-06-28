package org.sfx.gateway.config;

import com.alibaba.nacos.common.utils.HttpMethod;
import org.sfx.gateway.filter.CheckTokenFilter;
import org.sfx.gateway.filter.RequestLogFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AnonymousAuthenticationProvider;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AnonymousAuthenticationWebFilter;

import javax.crypto.spec.SecretKeySpec;

@Configuration
public class SecureConfig {


    @Autowired
    RequestLogFilter requestLogFilter;
    @Autowired
    CheckTokenFilter checkTokenFilter;
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange(exchange -> exchange
                        .pathMatchers("/uaa/**").permitAll()
                        .pathMatchers("/analyze/**").permitAll()// 通过自定义的（JWT）方式 来实现所有认证
                        .pathMatchers(HttpMethod.OPTIONS).permitAll()
                                .anyExchange().permitAll()
                        // .anyExchange().access(defaultAuthorizationManager)
                );
        http.csrf().disable();
        http.addFilterBefore(requestLogFilter, SecurityWebFiltersOrder.AUTHENTICATION)
               //  .addFilterBefore(checkTokenFilter,SecurityWebFiltersOrder.FIRST);
               // .pathMatchers("/api/user/**").hasAuthority("ROLE_USER")
               // .pathMatchers("/api/admin/**").hasAuthority("ROLE_ADMIN")
        ;

        return http.build();
    }
}
