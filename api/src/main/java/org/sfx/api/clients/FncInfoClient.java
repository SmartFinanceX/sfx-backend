package org.sfx.api.clients;

import org.sfx.api.domain.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("coreservice")
@RequestMapping("/fnc")
public interface FncInfoClient {
    @GetMapping("{ticker}/{category}")
    ResponseResult getFinanceReportInfo(@PathVariable String ticker, @PathVariable Short category);
}
