package org.sfx.analyze.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.sfx.analyze.domain.TradeRecord;

@Mapper
public interface TradeRecordMapper extends BaseMapper<TradeRecord> {
}
