package org.sfx.core.service;

import org.sfx.core.domain.ResponseResult;

public interface FinaInfoService {

    ResponseResult getFinanceReportInfo(String ticker, Short category);
}
