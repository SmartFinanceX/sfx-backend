package org.sfx.core.service;

import org.sfx.core.domain.ResponseResult;

public interface IncBasicService {
    ResponseResult selectAll();

    ResponseResult selectByTicker(String ticker);
}
