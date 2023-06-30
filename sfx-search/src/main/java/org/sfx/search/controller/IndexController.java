package org.sfx.search.controller;

import org.sfx.api.config.SfxResponseCode;
import org.sfx.api.domain.ResponseResult;
import org.sfx.search.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/_search")
@RestController
public class IndexController {

    @Autowired
    IndexService indexService;
    @GetMapping("/_flush/all")
    ResponseResult flushAllBasicInfo() {
        Integer basicInfoCnt = indexService.flushAllBasicInfo();
        switch (basicInfoCnt) {
            case -1:
                return new ResponseResult<>(SfxResponseCode.INDEX_CREATE_ERROR,"新增索引失败");
            case -2:
                return new ResponseResult<>(SfxResponseCode.MYSQL_CREATE_ERROR,"数据库访问失败");
        }
        if(basicInfoCnt > 0) {
            return new ResponseResult<>(SfxResponseCode.OK,"Successfully create basic index", basicInfoCnt);
        }
        return new ResponseResult<>(SfxResponseCode.UNKNOWN_ERROR,"未知异常");
    }

}
