package xk.baseinfo.enmu;

public enum FriendStatusEnum {

    ACQUIESCENCE(1,"等待通过"),
    AGREE(2,"同意"),
    REFUSE(3,"拒绝"),
    BE_OVERDUE(4,"过期");

    private int code;
    private String msg;

    FriendStatusEnum(int code, String msg) {
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
