package org.sfx.core.service.impl;


import lombok.val;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.*;
import org.sfx.core.config.ResponseCode;
import org.sfx.core.domain.IncBasicInfo;
import org.sfx.core.domain.ResponseResult;
import org.sfx.core.mapper.IncBasicMapper;
import org.sfx.core.util.SearchUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

// @Service
@Deprecated
public class SearchServiceImpl implements org.sfx.core.service.SearchService {


    @Autowired
    IncBasicMapper incBasicMapper;
    @Autowired
    SearchUtils searchUtils;
    @Autowired
    Analyzer analyzer;
    @Override
    public ResponseResult flushAllIndex() {
        IndexWriter indexWriter = searchUtils.getIndexWriter();
        try {
            indexWriter.deleteAll();
            val incBasicInfos = incBasicMapper.selectList(null);
            for(IncBasicInfo incBasicInfo:incBasicInfos) {
                indexWriter.addDocument(generateOneDocByIncBasicInfo(incBasicInfo));
            }
            indexWriter.commit();
            indexWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new ResponseResult(ResponseCode.OK,"刷新索引成功");
    }

    @Autowired
    @Qualifier("ContentType")
    FieldType contentField;

    /**
     * 根据公司信息生成一个Document
     * @param incBasicInfo
     * @return
     */
    private Document generateOneDocByIncBasicInfo(IncBasicInfo incBasicInfo) {
        Document document = new Document();
        document.add(new StringField("ticker", incBasicInfo.getTicker(), Field.Store.YES)); // 索引 不分词
        document.add(new Field("stockName",incBasicInfo.getStockName(),contentField));
        document.add(new Field("fullName",incBasicInfo.getFullName(),contentField));
        document.add(new Field("industryClass",incBasicInfo.getIndustryClass(),contentField));
        document.add(new Field("description",incBasicInfo.getDescription(),contentField));
        return document;
    }

    /**
     *
     * @param filedName ticker|stockName|fullName|class|description
     * @param KeyWord
     * @return
     * @throws IOException
     */
    public List<IncBasicInfo> searchHighlightByKeyWord(String filedName, String KeyWord) throws IOException {
        ArrayList<IncBasicInfo> incBasicInfos = new ArrayList<>();
        val indexSearcher = searchUtils.getIndexSearcher();
        TermQuery termQuery = new TermQuery(new Term(filedName, KeyWord));

        // 设置高亮查询
        QueryScorer score = new QueryScorer(termQuery);
        Highlighter highlighter = new Highlighter(SearchUtils.getSimpleHTMLFormatter(), score);
        SimpleSpanFragmenter simpleSpanFragmenter = new SimpleSpanFragmenter(score);
        highlighter.setTextFragmenter(simpleSpanFragmenter);
        //

        TopDocs searchResults = indexSearcher.search(termQuery, 10);
        for(ScoreDoc scoreDoc:searchResults.scoreDocs) {
            Document doc = indexSearcher.doc(scoreDoc.doc);
            IncBasicInfo basicInfo = incBasicMapper.selectById(doc.get("ticker"));
            if(filedName.equals("ticker")) {
                incBasicInfos.add(basicInfo);
                continue;
            }
            switch (filedName) {
                case "stockName":
                case "fullName":
                case "industryClass":
                case "description": {


                    try {
                        // val methods = IncBasicInfo.class.getMethods();
                        String fileNameUpper = filedName.substring(0, 1).toUpperCase() + filedName.substring(1);
                        Method setMethod = IncBasicInfo.class.getMethod("set" + fileNameUpper,String.class);
                        Method getMethod = IncBasicInfo.class.getMethod("get" + fileNameUpper);
                        String text = (String)getMethod.invoke(basicInfo);
                        String highLightString = getHighLightString(highlighter, indexSearcher, scoreDoc.doc, filedName,text);
                        setMethod.invoke(basicInfo,highLightString);
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                    incBasicInfos.add(basicInfo);
                    break;
                }
                default:
                {
                    return null;
                }
            }
        }
        return incBasicInfos;
    }
    private String getHighLightString(Highlighter highlighter,IndexSearcher indexSearcher,int doc,String filedName,String text) {
        try {
            TokenStream tokenStream = TokenSources.getAnyTokenStream(indexSearcher.getIndexReader(),doc,filedName,analyzer);
            return highlighter.getBestFragment(tokenStream,text);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InvalidTokenOffsetsException e) {
            throw new RuntimeException(e);
        }
    }
}
