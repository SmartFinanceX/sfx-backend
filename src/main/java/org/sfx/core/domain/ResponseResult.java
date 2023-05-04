package org.sfx.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.sfx.core.config.ResponseCode;

@Data
@AllArgsConstructor
public class ResponseResult <T>{
    int code;
    String msg;
    T data;

    public ResponseResult(int responseCode, String msg) {
        this.code = responseCode;
        this.msg = msg;
    }
}
