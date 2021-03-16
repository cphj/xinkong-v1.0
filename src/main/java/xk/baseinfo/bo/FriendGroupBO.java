package xk.baseinfo.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class FriendGroupBO {

    @ApiModelProperty(notes = "好友分组编码", required = true)
    private String code;

    @ApiModelProperty(notes = "好友分组名称", required = true)
    private String friendGroupName;

    @ApiModelProperty(notes = "好友分组创建人编码", required = true)
    private String createUserAccountCode;
}
