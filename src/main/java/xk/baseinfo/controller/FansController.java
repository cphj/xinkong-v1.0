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
import xk.baseinfo.bo.FansBO;
import xk.baseinfo.enmu.CustomResultEnum;
import xk.baseinfo.service.FansService;
import xk.baseinfo.vo.DeleteFansVO;
import xk.baseinfo.vo.PageFansVO;
import xk.common.exception.BusinessException;
import xk.common.exception.CustomException;
import xk.common.result.ApiResult;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api")
@Api(tags = "粉丝管理")
public class FansController {

    @Autowired
    private FansService fansService;

    @PostMapping("/listFans")
    @ApiOperation(value="粉丝列表", notes="粉丝列表")
    @ApiImplicitParam(name = "fansVO", value = "粉丝列表入参实体类", required = true, dataType = "FansVO")
    public ApiResult<IPage<FansBO>> listFans(@Valid @RequestBody PageFansVO pageFansVO){
        try{
            IPage<FansBO> page = fansService.listFans(pageFansVO);
            return new ApiResult<IPage<FansBO>>(CustomResultEnum.SUCCESS.getCode(), CustomResultEnum.SUCCESS.getMsg(), page);
        } catch (CustomException e){
            throw new BusinessException(e.getCode(), e.getMsg());
        }
    }

    @PostMapping("/deleteFans")
    @ApiOperation(value="删除粉丝", notes="删除粉丝")
    @ApiImplicitParam(name = "deleteFansVO", value = "删除粉丝入参实体类", required = true, dataType = "DeleteFansVO")
    private ApiResult<Void> deleteFans(@Valid @RequestBody DeleteFansVO deleteFansVO){
        try{
            fansService.deleteFans(deleteFansVO);
            return new ApiResult<Void>(CustomResultEnum.SUCCESS.getCode(), CustomResultEnum.SUCCESS.getMsg(), null);
        } catch (CustomException e){
            throw new BusinessException(e.getCode(), e.getMsg());
        }
    }
}
