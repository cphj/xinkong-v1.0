package xk.baseinfo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xk.baseinfo.bo.AdjunctionFriendBO;
import xk.baseinfo.enmu.CustomResultEnum;
import xk.baseinfo.service.FriendGroupService;
import xk.baseinfo.service.FriendService;
import xk.baseinfo.vo.AcquiescenceFriendVO;
import xk.baseinfo.vo.AdjunctionFriendVO;
import xk.baseinfo.vo.DeleteFriendVO;
import xk.baseinfo.vo.RefuseFriendVO;
import xk.common.exception.BusinessException;
import xk.common.exception.CustomException;
import xk.common.result.ApiResult;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api")
@Api(tags = "好友管理")
public class FriendController {

    @Autowired
    private FriendService friendService;

    @PostMapping("/addFriend")
    @ApiOperation(value="添加好友", notes="添加好友")
    @ApiImplicitParam(name = "adjunctionFriendVO", value = "添加好友入参实体类", required = true, dataType = "AdjunctionFriendVO")
    private ApiResult<AdjunctionFriendBO> addFriend(@Valid @RequestBody AdjunctionFriendVO adjunctionFriendVO){
        try {
            AdjunctionFriendBO adjunctionFriendBO = friendService.addFriend(adjunctionFriendVO);
            return new ApiResult<>(CustomResultEnum.SUCCESS.getCode(), CustomResultEnum.SUCCESS.getMsg(), adjunctionFriendBO);
        } catch (CustomException e){
            throw new BusinessException(e.getCode(), e.getMsg());
        }
    }

    @PostMapping("/acquiescenceFriend")
    @ApiOperation(value="同意添加好友", notes="同意添加好友")
    @ApiImplicitParam(name = "acquiescenceFriendVO", value = "同意添加好友入参实体类", required = true, dataType = "AcquiescenceFriendVO")
    private ApiResult<Void> acquiescenceFriend(@Valid @RequestBody AcquiescenceFriendVO acquiescenceFriendVO){
        try {
            friendService.acquiescenceFriend(acquiescenceFriendVO);
            return new ApiResult<Void>(CustomResultEnum.SUCCESS.getCode(), CustomResultEnum.SUCCESS.getMsg(), null);
        } catch (CustomException e){
            throw new BusinessException(e.getCode(), e.getMsg());
        }
    }

    @PostMapping("/refuseFriend")
    @ApiOperation(value="拒绝添加好友", notes="拒绝添加好友")
    @ApiImplicitParam(name = "refuseFriendVO", value = "拒绝添加好友入参实体类", required = true, dataType = "RefuseFriendVO")
    private ApiResult<Void> refuseFriend(@Valid @RequestBody RefuseFriendVO refuseFriendVO){
        try {
            AdjunctionFriendBO adjunctionFriendBO = friendService.refuseFriend(refuseFriendVO);
            return new ApiResult<Void>(CustomResultEnum.SUCCESS.getCode(), CustomResultEnum.SUCCESS.getMsg(), null);
        } catch (CustomException e){
            throw new BusinessException(e.getCode(), e.getMsg());
        }
    }

    @PostMapping("/deleteFriend")
    @ApiOperation(value="拒绝添加好友", notes="拒绝添加好友")
    @ApiImplicitParam(name = "deleteFriendVO", value = "拒绝添加好友入参实体类", required = true, dataType = "DeleteFriendVO")
    private ApiResult<Void> deleteFriend(@Valid @RequestBody DeleteFriendVO deleteFriendVO){
        try {
            friendService.deleteFriend(deleteFriendVO);
            return new ApiResult<Void>(CustomResultEnum.SUCCESS.getCode(), CustomResultEnum.SUCCESS.getMsg(), null);
        } catch (CustomException e){
            throw new BusinessException(e.getCode(), e.getMsg());
        }
    }
}
