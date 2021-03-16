package xk.baseinfo.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.time.Instant;

@Data
@ApiModel
public class UserInfoBO {

    @ApiModelProperty(notes = "用户信息编码", required = true)
    private String code;

    @ApiModelProperty(notes = "用户账户编码", required = true)
    private String userAccountCode;

    @ApiModelProperty(notes = "性别", required = true)
    private int gender;

    @ApiModelProperty(notes = "身份证", required = true)
    private String idCard;

    @ApiModelProperty(notes = "身份证姓名", required = true)
    private String username;

    @ApiModelProperty(notes = "出生日期", required = true)
    private Instant birthday;

    @ApiModelProperty(notes = "昵称", required = true)
    private String nickname;

    @ApiModelProperty(notes = "兴趣爱好", required = true)
    private String hobby;

    @ApiModelProperty(notes = "地址", required = true)
    private String address;
}
