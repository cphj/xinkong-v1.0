package xk.baseinfo.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class FansBO {

    @ApiModelProperty(notes = "用户账户编码", required = true)
    private String userAccountCode;

    @ApiModelProperty(notes = "编码", required = true)
    private String code;

    @ApiModelProperty(notes = "粉丝编码（粉丝账户编码）", required = true)
    private String fansCode;

    @ApiModelProperty(notes = "粉丝昵称", required = true)
    private String fansNickname;

}
