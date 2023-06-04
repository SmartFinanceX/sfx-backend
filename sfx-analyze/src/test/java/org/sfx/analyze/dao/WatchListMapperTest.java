package org.sfx.analyze.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WatchListMapperTest {
    @Autowired
    WatchListMapper watchListMapper;
    @Test
    public void MapperTest() {
        System.out.println(watchListMapper.selectList(null));
    }
}
