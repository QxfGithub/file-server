package com.qxf.fileserver.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by nieyunfei184 on 17/6/2.
 */
@ApiModel(description = "返回Vo")
public class ResponseVO<V> {

    @ApiModelProperty(value = "异常Code,可选", example = "null")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String errorCode = "0";

    @ApiModelProperty("业务是否成功")
    private boolean success;
    @ApiModelProperty("分字段出错信息，例如表单中某一个字段")
    private List<FieldExceptionVO> fieldErrors;
    @ApiModelProperty("全局出错信息")
    private String msg;
    @ApiModelProperty("返回内容")
    private V data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<FieldExceptionVO> getFieldErrors() {
        return fieldErrors;
    }

    public void setFieldErrors(List<FieldExceptionVO> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public V getData() {
        return data;
    }

    public void setData(V data) {
        this.data = data;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * 无参构造
     */
    public ResponseVO() {
        super();
    }

    /**
     * 有参构造
     *
     * @param success
     * @param fieldErrors
     * @param msg
     * @param data
     */
    public ResponseVO(boolean success, List<FieldExceptionVO> fieldErrors, String msg, V data) {
        super();
        this.success = success;
        this.fieldErrors = fieldErrors;
        this.msg = msg;
        this.data = data;
    }

    public ResponseVO(boolean success, List<FieldExceptionVO> fieldErrors, String msg) {
        super();
        this.success = success;
        this.fieldErrors = fieldErrors;
        this.msg = msg;
    }

    public static <T> ResponseVO<T> successResponse(T data) {
        return response(true, Collections.emptyList(), "请求成功！", data);
    }

    public static <T> ResponseVO<T> successResponse() {
        return response(true, Collections.emptyList(), "请求成功！");
    }

    public static <T> ResponseVO<T> response(boolean success, List<FieldExceptionVO> fieldErrors, String msg, T data) {
        return new ResponseVO<>(success, fieldErrors, msg, data);
    }

    public static <T> ResponseVO<T> response(boolean success, List<FieldExceptionVO> fieldErrors, String msg) {
        return new ResponseVO<>(success, fieldErrors, msg);
    }

    public void addFieldError(String fieldName, String errorMsg) {
        if (this.getFieldErrors() == null) {
            this.fieldErrors = new ArrayList<>();
        }
        this.fieldErrors.add(new FieldExceptionVO(fieldName, errorMsg));
    }
}
