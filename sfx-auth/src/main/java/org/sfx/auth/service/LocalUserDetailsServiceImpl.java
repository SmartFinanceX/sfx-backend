package org.sfx.auth.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.sfx.auth.domain.SfxUser;
import org.sfx.auth.domain.SfxUserDetails;
import org.sfx.auth.mapper.MenuMapper;
import org.sfx.auth.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

// TODO(AntiO2) 添加feign方法获取用户信息。
@Service("localUser")
public class LocalUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    MenuMapper menuMapper;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        LambdaQueryWrapper<SfxUser> wrapper = new LambdaQueryWrapper<SfxUser>();
        wrapper.eq(SfxUser::getUserName,s);
        SfxUser user = userMapper.selectOne(wrapper);
        if(Objects.isNull(user)) {
            throw new RuntimeException("用户或密码错误");
        }
        List<String> permsByUserId = menuMapper.selectPermsByUserId(user.getId());
        return new SfxUserDetails(user,permsByUserId);
    }
}
