package org.sfx.gateway.filter;

import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.jetbrains.annotations.NotNull;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@Slf4j
public class CheckTokenFilter implements WebFilter,Ordered {

    private Mono<Void> buildReturnMono(String error, ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        String jsonString = JSON.toJSONString(error);
        byte[] bits = jsonString.getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bits);
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        return response.writeWith(Mono.just(buffer));
    }

    /**
     * Get the order value of this object.
     * <p>Higher values are interpreted as lower priority. As a consequence,
     * the object with the lowest value has the highest priority (somewhat
     * analogous to Servlet {@code load-on-startup} values).
     * <p>Same order values will result in arbitrary sort positions for the
     * affected objects.
     *
     * @return the order value
     * @see #HIGHEST_PRECEDENCE
     * @see #LOWEST_PRECEDENCE
     */
    @Override
    public int getOrder() {
        return -1999;
    }

    /**
     * Process the Web request and (optionally) delegate to the next
     * {@code WebFilter} through the given {@link WebFilterChain}.
     *
     * @param exchange the current server exchange
     * @param chain    provides a way to delegate to the next filter
     * @return {@code Mono<Void>} to indicate when request processing is complete
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        log.info("Token Solve");
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");

        if(token==null) {
            log.info("Token is null");
            return chain.filter(exchange);
        }
        // log.info(token);
        try {
            Claims claimsJws = Jwts.parser()
                    .setSigningKey("AntiO2".getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(token)
                    .getBody();
            List<String> authorities = (List<String>) claimsJws.get("authorities");

            String userName = (String)claimsJws.get("user_name");
            log.info(userName+" "+authorities.toString());
            // TODO(AntiO2) 这里的异常处理！
            SecurityContextHolder.getContext().setAuthentication(
                    new AnonymousAuthenticationToken("jwt",userName,
                            authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())));

            return chain.filter(exchange);
        } catch (Exception | Error e) {
            log.error(e.toString());
            throw new AuthenticationServiceException("鉴权失败");
        }
    }
}
