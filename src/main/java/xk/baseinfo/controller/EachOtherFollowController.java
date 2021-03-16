package xk.baseinfo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xk.baseinfo.bo.EachOtherFollowBO;
import xk.baseinfo.enmu.CustomResultEnum;
import xk.baseinfo.service.EachOtherFollowService;
import xk.baseinfo.vo.CancelEachOtherFollowVO;
import xk.baseinfo.vo.PageEachOtherFollowVO;
import xk.common.exception.BusinessException;
import xk.common.exception.CustomException;
import xk.common.result.ApiResult;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api")
@Api(tags = "互相关注管理")
public class EachOtherFollowController {

    @Autowired
    private EachOtherFollowService eachOtherFollowService;

    @PostMapping("/listEachOtherFollow")
    @ApiOperation(value="互相关注列表", notes="互相关注列表")
    @ApiImplicitParam(name = "pageEachOtherFollowVO", value = "互相关注列表入参实体类", required = true, dataType = "PageEachOtherFollowVO")
    public ApiResult<IPage<EachOtherFollowBO>> listEachOtherFollow(@Valid @RequestBody PageEachOtherFollowVO pageEachOtherFollowVO){
        try {
            IPage<EachOtherFollowBO> page = eachOtherFollowService.listEachOtherFollow(pageEachOtherFollowVO);
            return new ApiResult<IPage<EachOtherFollowBO>>(CustomResultEnum.SUCCESS.getCode(), CustomResultEnum.SUCCESS.getMsg(), page);
        } catch (CustomException e){
            throw new BusinessException(e.getCode(), e.getMsg());
        }
    }

    @PostMapping("/cancelEachOtherFollow")
    @ApiOperation(value="取消互相关注", notes="取消互相关注")
    @ApiImplicitParam(name = "cancelEachOtherFollowVO", value = "取消互相关注入参实体类", required = true, dataType = "CancelEachOtherFollowVO")
    public ApiResult<Void> cancelEachOtherFollow(@Valid @RequestBody CancelEachOtherFollowVO cancelEachOtherFollowVO){
        try {
            eachOtherFollowService.cancelEachOtherFollow(cancelEachOtherFollowVO);
            return new ApiResult<Void>(CustomResultEnum.SUCCESS.getCode(), CustomResultEnum.SUCCESS.getMsg(), null);
        } catch (CustomException e){
            throw new BusinessException(e.getCode(), e.getMsg());
        }
    }
}
