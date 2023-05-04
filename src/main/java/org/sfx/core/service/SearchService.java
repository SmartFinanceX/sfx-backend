package org.sfx.core.service;

import lombok.val;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.sfx.core.domain.IncBasicInfo;
import org.sfx.core.domain.ResponseResult;

public interface SearchService {
    ResponseResult flushAllIndex();


}
