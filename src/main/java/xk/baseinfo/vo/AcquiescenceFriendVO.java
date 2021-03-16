package xk.baseinfo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel
public class AcquiescenceFriendVO {

    @ApiModelProperty(notes = "用户账户编码", required = true)
    @NotBlank(message = "用户账户编码不能为空")
    private String userAccountCode;

    @ApiModelProperty(notes = "好友用户账户编码", required = true)
    @NotBlank(message = "好友用户账户编码不能为空")
    private String friendCode;

    @ApiModelProperty(notes = "好友分组编码", required = true)
    private String friendGroupCode;

    @ApiModelProperty(notes = "好友分组名称", required = true)
    private String friendGroupName;

    @ApiModelProperty(notes = "好友昵称", required = true)
    private String friendNickname;

    @ApiModelProperty(notes = "好友图像", required = true)
    private String friendHeadPortraitUrl;

    @ApiModelProperty(notes = "好友在线", required = true)
    private String online;

    @ApiModelProperty(notes = "好友签名", required = true)
    private String friendSigner;

    @ApiModelProperty(notes = "好友备注", required = true)
    private String friendRemark;

    @ApiModelProperty(notes = "当前用户昵称", required = true)
    private String nickname;
}
