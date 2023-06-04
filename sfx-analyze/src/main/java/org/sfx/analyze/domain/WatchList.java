package org.sfx.analyze.domain;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@TableName(value = "watchlist")
@AllArgsConstructor
public class WatchList {
    private String watchTicker;
    private Long userId;
}
