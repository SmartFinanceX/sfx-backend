package org.sfx.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.mybatis.spring.annotation.MapperScan;
import org.sfx.core.domain.FncReportInfo;

@Mapper
public interface FncMapper extends BaseMapper<FncReportInfo> {
}
