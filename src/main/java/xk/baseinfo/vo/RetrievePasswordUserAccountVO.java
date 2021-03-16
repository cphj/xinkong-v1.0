package xk.baseinfo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel
public class RetrievePasswordUserAccountVO {

    @ApiModelProperty(notes = "手机号", required = true)
    @NotBlank(message = "手机号不能为空")
    private String mobile;

    @ApiModelProperty(notes = "新密码", required = true)
    @NotBlank(message = "新密码不能为空")
    private String password;

    @ApiModelProperty(notes = "心空ID", required = true)
    @NotBlank(message = "心空ID不能为空")
    private String xkId;
}
