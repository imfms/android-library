package cn.f_ms.android.ui;

/**
 * ui参数错误信息 包装异常
 * @author f_ms
 */
public class ArgumentException extends RuntimeException {
    public ArgumentException() {
    }

    public ArgumentException(String s) {
        super(s);
    }

    public ArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public ArgumentException(Throwable cause) {
        super(cause);
    }

    /**
     * 参数格式错误 包装异常
     */
    public static class ArgumentTypeErrorException extends ArgumentException {
        public ArgumentTypeErrorException() {
        }

        public ArgumentTypeErrorException(String s) {
            super(s);
        }

        public ArgumentTypeErrorException(String message, Throwable cause) {
            super(message, cause);
        }

        public ArgumentTypeErrorException(Throwable cause) {
            super(cause);
        }
    }
}
