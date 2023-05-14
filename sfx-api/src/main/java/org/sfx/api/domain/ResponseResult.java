package org.sfx.api.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseResult <T>{
    int code;
    String msg;
    T data;

    public ResponseResult(int responseCode, String msg) {
        this.code = responseCode;
        this.msg = msg;
    }
}
