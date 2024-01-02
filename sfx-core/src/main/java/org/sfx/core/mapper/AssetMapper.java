package org.sfx.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.sfx.core.domain.Asset;

@Mapper
public interface AssetMapper extends BaseMapper<Asset> {
}
