package org.sfx.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.sfx.api.config.SfxResponseCode;
import org.sfx.core.domain.FncReportInfo;
import org.sfx.core.domain.ResponseResult;
import org.sfx.core.mapper.FncMapper;
import org.sfx.core.service.FinaInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class FinaInfoServiceImpl implements FinaInfoService {

    @Autowired
    FncMapper fncMapper;
    @Override
    public ResponseResult getFinanceReportInfo(String ticker, Short category) {
        LambdaQueryWrapper<FncReportInfo> fncReportInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        fncReportInfoLambdaQueryWrapper.eq(FncReportInfo::getTicker,ticker).eq(FncReportInfo::getCategory,category);
        try {
            List<FncReportInfo> fncReportInfos = fncMapper.selectList(fncReportInfoLambdaQueryWrapper);
            if(fncReportInfos.isEmpty()) {
                return new ResponseResult<>(SfxResponseCode.INDEX_NOT_FIND,"无对应数据");
            } else {
                return new ResponseResult<>(SfxResponseCode.OK,"找到对应结果",fncReportInfos.get(0));
            }
        } catch (Exception e) {
            return new ResponseResult<>(SfxResponseCode.MYSQL_SEARCH_ERROR,"访问数据库失败");
        }
    }
}
