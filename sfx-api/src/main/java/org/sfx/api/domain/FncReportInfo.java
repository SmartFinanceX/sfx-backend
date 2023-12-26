package org.sfx.api.domain;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;


@Data
@TableName(value = "inc_finc_info", autoResultMap = true)
public class FncReportInfo {
    String ticker;
    Short category;
    @TableField(value = "finanace_data", typeHandler = JacksonTypeHandler.class)
    JSONArray finanace_data;

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public Short getCategory() {
        return category;
    }

    public void setCategory(Short category) {
        this.category = category;
    }

    public JSONArray getFinanace_data() {
        return finanace_data;
    }

    public void setFinanace_data(JSONArray finanace_data) {
        this.finanace_data = finanace_data;
    }
}