package org.sfx.auth.mapper;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.sfx.auth.mapper.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class MapperTest {
    @Autowired
    MenuMapper menuMapper;

    @Test
    public void getMenu() {
        log.info(menuMapper.selectPermsByUserId(0L).toString());
    }
}
