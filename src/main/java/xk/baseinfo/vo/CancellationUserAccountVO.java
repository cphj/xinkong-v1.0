package xk.baseinfo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel
public class CancellationUserAccountVO {

    @ApiModelProperty(notes = "手机号", required = true)
    @NotBlank(message = "手机号不能为空")
    private String mobile;
}
