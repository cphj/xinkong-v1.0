package xk.baseinfo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;

@Data
@ApiModel
public class UserInfoVO {

    @ApiModelProperty(notes = "用户账号编码", required = true)
    @NotBlank(message = "用户账号编码不能为空")
    private String userAccountCode;

    @ApiModelProperty(notes = "性别", required = true)
    @Size(min = 1, max = 2,message = "性别必选")
    private int gender;

    @ApiModelProperty(notes = "身份证号", required = true)
    @NotBlank(message = "用户账号编码不能为空")
    private String idCard;

    @ApiModelProperty(notes = "用户名", required = true)
    @NotBlank(message = "用户名不能为空")
    private String username;

    @ApiModelProperty(notes = "生日", required = true)
    @NotNull(message = "生日不能为空")
    private Instant birthday;

    @ApiModelProperty(notes = "昵称", required = true)
    @NotBlank(message = "昵称不能为空")
    private String nickname;

    @ApiModelProperty(notes = "兴趣爱好", required = true)
    private String hobby;

    @ApiModelProperty(notes = "地址", required = true)
    private String address;
}
