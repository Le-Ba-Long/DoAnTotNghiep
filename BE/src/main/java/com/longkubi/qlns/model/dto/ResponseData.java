package com.longkubi.qlns.model.dto;

import com.longkubi.qlns.common.ErrorMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.longkubi.qlns.common.ErrorMessage.SUCCESS;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseData<T> {
    private Integer statusCode;
    private  String message;
    private T data;

    public ResponseData(T data) {
        this.data = data;
        this.statusCode = SUCCESS.getCode();
        this.message = SUCCESS.getMessage();
    }


    public ResponseData(ErrorMessage errorMessage, T data) {
        this.data = data;
        this.statusCode = errorMessage.getCode();
        this.message = errorMessage.getMessage();
    }

}
