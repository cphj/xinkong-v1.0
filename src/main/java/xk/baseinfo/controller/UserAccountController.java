package xk.baseinfo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xk.baseinfo.bo.UserAccountBO;
import xk.baseinfo.enmu.CustomResultEnum;
import xk.baseinfo.service.UserAccountService;
import xk.baseinfo.vo.*;
import xk.common.exception.BusinessException;
import xk.common.exception.CustomException;
import xk.common.result.ApiResult;

import javax.validation.Valid;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/api")
@Api(tags = "用户账户管理")
public class UserAccountController {

    @Autowired
    private UserAccountService userAccountService;

    @PostMapping("/getUserAccount")
    @ApiOperation(value="获取用户账户", notes="获取用户账户")
    @ApiImplicitParam(name = "userAccountVO", value = "用户账户实体类", required = true, dataType = "UserAccountVO")
    @ApiResponse(code = 200,message = "成功",response = UserAccountBO.class)
    public ApiResult<UserAccountBO> getUserAccount(@Valid @RequestBody UserAccountVO userAccountVO){
        try {
            UserAccountBO userAccount = userAccountService.getUserAccount(userAccountVO);
            return new ApiResult<>(CustomResultEnum.SUCCESS.getCode(), CustomResultEnum.SUCCESS.getMsg(), userAccount);
        } catch (CustomException e){
            throw new BusinessException(e.getCode(), e.getMsg());
        }
    }

    @PostMapping("/registerAccount")
    @ApiOperation(value="用户注册", notes="用户注册")
    @ApiImplicitParam(name = "registerUserAccountVO", value = "用户注册实体类", required = true, dataType = "RegisterUserAccountVO")
    @ApiResponse(code = 200,message = "成功",response = UserAccountBO.class)
    public ApiResult<UserAccountBO> registerUserAccount(@Valid @RequestBody RegisterUserAccountVO registerUserAccountVO){
        try {
            UserAccountBO userAccountBO = userAccountService.registerUserAccount(registerUserAccountVO);
            return new ApiResult<>(CustomResultEnum.SUCCESS.getCode(), CustomResultEnum.SUCCESS.getMsg(), userAccountBO);
        } catch (CustomException e){
            throw new BusinessException(e.getCode(), e.getMsg());
        }
    }

    @PostMapping("/freezeUserAccount")
    @ApiOperation(value="用户冻结", notes="用户冻结")
    @ApiImplicitParam(name = "freezeUserAccountVO", value = "用用户冻结实体类", required = true, dataType = "FreezeUserAccountVO")
    @ApiResponse(code = 200,message = "成功")
    public ApiResult<Void> freezeUserAccount(@Valid @RequestBody FreezeUserAccountVO freezeUserAccountVO){
        try{
            UserAccountBO userAccountBO = userAccountService.freezeUserAccount(freezeUserAccountVO);
            return new ApiResult<Void>(CustomResultEnum.SUCCESS.getCode(), CustomResultEnum.SUCCESS.getMsg(),null);
        } catch (CustomException e){
            throw new BusinessException(e.getCode(), e.getMsg());
        }
    }

    @PostMapping("/thawUserAccount")
    @ApiOperation(value="用户解冻", notes="用户解冻")
    @ApiImplicitParam(name = "thawUserAccountVO", value = "用户解冻实体类", required = true, dataType = "ThawUserAccountVO")
    @ApiResponse(code = 200,message = "成功")
    public ApiResult<Void> thawUserAccount(@Valid @RequestBody ThawUserAccountVO thawUserAccountVO){
        try{
            UserAccountBO userAccountBO = userAccountService.thawUserAccount(thawUserAccountVO);
            return new ApiResult<Void>(CustomResultEnum.SUCCESS.getCode(), CustomResultEnum.SUCCESS.getMsg(),null);
        } catch (CustomException e){
            throw new BusinessException(e.getCode(), e.getMsg());
        }
    }

    @PostMapping("/titleUserAccount")
    @ApiOperation(value="用户解封", notes="用户解封")
    @ApiImplicitParam(name = "titleUserAccountVO", value = "用户解封实体类", required = true, dataType = "TitleUserAccountVO")
    @ApiResponse(code = 200,message = "成功")
    public ApiResult<UserAccountBO> titleUserAccount(@Valid @RequestBody TitleUserAccountVO titleUserAccountVO){
        try {
            UserAccountBO userAccountBO = userAccountService.titleUserAccount(titleUserAccountVO);
            return new ApiResult<UserAccountBO>(CustomResultEnum.SUCCESS.getCode(), CustomResultEnum.SUCCESS.getMsg(),userAccountBO);
        } catch (CustomException e){
            throw new BusinessException(e.getCode(), e.getMsg());
        }
    }

    @PostMapping("/cancellationUserAccount")
    @ApiOperation(value="用户注销", notes="用户注销")
    @ApiImplicitParam(name = "cancellationUserAccountVO", value = "用户注销实体类", required = true, dataType = "CancellationUserAccountVO")
    @ApiResponse(code = 200,message = "成功")
    public ApiResult<Void> cancellationUserAccount(@Valid @RequestBody CancellationUserAccountVO cancellationUserAccountVO){
        try{
            UserAccountBO userAccountBO = userAccountService.cancellationUserAccount(cancellationUserAccountVO);
            return new ApiResult<Void>(CustomResultEnum.SUCCESS.getCode(), CustomResultEnum.SUCCESS.getMsg(),null);
        } catch (CustomException e){
            throw new BusinessException(e.getCode(), e.getMsg());
        }
    }

    @PostMapping("/retrievePasswordUserAccount")
    @ApiOperation(value="用户找回密码", notes="用户找回密码")
    @ApiImplicitParam(name = "retrievePasswordUserAccountVO", value = "用户找回密码实体类", required = true, dataType = "RetrievePasswordUserAccountVO")
    @ApiResponse(code = 200,message = "成功",response = UserAccountBO.class)
    public ApiResult<Void> retrievePasswordUserAccount(@Valid @RequestBody RetrievePasswordUserAccountVO retrievePasswordUserAccountVO){
        try {
            UserAccountBO userAccountBO = userAccountService.retrievePasswordUserAccount(retrievePasswordUserAccountVO);
            return new ApiResult<Void>(CustomResultEnum.SUCCESS.getCode(), CustomResultEnum.SUCCESS.getMsg(), null);
        } catch (CustomException e){
            throw new BusinessException(e.getCode(), e.getMsg());
        }
    }
}
