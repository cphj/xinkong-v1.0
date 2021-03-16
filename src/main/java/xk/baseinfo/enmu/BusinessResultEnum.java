package xk.baseinfo.enmu;

public enum  BusinessResultEnum {

    FOLLOW_ACCOUNT_ERROR(10000,"关注用户错误");

    private int code;
    private String msg;

    BusinessResultEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
