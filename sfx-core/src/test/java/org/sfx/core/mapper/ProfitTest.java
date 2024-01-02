package org.sfx.core.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.junit.jupiter.api.Test;
import org.sfx.core.domain.Profit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProfitTest {
    @Autowired
    ProfitMapper mapper;

    @Test
    public void GetOneInc() {
        LambdaQueryWrapper<Profit> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Profit::getTicker, "000001");
        System.out.println(mapper.selectList(wrapper));
    }
}
