package org.sfx.core.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


@TableName("lr")
@Data
public class Profit {
    public String ticker;
    public String name;
    public String date;
    public String jlr;
    public String zsr;
    public String zzc;
    public String yyzc;
    public String xsfy;
    public String glfy;
    public String cwfy;
    public String yylr;
    public String lrze;
    public String yysrtb;
    public String jlrtb;
}
