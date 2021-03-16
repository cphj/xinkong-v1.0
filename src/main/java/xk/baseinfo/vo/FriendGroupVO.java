package xk.baseinfo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel
public class FriendGroupVO {

    @ApiModelProperty(notes = "好友分组名称", required = true)
    @NotBlank(message = "好友分组名称不能为空")
    private String friendGroupName;

    @ApiModelProperty(notes = "好友分组创建人编码", required = true)
    @NotBlank(message = "好友分组创建人编码不能为空")
    private String createUserAccountCode;
}
