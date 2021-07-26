package top.dreamcenter.epoch.exception;

public class TokenParseFailException extends Exception{

    private String message;

    public TokenParseFailException() {
    }

    public TokenParseFailException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return "token未通过验证" + (message!=null? ":" +message : "");
    }
}
