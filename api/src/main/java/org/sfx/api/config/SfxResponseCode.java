package org.sfx.api.config;


public class SfxResponseCode {
    public static int OK=200;
    public static int UNKNOWN_ERROR=500;

    /**
     * 没有对应的搜索字段
     */
    public static int NO_SUCH_FIELD = 4101;
    /**
     * 新增索引文档失败
     *
     */
    public static int INDEX_CREATE_ERROR=4201;
    /**
     * 索引库中无对应结果
     *
     */
    public static int INDEX_NOT_FIND=4202;
    /**
     * Mysql数据库访问失败
     *
     */
    public static int MYSQL_CREATE_ERROR=4301;
}
