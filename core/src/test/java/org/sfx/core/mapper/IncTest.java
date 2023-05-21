package org.sfx.core.mapper;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class IncTest {
    @Autowired
    IncBasicMapper incBasicMapper;

    @Test
    public void GetAllInfo() {
        System.out.println(JSON.toJSONString( incBasicMapper.selectList(null)));
    }


}
