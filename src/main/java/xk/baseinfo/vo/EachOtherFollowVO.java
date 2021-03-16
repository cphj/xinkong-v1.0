package xk.baseinfo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel
public class EachOtherFollowVO {

    @ApiModelProperty(notes = "用户账户编码", required = true)
    @NotBlank(message = "用户账户编码不能为空")
    private String userAccountCode;

    @ApiModelProperty(notes = "被关注人编码", required = true)
    @NotBlank(message = "被关注人编码不能为空")
    private String eachUserCode;

    @ApiModelProperty(notes = "被关注人姓名", required = true)
    @NotBlank(message = "被关注人姓名不能为空")
    private String eachUserNickname;
}
