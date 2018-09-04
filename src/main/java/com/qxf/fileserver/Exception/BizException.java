package com.qxf.fileserver.Exception;

/**
 * 系统异常
 * 
 * @author LUYI374
 * @date 2017年2月12日
 * @since 1.0.0
 */
public class BizException extends RuntimeException {

    private static final long serialVersionUID = 1095242212086237834L;

    protected Object errorCode;
    protected Object[] args;

    public BizException() {
        super();
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }

    public BizException(String message) {
        super(message);
    }

    public BizException(String message, Object[] args, Throwable cause) {
        super(message, cause);
        this.args = args;
    }

    public BizException(String message, Object[] args) {
        super(message);
        this.args = args;
    }

    public BizException(Object errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public BizException(Object errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public BizException(Object errorCode, String message, Object[] args, Throwable cause) {
        super(message, cause);
        this.args = args;
        this.errorCode = errorCode;
    }

    public BizException(Object errorCode, String message, Object[] args) {
        super(message);
        this.args = args;
        this.errorCode = errorCode;
    }

    public BizException(Throwable cause) {
        super(cause);
    }

    public Object[] getArgs() {
        return args;
    }

    public Object getErrorCode() {
        return errorCode;
    }
}
