package com.fanyin.exception;

import com.fanyin.enums.ErrorCodeEnum;

/**
 * @author 二哥很猛
 * @date 2018/1/12 16:40
 */
public class BusinessException extends SystemException {

    private static final long serialVersionUID = 860110039545282195L;

    public BusinessException(ErrorCodeEnum error) {
        super(error);
    }
}
