package xk.baseinfo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xk.baseinfo.bo.UserInfoBO;
import xk.baseinfo.enmu.CustomResultEnum;
import xk.baseinfo.service.UserInfoService;
import xk.baseinfo.vo.UserInfoVO;
import xk.common.exception.BusinessException;
import xk.common.exception.CustomException;
import xk.common.result.ApiResult;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api")
@Api(tags = "用户信息管理")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @PostMapping("/addUserInfo")
    @ApiOperation(value="添加用户信息", notes="添加用户信息")
    @ApiImplicitParam(name = "userInfoVO", value = "用户账户实体类", required = true, dataType = "UserInfoVO")
    @ApiResponse(code = 200,message = "成功",response = UserInfoBO.class)
    public ApiResult<UserInfoBO> addUserInfo(@Valid @RequestBody UserInfoVO userInfoVO){
        try{
            UserInfoBO userInfoBO = userInfoService.addUserInfo(userInfoVO);
            return new ApiResult<>(CustomResultEnum.SUCCESS.getCode(), CustomResultEnum.SUCCESS.getMsg(),userInfoBO);
        } catch (CustomException e){
            throw new BusinessException(e.getCode(), e.getMsg());
        }
    }
}
