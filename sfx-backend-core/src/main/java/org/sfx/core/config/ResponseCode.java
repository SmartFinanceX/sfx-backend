package org.sfx.core.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


public class  ResponseCode {
    public static int OK=200;
    public static int UNKNOWN_ERROR=500;

    /**
     * 没有对应的搜索字段
     */
    public static int NO_SUCH_FIELD = 4101;
}
