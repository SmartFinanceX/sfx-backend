package org.sfx.core.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("money")
@Data
public class Money {
    public String ticker;
    public String a;
    public String b;
    public String c;
    public String d;
    public String e;
    public String f;
    public String g;
    public String h;
}
