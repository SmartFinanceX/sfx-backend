package org.sfx.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.sfx.auth.domain.SfxUser;
@Mapper
public interface UserMapper extends BaseMapper<SfxUser> {
}
