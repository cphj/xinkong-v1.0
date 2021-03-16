package xk.baseinfo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel
public class ThawUserAccountVO {
    @ApiModelProperty(notes = "手机号或者心空ID", required = true)
    @NotBlank(message = "手机号或者心空ID不能为空")
    private String mobileOrxkId;

    @ApiModelProperty(notes = "密码", required = true)
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty(notes = "身份证号", required = true)
    @NotBlank(message = "身份证号不能为空")
    private String idCard;

    @ApiModelProperty(notes = "用户名", required = true)
    @NotBlank(message = "用户名不能为空")
    private String username;
}
