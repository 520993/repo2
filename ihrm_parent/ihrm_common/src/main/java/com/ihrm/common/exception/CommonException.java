package com.ihrm.common.exception;

import com.ihrm.common.entity.ResultCode;

/**
 * @author xf
 * @create 2020-03-04-14:04
 */
public class CommonException extends Exception {
    private ResultCode resultCode;
    public CommonException(ResultCode resultCode){
        this.resultCode = resultCode;
    }
}
