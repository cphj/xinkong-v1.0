package xk.baseinfo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@ApiModel
public class RegisterUserAccountVO {

    @ApiModelProperty(notes = "手机号", required = true)
    @NotBlank(message = "手机号不能为空")
    private String mobile;

    @ApiModelProperty(notes = "密码", required = true)
    @Size(min = 8, max = 16, message = "密码长度为8到16位")
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty(notes = "邀请码", required = true)
    private String invitation;

    @ApiModelProperty(notes = "昵称", required = true)
    @Size(min = 1, max = 6, message = "昵称不能超过6个字")
    private String nickname;

    @ApiModelProperty(notes = "图像", required = true)
    private String imageUrl;
}
