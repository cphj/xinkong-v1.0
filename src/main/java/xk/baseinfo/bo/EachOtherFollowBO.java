package xk.baseinfo.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class EachOtherFollowBO {

    @ApiModelProperty(notes = "编码", required = true)
    private String code;

    @ApiModelProperty(notes = "用户账户编码", required = true)
    private String userAccountCode;

    @ApiModelProperty(notes = "被关注人编码", required = true)
    private String eachUserCode;

    @ApiModelProperty(notes = "被关注人姓名", required = true)
    private String eachUserNickname;
}
