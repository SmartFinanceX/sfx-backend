package org.sfx.search.service;

import org.sfx.api.domain.ResponseResult;

public interface SearchService {
    /**
     * 通过股票代码精确查询
     *
     * @param ticker 股票代码
     * @return
     */
    ResponseResult searchByTicker(String ticker);

    /**
     * 通过关键词查询
     *
     * @param keyword
     * @return
     */
    ResponseResult searchByKeyWord(String keyword);

    /**
     * 通过关键词查询 分页
     *
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    ResponseResult searchByKeyWord(String keyword, int pageNum, int pageSize);
}
