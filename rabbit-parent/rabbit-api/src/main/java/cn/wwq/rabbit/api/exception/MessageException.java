package cn.wwq.rabbit.api.exception;

public class MessageException extends Exception {
    private static final long serialVersionUID = -9031312685026766875L;

    public MessageException(){
        super();
    }

    public MessageException(String message) {
        super(message);
    }

    public MessageException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageException(Throwable cause) {
        super(cause);
    }
}
