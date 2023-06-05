package org.sfx.core.service;

import com.alibaba.fastjson.JSON;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class IncServiceTest {
    @Autowired
    IncBasicService incBasicService;

    @Test
    public void selectAll() {
        System.out.println(JSON.toJSON(incBasicService.selectAll()) );
    }

    @Test
    public void GetByTicker() {
        val responseResult = incBasicService.selectByTicker("002035");
        System.out.println(responseResult);
    }
}
