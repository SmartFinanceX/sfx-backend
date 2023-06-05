package org.sfx.auth.domain;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SfxUserDetails implements UserDetails {

    private SfxUser sfxUser;

    private List<String> permissions; // 字符串形式的权限信息

    @JSONField(serialize = false)
    private List<GrantedAuthority> authorities;

    public SfxUserDetails(SfxUser sfxUser,List<String> permissions) {
        this.sfxUser = sfxUser;
        this.permissions = permissions;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(authorities!=null) {
            return authorities;
        }
        authorities  = permissions.stream().
                map(SimpleGrantedAuthority::new).
                collect(Collectors.toList());
        return authorities;
    }

    @Override
    public String getPassword() {
        return sfxUser.getPassword();
    }

    @Override
    public String getUsername() {
        return sfxUser.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return sfxUser.getStatus().equals("0");
    }
}
