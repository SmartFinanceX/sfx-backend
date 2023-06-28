package org.sfx.auth.config;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
@Slf4j
public class ConfigTest {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Test
    public void getEncryptedPasswd() {
        String passwd="123456";
        log.info(passwordEncoder.encode(passwd));
        log.info(passwordEncoder.encode("secret"));
        log.info(String.valueOf(passwordEncoder.matches("secret","$2a$10$vBJI.qicDnLOigE4akfYf.fczKRO4DyxWqqY08A4L3huZWz2gqWrG")));
    }

}
