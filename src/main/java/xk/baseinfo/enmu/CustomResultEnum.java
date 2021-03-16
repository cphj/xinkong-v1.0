package xk.baseinfo.enmu;

public enum CustomResultEnum {
    SUCCESS(200, "成功"),

    //用户账户
    FAIL(1101,"用户账号查询失败"),
    PASSWORD_ERROR(1102,"密码错误"),
    FREEZE(1103,"该用户账号已冻结"),
    TITLE(1104,"该用户账号已封号"),
    DELETED(1105,"该用户账号已删除"),
    ENABLE(1106,"该用户账号已禁用"),
    REGISTER(1107,"该手机号已注册"),
    CANCELLATION(1108,"该用户账号已注销"),
    USERACCOUNT_STATUS_ERROR(1109,"该用户账号状态不正确"),

    //用户信息
    IDCARD_REPEAT(1201,"该用户信息已填加完"),
    XKID_ERROR(1202,"该心空ID错误"),
    INFO_QUERY_ERROR(1203,"该用户信息查询失败"),
    PASSWORD_VALID(1204,"密码校验失败"),
    IDCARD_VALID(1205,"身份证校验失败"),
    USERNAME_FAIL(1206,"用户名校验失败"),
    MULTIPLE_DATA(1207,"查询出多条数量"),
    STATUS_ERROR(1208,"当前账号状态不正确"),

    //好友分组
    FRIEND_GROUP_QUERY_FAIL(1301,"好友分组查询失败"),
    FRIEND_GROUP_DELETED(1302,"好友分组已删除"),

    //文件上传
    NO_DOCUMENTS(1401,"没有上传文件"),
    DOCUMENTS_NAME_ERROR(1402,"文件名不正确"),
    UPLOAD_ERROR(1403,"上传失败"),
    DOCUMENTS_FORMAT_ERROR(1404,"文件夹格式错误"),
    SAVE_IMAGE_ERROR(1405,"文件保存失败"),

    //粉丝
    NO_FANS(1501,"该用户没粉丝"),

    //关注
    NO_FOLLOW(1601,"没有该关注用户"),

    //互关
    NO_EACH_OTHER_FOLLOW(1701,"没有该互相关注的用户"),

    //好友
    ADD_FRIEND(1801,"你已经添加了该好友，等待对方同意"),
    BE_OVERDUE(1802,"添加好友过期")

    ;

    private int code;
    private String msg;

    CustomResultEnum(int code, String msg) {
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
