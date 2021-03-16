package xk.baseinfo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel
public class UpdateFriendGroupVO {

    @ApiModelProperty(notes = "好友分组编码", required = true)
    @NotBlank(message = "好友分组编码不能为空")
    private String code;

    @ApiModelProperty(notes = "好友分组名", required = true)
    @NotBlank(message = "好友分组名不能为空")
    private String friendGroupName;
}
