package org.sfx.analyze.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AnalyzeData {
    @Autowired
    AnalyzeService service;

    @Test
    public void GetData1() {
        System.out.println(service.GetData1("000001"));
    }
}
