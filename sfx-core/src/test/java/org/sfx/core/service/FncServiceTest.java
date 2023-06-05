package org.sfx.core.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FncServiceTest {
    @Autowired
    FinaInfoService finaInfoService;
    @Test
    public void getFnc() {
        System.out.println(finaInfoService.getFinanceReportInfo("000001", (short) 2));
        System.out.println(finaInfoService.getFinanceReportInfo("000001", (short) 7 ));
    }
}
