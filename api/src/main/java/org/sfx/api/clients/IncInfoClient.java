package org.sfx.api.clients;

import org.sfx.api.domain.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("coreservice")
@RequestMapping("/inc")
public interface IncInfoClient {
    @GetMapping("/")
    public ResponseResult getAllInc();

    @GetMapping("/_ticker/{ticker}")
    public ResponseResult getByTicker(@PathVariable String ticker);
}
