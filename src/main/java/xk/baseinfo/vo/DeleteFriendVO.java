package xk.baseinfo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel
public class DeleteFriendVO {

    @ApiModelProperty(notes = "用户账户编码", required = true)
    @NotBlank(message = "用户账户编码不能为空")
    private String userAccountCode;

    @ApiModelProperty(notes = "好友用户账户编码", required = true)
    @NotBlank(message = "好友用户账户编码不能为空")
    private String friendCode;
}
