package org.sfx.core.controller;

import org.sfx.api.config.SfxResponseCode;
import org.sfx.core.domain.IncBasicInfo;
import org.sfx.core.domain.ResponseResult;
import org.sfx.core.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/search0")
@Deprecated
public class SearchController {

    @Autowired
    SearchService searchService;

    @GetMapping("/desc/{keyword}")
    ResponseResult SearchByDescription(@PathVariable String keyword) throws IOException {
        List<IncBasicInfo> description = searchService.searchHighlightByKeyWord("description", keyword);
        return new ResponseResult(SfxResponseCode.OK,"Find Results",description);
    }

    /**
     * 根据股票名称搜索
     * @param keyword
     * @return
     * @throws IOException
     */
    @GetMapping("/stock/{keyword}")
    ResponseResult SearchByStockName(@PathVariable String keyword) throws IOException {
        List<IncBasicInfo> description = searchService.searchHighlightByKeyWord("stockName", keyword);
        return new ResponseResult(SfxResponseCode.OK,"Find Results",description);
    }

    /**
     * 根据公司名称搜索
     * @param keyword
     * @return
     * @throws IOException
     */
    @GetMapping("/name/{keyword}")
    ResponseResult SearchByFullName(@PathVariable String keyword) throws IOException {
        List<IncBasicInfo> description = searchService.searchHighlightByKeyWord("fullName", keyword);
        return new ResponseResult(SfxResponseCode.OK,"Find Results",description);
    }

    /**
     * 根据行业搜索
     * @param keyword
     * @return
     * @throws IOException
     */
    @GetMapping("/class/{keyword}")
    ResponseResult SearchByClassName(@PathVariable String keyword) throws IOException {
        List<IncBasicInfo> description = searchService.searchHighlightByKeyWord("industryClass", keyword);
        return new ResponseResult(SfxResponseCode.OK,"Find Results",description);
    }

    /**
     *
     * TODO(AntiO2): 改变这里的Service实现。
     * 根据股票代码搜索
     * @param keyword
     * @return
     * @throws IOException
     */
    @GetMapping("/ticker/{keyword}")
    ResponseResult SearchByTickerName(@PathVariable String keyword) throws IOException {
        List<IncBasicInfo> description = searchService.searchHighlightByKeyWord("ticker", keyword);
        return new ResponseResult(SfxResponseCode.OK,"Find Results",description);
    }
}
