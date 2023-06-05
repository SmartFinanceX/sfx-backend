package org.sfx.search.service;

import org.sfx.api.domain.ResponseResult;

public interface SearchService {
    ResponseResult searchByTicker(String ticker);
    ResponseResult searchByKeyWord(String keyword);
    ResponseResult searchByKeyWord(String keyword,int pageNum, int pageSize);
}
