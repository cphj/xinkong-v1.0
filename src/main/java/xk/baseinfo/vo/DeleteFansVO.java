package xk.baseinfo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel
public class DeleteFansVO {

    @ApiModelProperty(notes = "用户账户编码", required = true)
    @NotBlank(message = "用户账户编码不能为空")
    private String userAccountCode;

    @ApiModelProperty(notes = "粉丝编码（粉丝账户编码）", required = true)
    @NotBlank(message = "粉丝账户编码编码不能为空")
    private String fansCode;
}
