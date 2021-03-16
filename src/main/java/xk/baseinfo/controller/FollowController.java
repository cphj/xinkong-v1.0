package xk.baseinfo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xk.baseinfo.bo.FollowBO;
import xk.baseinfo.enmu.CustomResultEnum;
import xk.baseinfo.service.FollowService;
import xk.baseinfo.vo.CancelFollowVO;
import xk.baseinfo.vo.FollowVO;
import xk.baseinfo.vo.PageFollowVO;
import xk.common.exception.BusinessException;
import xk.common.exception.CustomException;
import xk.common.result.ApiResult;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api")
@Api(tags = "关注管理")
public class FollowController {

    @Autowired
    private FollowService followService;

    @PostMapping("/addFollow")
    @ApiOperation(value="添加关注", notes="添加关注")
    @ApiImplicitParam(name = "followVO", value = "添加关注入参实体类", required = true, dataType = "FollowVO")
    @ApiResponse(code = 200,message = "成功",response = FollowBO.class)
    public ApiResult<FollowBO> addFollow(@Valid @RequestBody FollowVO followVO){
        try{
            FollowBO followBO = followService.addFollow(followVO);
            return new ApiResult<>(CustomResultEnum.SUCCESS.getCode(), CustomResultEnum.SUCCESS.getMsg(), followBO);
        }catch (CustomException e){
            throw new BusinessException(e.getCode(), e.getMsg());
        }

    }

    @PostMapping("/listFollow")
    @ApiOperation(value="关注列表", notes="关注列表")
    @ApiImplicitParam(name = "pageFollowVO", value = "关注列表入参实体类", required = true, dataType = "PageFollowVO")
    @ApiResponse(code = 200,message = "成功",response = FollowBO.class)
    public ApiResult<IPage<FollowBO>> listFollow(@Valid @RequestBody PageFollowVO pageFollowVO){
        try {
            IPage<FollowBO> page = followService.listFollow(pageFollowVO);
            return new ApiResult<IPage<FollowBO>>(CustomResultEnum.SUCCESS.getCode(), CustomResultEnum.SUCCESS.getMsg(), page);
        }catch (CustomException e){
            throw new BusinessException(e.getCode(), e.getMsg());
        }
    }

    @PostMapping("/cancelFollow")
    @ApiOperation(value="取消关注", notes="取消关注")
    @ApiImplicitParam(name = "pageFollowVO", value = "取消关注入参实体类", required = true, dataType = "PageFollowVO")
    @ApiResponse(code = 200,message = "成功",response = FollowBO.class)
    public ApiResult<Void> cancelFollow(@Valid @RequestBody CancelFollowVO cancelFollowVO){
        try {
            followService.cancelFollow(cancelFollowVO);
            return new ApiResult<Void>(CustomResultEnum.SUCCESS.getCode(), CustomResultEnum.SUCCESS.getMsg(), null);
        }catch (CustomException e){
            throw new BusinessException(e.getCode(), e.getMsg());
        }
    }
}
