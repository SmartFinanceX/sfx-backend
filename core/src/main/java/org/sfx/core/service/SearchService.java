package org.sfx.core.service;

import lombok.val;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.sfx.core.domain.IncBasicInfo;
import org.sfx.core.domain.ResponseResult;

import java.io.IOException;
import java.util.List;

/**
 *
 * @see <a href="https://sfx.antio2.cn/API/1.html">检索接口</a>
 */
@Deprecated
public interface SearchService {
    ResponseResult flushAllIndex();

    public List<IncBasicInfo> searchHighlightByKeyWord(String filedName, String KeyWord) throws IOException;
}
