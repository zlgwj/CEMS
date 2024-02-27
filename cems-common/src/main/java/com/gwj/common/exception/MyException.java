package com.gwj.common.exception;

import com.gwj.common.enums.ResultEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data//生成set、get方法
@AllArgsConstructor//生成有参构造
@NoArgsConstructor//生成无参构造
public class MyException extends RuntimeException {
    private Integer code;
    private String msg;

    public MyException(ResultEnum resultEnum) {
        throw new MyException(resultEnum.getCode(), resultEnum.getMsg());
    }

    public MyException(String msg) {
        throw new MyException(ResultEnum.ERROR.getCode(), msg);
    }

    public MyException(ResultEnum resultEnum, String msg) {
        throw new MyException(resultEnum.getCode(), msg);
    }
}
