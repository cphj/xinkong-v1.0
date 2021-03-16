package xk.baseinfo.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.commons.util.IdUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import xk.baseinfo.enmu.CustomResultEnum;
import xk.baseinfo.enmu.TureFalseEnum;
import xk.baseinfo.entity.RecordImage;
import xk.baseinfo.entity.UserAccount;
import xk.baseinfo.mapper.RecordImageMapper;
import xk.baseinfo.vo.UpdateRecordImageAccountVO;
import xk.common.exception.CustomException;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
public class RecordImageService extends ServiceImpl<RecordImageMapper, RecordImage> implements BaseService<RecordImage>{

    @Autowired
    private UserAccountService userAccountService;

    //模块
    private static String MODELCODE = "user-account";

    //所需图片的功能
    private static String SHOWPATH ="user-account-register";

    @Transactional
    public void addRecordImage(RecordImage recordImage) throws CustomException {
        LambdaQueryWrapper<RecordImage> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(recordImage.getUserAccountCode()), RecordImage::getUserAccountCode,recordImage.getUserAccountCode());
        List<RecordImage> models = this.list(wrapper);
        models.stream().filter(model -> Objects.equals(model.isEnable(), TureFalseEnum.FALSE_VALUE.isValue())).forEach(model -> {
            model.setEnable(TureFalseEnum.TURE_VALUE.isValue());
            this.updateById(model);
        });
        recordImage.setCode(IdUtils.getUnresolvedServiceId());
        recordImage.setDeleted(TureFalseEnum.FALSE_VALUE.isValue());
        recordImage.setEnable(TureFalseEnum.FALSE_VALUE.isValue());
        this.save(recordImage);
    }

    public String upload(MultipartFile fileUpload) throws CustomException {
        //todo 图片大小校验
        String fileName = fileUpload.getOriginalFilename();
        if(StringUtils.isBlank(fileName)){
            throw new CustomException(CustomResultEnum.DOCUMENTS_NAME_ERROR.getCode(), CustomResultEnum.DOCUMENTS_NAME_ERROR.getMsg());
        }
        //校验文件格式
        checkFile(fileName);
        String savePath = UUID.randomUUID().toString().replaceAll("-","");
        File saveFile = new File(savePath);
        if (!saveFile.exists()) {
            saveFile.mkdir();
        }

        try {
            log.info(savePath + "/" + fileName);
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(savePath + "\\" + fileName));
            out.write(fileUpload.getBytes());
            out.flush();
            out.close();
        } catch (IOException e) {
            throw new CustomException(CustomResultEnum.UPLOAD_ERROR.getCode(), CustomResultEnum.UPLOAD_ERROR.getMsg());
        }
        return savePath + "/" + fileName;
    }

    private void checkFile(String fileName) throws CustomException {
        //设置允许上传文件类型
        String suffixList = ".jpg,.gif,.png,.ico,.bmp,.jpeg";
        // 获取文件后缀
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        if (suffixList.contains(suffix.trim().toLowerCase())) {
            throw new CustomException(CustomResultEnum.DOCUMENTS_FORMAT_ERROR.getCode(), CustomResultEnum.DOCUMENTS_FORMAT_ERROR.getMsg());
        }
    }

    @Transactional
    public void updatePortraitUserAccount(UpdateRecordImageAccountVO updatePortraitUserAccountVO) throws CustomException {
        UserAccount userAccount = userAccountService.getUserAccountByCode(updatePortraitUserAccountVO.getUserAccountCode());
        userAccount.setImageUrl(updatePortraitUserAccountVO.getImageUrl());
        userAccountService.updateById(userAccount);
        this.saveRecordImage(userAccount);
    }

    public void saveRecordImage(UserAccount userAccount) throws CustomException {
        try {
            RecordImage recordImage = new RecordImage();
            recordImage.setModelCode(MODELCODE);
            recordImage.setShowPath(SHOWPATH);
            recordImage.setImageUrl(userAccount.getImageUrl());
            recordImage.setUserAccountCode(userAccount.getCode());
            this.addRecordImage(recordImage);
        }catch (Exception e){
            throw new CustomException(CustomResultEnum.SAVE_IMAGE_ERROR.getCode(), CustomResultEnum.SAVE_IMAGE_ERROR.getMsg());
        }
    }
}
