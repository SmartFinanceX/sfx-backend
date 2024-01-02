package org.sfx.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.sfx.core.domain.Money;

@Mapper
public interface MoneyMapper extends BaseMapper<Money> {
}
