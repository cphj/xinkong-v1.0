package xk.baseinfo.enmu;

public enum UserStautsEnum {
    NORMAL(1,"正常"),
    FREEZE(2,"冻结"),
    TITLE(3,"封号"),
    CANCELLATION(4,"注销");

    private int code;
    private String msg;

    UserStautsEnum(int code, String msg) {
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
