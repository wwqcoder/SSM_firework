package cn.wwq.rabbit.api.exception;

/**
 * 运行时异常
 */
public class MessageRunTimeException extends RuntimeException {

    private static final long serialVersionUID = -8917533491970717784L;

    public MessageRunTimeException() {
    }

    public MessageRunTimeException(String message) {
        super(message);
    }

    public MessageRunTimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageRunTimeException(Throwable cause) {
        super(cause);
    }
}
