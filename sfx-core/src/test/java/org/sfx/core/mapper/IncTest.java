package org.sfx.core.mapper;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.junit.jupiter.api.Test;
import org.sfx.core.domain.IncBasicInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class


IncTest {
    @Autowired
    IncBasicMapper incBasicMapper;

    @Test
    public void GetAllInfo() {
        System.out.println(JSON.toJSONString(incBasicMapper.selectList(null)));
    }

    @Test
    public void GetOneInc() {
        LambdaQueryWrapper<IncBasicInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(IncBasicInfo::getTicker, "000001");
        System.out.println(JSON.toJSONString(incBasicMapper.selectList(wrapper)));
    }

}
