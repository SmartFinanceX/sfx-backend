package org.sfx.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.val;
import org.sfx.core.config.ResponseCode;
import org.sfx.core.domain.IncBasicInfo;
import org.sfx.core.domain.ResponseResult;
import org.sfx.core.mapper.IncBasicMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class IncBasicServiceImpl implements org.sfx.core.service.IncBasicService {
    @Autowired
    IncBasicMapper incBasicMapper;

    /**
     * 查询所有公司信息
     * @return
     */
    @Override
    public ResponseResult selectAll() {
        List<IncBasicInfo> incBasicInfos = incBasicMapper.selectList(new QueryWrapper<>());
        int count = incBasicInfos.size();

        return new ResponseResult(ResponseCode.OK, Integer.toString(count),incBasicInfos);
    }
    /**
     * 根据股票代码查询
     * @param ticker
     * @return
     */
    @Override
    public ResponseResult selectByTicker(String ticker) {
        LambdaQueryWrapper<IncBasicInfo> incBasicInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        incBasicInfoLambdaQueryWrapper.eq(IncBasicInfo::getTicker,ticker);
        IncBasicInfo incBasicInfo = incBasicMapper.selectOne(incBasicInfoLambdaQueryWrapper);
        if(Objects.isNull(incBasicInfo)) {
            return new ResponseResult(ResponseCode.OK,"未找到对应股票代码",null);
        }
        return new ResponseResult(ResponseCode.OK,"找到对应股票",incBasicInfo);
    }


}
