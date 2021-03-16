package xk.baseinfo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel
public class PageFansVO {

    @ApiModelProperty(notes = "用户账户编码", required = true)
    @NotBlank(message = "用户账户编码不能为空")
    private String userAccountCode;

    @ApiModelProperty(notes = "页码", required = true)
    @NotBlank(message = "页码不能为空")
    private Integer pageNo;

    @ApiModelProperty(notes = "分页大小", required = true)
    @NotBlank(message = "分页大小不能为空")
    private Integer pageSize;
}
