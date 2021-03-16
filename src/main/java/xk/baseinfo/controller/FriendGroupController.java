package xk.baseinfo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xk.baseinfo.bo.FriendGroupBO;
import xk.baseinfo.enmu.CustomResultEnum;
import xk.baseinfo.service.FriendGroupService;
import xk.baseinfo.vo.DeleteFriendGroupVO;
import xk.baseinfo.vo.FriendGroupVO;
import xk.baseinfo.vo.ListFriendGroupVO;
import xk.baseinfo.vo.UpdateFriendGroupVO;
import xk.common.exception.BusinessException;
import xk.common.exception.CustomException;
import xk.common.result.ApiResult;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api")
@Api(tags = "好友分组管理")
public class FriendGroupController {

    @Autowired
    private FriendGroupService friendGroupService;

    @PostMapping("/addFriendGroup")
    @ApiOperation(value="添加好友分组", notes="添加好友分组")
    @ApiImplicitParam(name = "friendGroupVO", value = "好友分组实体类", required = true, dataType = "FriendGroupVO")
    public ApiResult<FriendGroupBO> addFriendGroup(@Valid @RequestBody FriendGroupVO friendGroupVO){
        try {
            FriendGroupBO friendGroupBO = friendGroupService.addFriendGroup(friendGroupVO);
            return new ApiResult<FriendGroupBO>(CustomResultEnum.SUCCESS.getCode(), CustomResultEnum.SUCCESS.getMsg(), friendGroupBO);
        } catch (CustomException e){
            throw new BusinessException(e.getCode(), e.getMsg());
        }
    }

    @PostMapping("/updateFriendGroup")
    @ApiOperation(value="修改好友分组", notes="修改好友分组")
    @ApiImplicitParam(name = "updateFriendGroupVO", value = "好友分组实体类", required = true, dataType = "UpdateFriendGroupVO")
    public ApiResult<FriendGroupBO> updateFriendGroup(@Valid @RequestBody UpdateFriendGroupVO updateFriendGroupVO){
        try{
            FriendGroupBO friendGroupBO = friendGroupService.updateFriendGroup(updateFriendGroupVO);
            return new ApiResult<FriendGroupBO>(CustomResultEnum.SUCCESS.getCode(), CustomResultEnum.SUCCESS.getMsg(), friendGroupBO);
        } catch (CustomException e){
            throw new BusinessException(e.getCode(), e.getMsg());
        }
    }

    @PostMapping("/deleteFriendGroup")
    @ApiOperation(value="删除分组", notes="删除分组")
    @ApiImplicitParam(name = "deleteFriendGroupVO", value = "删除好友分组实体类", required = true, dataType = "DeleteFriendGroupVO")
    public ApiResult<Void> deleteFriendGroup(@Valid @RequestBody DeleteFriendGroupVO deleteFriendGroupVO){
        try {
            friendGroupService.deleteFriendGroup(deleteFriendGroupVO);
            return new ApiResult<>(CustomResultEnum.SUCCESS.getCode(), CustomResultEnum.SUCCESS.getMsg(), null);
        } catch (CustomException e){
            throw new BusinessException(e.getCode(), e.getMsg());
        }
    }

    @PostMapping("/listFriendGroup")
    @ApiOperation(value="好友分组列表", notes="好友分组列表")
    @ApiImplicitParam(name = "listFriendGroupVO", value = "好友分组列表实体类", required = true, dataType = "ListFriendGroupVO")
    public ApiResult<List<FriendGroupBO>> listFriendGroup(@Valid @RequestBody ListFriendGroupVO listFriendGroupVO){
        try{
            List<FriendGroupBO> friendGroups = friendGroupService.listFriendGroup(listFriendGroupVO);
            return new ApiResult<List<FriendGroupBO>>(CustomResultEnum.SUCCESS.getCode(), CustomResultEnum.SUCCESS.getMsg(), friendGroups);
        } catch (CustomException e){
            throw new BusinessException(e.getCode(), e.getMsg());
        }
    }

}
