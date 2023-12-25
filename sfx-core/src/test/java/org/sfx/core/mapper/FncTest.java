package org.sfx.core.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.sfx.api.domain.FncReportInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FncTest {
    @Autowired
    FncMapper fncMapper;

    @Test
    public void FncInfoTest() {
        LambdaQueryWrapper<FncReportInfo> fncReportInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        fncReportInfoLambdaQueryWrapper.eq(FncReportInfo::getTicker, "000001").eq(FncReportInfo::getCategory, 1);
        System.out.println(fncMapper.selectList(fncReportInfoLambdaQueryWrapper));
    }

    @Test
    public void FncInfoTest2() {
        QueryWrapper<FncReportInfo> fncReportInfoLambdaQueryWrapper = new QueryWrapper<>();
        fncReportInfoLambdaQueryWrapper.eq("ticker", "1").eq("category", 1);
        System.out.println(fncMapper.selectList(fncReportInfoLambdaQueryWrapper));
    }

    @Test
    public void FncInfoTest3() {
    }
}
