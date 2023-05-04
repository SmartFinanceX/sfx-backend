package org.sfx.core.util;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.*;
import org.apache.lucene.luke.app.desktop.LukeMain;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;







@Component
public class SearchUtils {

    @Value("${lucene.index-path}")
    String defaultPath;
    /**
     * 分词器
     * @return
     */
    @Bean
    Analyzer getAnalyzer() {
        return new IKAnalyzer();
    }

    /**
     * IndexWriterConfig不能共享，故这里不作为bean<br>
     * 返回一个IndexWriterConfig
     * @param analyzer 分词器
     * @return
     */
    public static IndexWriterConfig getindexWriterCfg(Analyzer analyzer) {
        return new IndexWriterConfig(analyzer);
    }

    /**
     * 获取写索引工具
     * @param path 索引文件夹
     * @param iwc IndexWeiterConfig 配置
     * @return
     */
    public static IndexWriter getIndexWriter(String path, IndexWriterConfig iwc) {
        Path indexPath = Paths.get(path);
        File file = new File(path);
        if(!file.exists()) {
            boolean mkdirs = file.mkdirs();
        }
        if(!file.canRead()) {
            throw new RuntimeException("Can't open filepath " + path);
        }
        Directory directory = null;
        try {
            directory = FSDirectory.open(indexPath);
            return new IndexWriter(directory,iwc);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 通过path获取Writer，使用默认的IK分词器
     * @param path 索引路径
     * @return
     */
    public static IndexWriter getIndexWriter(String path) {
        IndexWriterConfig iwc = getindexWriterCfg(ikAnalyzer);
        return getIndexWriter(path,iwc);
    }

    /**
     * 通过默认路径获取IndexWriter
     * @return
     */
    public IndexWriter getIndexWriter(){
        return getIndexWriter(this.defaultPath);
    }
    private static final IKAnalyzer ikAnalyzer ;
    static {
        ikAnalyzer = new IKAnalyzer();
    }

    /**
     * 通过默认路径获取indexSearcher
     * @return
     * @throws IOException
     */
    public IndexSearcher getIndexSearcher() throws IOException {
         return new IndexSearcher(DirectoryReader.open(FSDirectory.open(Path.of(this.defaultPath))));
    }
    /**
     * 索引类型，不存储、分词并保存词向量
     * @return
     */
    @Bean("ContentType")
    public static FieldType getContentType() {
        FieldType fieldType = new FieldType();
        /**
         * 存储 (为了高亮显示)
         *
         */
        fieldType.setStored(true);
        /**
         * 索引
         * 保存词向量
         */
        fieldType.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);
        /**
         * 设置类型
         *
         */
        // fieldType.setDocValuesType(DocValuesType.BINARY);

        /**
         * 分词
         *
         */
        fieldType.setTokenized(true);
        return fieldType;
    }

    /**
     * 获取高亮器
     * @return SimpleHTMLFormatter
     */
    @Bean
    public static SimpleHTMLFormatter getSimpleHTMLFormatter() {
        return new SimpleHTMLFormatter("<span class=\"highlight_sfx\">","</span>");
    }
    /**
     * 开启luke
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        String[] params = {"--open"};
        LukeMain.main(params);
    }
}
