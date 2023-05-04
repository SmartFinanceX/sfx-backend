package org.sfx.core.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 *
 * @see  <a href="https://sfx.antio2.cn/Script/SQL.html#基础信息">公司基础信息</a>
 */
@TableName("inc_basic_info")
public class IncBasicInfo {
    @TableId
    String ticker;
    String stockName;
    String fullName;
    /**
     * 上市日期
     */
    Date listDate;
    /**
     * 业务类别
     *
     */
    String industryClass;
    /**
     * 业务描述
     *
     */
    String description;

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getListDate() {
        return listDate;
    }

    public void setListDate(Date listDate) {
        this.listDate = listDate;
    }

    public String getIndustryClass() {
        return industryClass;
    }

    public void setIndustryClass(String industryClass) {
        this.industryClass = industryClass;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
