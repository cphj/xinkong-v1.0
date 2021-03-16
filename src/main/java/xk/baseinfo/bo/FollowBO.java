package xk.baseinfo.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class FollowBO {

    @ApiModelProperty(notes = "编码", required = true)
    private String code;

    @ApiModelProperty(notes = "用户账户编码", required = true)
    private String userAccountCode;

    @ApiModelProperty(notes = "关注用户账户编码", required = true)
    private String followCode;

    @ApiModelProperty(notes = "关注用户账户昵称", required = true)
    private String followNickname;
}
