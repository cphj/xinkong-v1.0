package xk.baseinfo.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class UserAccountBO {

    @ApiModelProperty(notes = "用户账号编码", required = true)
    private String code;

    @ApiModelProperty(notes = "昵称编码", required = true)
    private String nickname;

    @ApiModelProperty(notes = "手机号", required = true)
    private String mobile;

    @ApiModelProperty(notes = "心空ID", required = true)
    private String xkId;

    @ApiModelProperty(notes = "图像url",required = true)
    private String imageUrl;

    @ApiModelProperty(notes = "用户账户状态",required = true)
    private int status;

//    @ApiModelProperty(notes = "是否删除",required = true)
//    private boolean deleted;
//
//    @ApiModelProperty(notes = "是否禁用",required = true)
//    private boolean enable;
}
