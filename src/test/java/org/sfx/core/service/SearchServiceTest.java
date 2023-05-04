package org.sfx.core.service;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.sfx.core.service.impl.SearchServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class SearchServiceTest {

    @Autowired
    SearchServiceImpl searchService;


    @Test
    public void FlushAllIndex() {
        searchService.flushAllIndex();
    }

    @Test
    public void SearchTerm() throws IOException {
        val ticker = searchService.searchHighlightByKeyWord("ticker", "002050");
        val incBasicInfos = searchService.searchHighlightByKeyWord("description", "汽车");
        Integer pause = 0;
    }
}
