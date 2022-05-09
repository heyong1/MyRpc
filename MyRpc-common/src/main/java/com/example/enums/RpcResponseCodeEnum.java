package com.example.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Description
 *
 * @Author heyong
 * @Date 2022/5/9 19:57
 */
@AllArgsConstructor
@Getter
@ToString
public enum RpcResponseCodeEnum {

    SUCCESS(200, "The remote call is successful"),
    FAIL(500, "The remote call is fail");

    private final int code;
    private final String message;
}
