package xk.baseinfo.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class AdjunctionFriendBO {

    @ApiModelProperty(notes = "编码", required = true)
    private String code;

    @ApiModelProperty(notes = "用户账户编码", required = true)
    private String userAccountCode;

    @ApiModelProperty(notes = "好友用户账户编码", required = true)
    private String friendCode;

    @ApiModelProperty(notes = "好友昵称", required = true)
    private String friendNickname;
}
