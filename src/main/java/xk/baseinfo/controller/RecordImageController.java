package xk.baseinfo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xk.baseinfo.enmu.CustomResultEnum;
import xk.baseinfo.service.RecordImageService;
import xk.baseinfo.vo.UpdateRecordImageAccountVO;
import xk.common.exception.BusinessException;
import xk.common.exception.CustomException;
import xk.common.result.ApiResult;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api")
@Api(tags = "备案图片管理")
public class RecordImageController {

    @Autowired
    private RecordImageService recordImageService;

    @PostMapping("/upload")
    @ApiOperation(value="上传图片", notes="上传图片")
    @ApiImplicitParam(name = "fileUpload", value = "上传图片", required = true, dataType = "")
    @ResponseBody
    public ApiResult<String> upload(@RequestParam("fileUpload") MultipartFile fileUpload) {
        try {
            if (fileUpload.isEmpty()) {
                throw new CustomException(CustomResultEnum.NO_DOCUMENTS.getCode(), CustomResultEnum.NO_DOCUMENTS.getMsg());
            }
            return new ApiResult<>(CustomResultEnum.SUCCESS.getCode(), CustomResultEnum.SUCCESS.getMsg(), recordImageService.upload(fileUpload));
        } catch (CustomException e){
            throw new BusinessException(e.getCode(), e.getMsg());
        }
    }

    @PostMapping("/updatePortraitUserAccount")
    @ApiOperation(value="更新图像", notes="更新图像")
    @ApiImplicitParam(name = "updatePortraitUserAccountVO", value = "用户注册实体类", required = true, dataType = "UpdatePortraitUserAccountVO")
    public ApiResult<Void> updatePortraitUserAccount(@Valid @RequestBody UpdateRecordImageAccountVO updatePortraitUserAccountVO){
        try {
            recordImageService.updatePortraitUserAccount(updatePortraitUserAccountVO);
            return new ApiResult<>(CustomResultEnum.SUCCESS.getCode(), CustomResultEnum.SUCCESS.getMsg(),null);
        } catch (CustomException e){
            throw new BusinessException(e.getCode(), e.getMsg());
        }
    }
}
