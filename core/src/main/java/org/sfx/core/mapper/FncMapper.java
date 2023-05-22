package org.sfx.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.sfx.api.domain.FncReportInfo;

@Mapper
public interface FncMapper extends BaseMapper<FncReportInfo> {
}
