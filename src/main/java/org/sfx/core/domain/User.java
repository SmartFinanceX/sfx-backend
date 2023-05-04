package org.sfx.core.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {

    /**
     * 用户id
     */
    private Long id;
    /**
     *
     */
    private String userName;
    private String nickName;
    private String password;
    private String status;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机号
     */
    private String phonenumber;
}
