package org.sfx.core.domain;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;


@Data
@TableName(value = "inc_finc_info", autoResultMap=true)
public class FncReportInfo {
    String ticker;
    Short category;
    @TableField(value = "finanace_data")
    String finanace_data;
}