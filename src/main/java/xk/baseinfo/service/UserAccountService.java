package xk.baseinfo.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xk.baseinfo.enmu.CustomResultEnum;
import xk.baseinfo.bo.UserAccountBO;
import xk.baseinfo.enmu.TureFalseEnum;
import xk.baseinfo.enmu.UserStautsEnum;
import xk.baseinfo.entity.UserAccount;
import xk.baseinfo.entity.UserInfo;
import xk.baseinfo.mapper.UserAccountMapper;
import xk.baseinfo.vo.*;
import xk.common.exception.CustomException;
import xk.common.utils.BeanUtils;
import xk.common.utils.IDUtils;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class UserAccountService extends ServiceImpl<UserAccountMapper, UserAccount> implements BaseService<UserAccount>{

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private RecordImageService recordImageService;

    public UserAccountBO getUserAccount(UserAccountVO userAccountVO) throws CustomException {
        UserAccount userAccount =  getUserAccountSecurity(userAccountVO);
        return BeanUtils.transfrom(userAccount, UserAccountBO.class);
    }

    //获取用户账户 提供给认证
    public UserAccount getUserAccountSecurity(UserAccountVO userAccountVO) throws CustomException{
            LambdaQueryWrapper<UserAccount> wrapper = new LambdaQueryWrapper<UserAccount>();
            wrapper.eq(StringUtils.isNotBlank(userAccountVO.getMobile()), UserAccount::getMobile, userAccountVO.getMobile());
            List<UserAccount> models = this.list(wrapper);

            UserAccount userAccount = getUserAccount(models);

            validUserAccount(userAccount);

            return userAccount;
    }

    private void validUserAccount(UserAccount userAccount) throws CustomException{
        //是否删除
        if(userAccount.isDeleted()){
            throw new CustomException(CustomResultEnum.DELETED.getCode(), CustomResultEnum.DELETED.getMsg());
        }

        //是否禁用
        if(userAccount.isEnable()){
            throw new CustomException(CustomResultEnum.ENABLE.getCode(), CustomResultEnum.ENABLE.getMsg());
        }

        //冻结
        if(Objects.equals(userAccount.getStatus(), UserStautsEnum.FREEZE.getCode())){
            throw new CustomException(CustomResultEnum.FREEZE.getCode(), CustomResultEnum.FREEZE.getMsg());
        }

        //封号
        if(Objects.equals(userAccount.getStatus(), UserStautsEnum.TITLE.getCode())){
            throw new CustomException(CustomResultEnum.TITLE.getCode(), CustomResultEnum.TITLE.getMsg());
        }

        //注销
        if(Objects.equals(userAccount.getStatus(), UserStautsEnum.CANCELLATION.getCode())){
            throw new CustomException(CustomResultEnum.CANCELLATION.getCode(), CustomResultEnum.CANCELLATION.getMsg());
        }
    }

    //注册
    public UserAccountBO registerUserAccount(RegisterUserAccountVO registerUserAccountVO) throws CustomException {
        LambdaQueryWrapper<UserAccount> wrapper = new LambdaQueryWrapper<UserAccount>();
        wrapper.eq(StringUtils.isNotBlank(registerUserAccountVO.getMobile()), UserAccount::getMobile, registerUserAccountVO.getMobile());
        List<UserAccount> models = this.list(wrapper);
        //手机号已经注册
        if(CollectionUtils.isNotEmpty(models)){
            throw new CustomException(CustomResultEnum.REGISTER.getCode(), CustomResultEnum.REGISTER.getMsg());
        }
        UserAccount userAccount = BeanUtils.transfrom(registerUserAccountVO, UserAccount.class);

        userAccount.setXkId("xk001");
        //加密
        String encode = passwordEncoder.encode(userAccount.getPassword());
        userAccount.setPassword(encode);
        //默认状态 正常 1
        userAccount.setStatus(UserStautsEnum.NORMAL.getCode());
        //code
        userAccount.setCode(IDUtils.getGeneratID());
        //delete
        userAccount.setDeleted(TureFalseEnum.FALSE_VALUE.isValue());
        //enable
        userAccount.setEnable(TureFalseEnum.FALSE_VALUE.isValue());
        this.save(userAccount);
        //保存图像
        if(StringUtils.isNotBlank(registerUserAccountVO.getImageUrl())) {
            recordImageService.saveRecordImage(userAccount);
        }

        UserAccountBO userAccountBO = BeanUtils.transfrom(userAccount, UserAccountBO.class);
        return userAccountBO;
    }

    @Transactional
    public UserAccountBO freezeUserAccount(@Valid FreezeUserAccountVO freezeUserAccountVO) throws CustomException {
        LambdaQueryWrapper<UserAccount> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(freezeUserAccountVO.getMobile()), UserAccount::getMobile, freezeUserAccountVO.getMobile());
        List<UserAccount> models = this.list(wrapper);
        //获得用户账户
        UserAccount userAccount = getUserAccount(models);

        //校验当前状态
        if(!Objects.equals(userAccount.getStatus(),UserStautsEnum.NORMAL.getCode())){
            throw new CustomException(CustomResultEnum.USERACCOUNT_STATUS_ERROR.getCode(), CustomResultEnum.USERACCOUNT_STATUS_ERROR.getMsg());
        }

        //密码校验
        if (!passwordEncoder.matches(freezeUserAccountVO.getPassword(), userAccount.getPassword())) {
            throw new CustomException(CustomResultEnum.PASSWORD_ERROR.getCode(), CustomResultEnum.PASSWORD_ERROR.getMsg());
        }

        //心空ID校验
        if(Objects.equals(freezeUserAccountVO.getXkId(),userAccount.getXkId())){
            throw new CustomException(CustomResultEnum.XKID_ERROR.getCode(), CustomResultEnum.XKID_ERROR.getMsg());
        }

        //用户信息校验
        validUserInfo(userAccount, freezeUserAccountVO.getIdCard(), freezeUserAccountVO.getUsername());
        //更新状态
        userAccount.setStatus(UserStautsEnum.FREEZE.getCode());
        this.updateById(userAccount);
        return BeanUtils.transfrom(userAccount, UserAccountBO.class);
    }

    @Transactional
    public UserAccountBO thawUserAccount(ThawUserAccountVO thawUserAccountVO) throws CustomException {
        LambdaQueryWrapper<UserAccount> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(thawUserAccountVO.getMobileOrxkId()), UserAccount::getMobile, thawUserAccountVO.getMobileOrxkId())
        .eq(StringUtils.isNotBlank(thawUserAccountVO.getMobileOrxkId()),UserAccount::getXkId,thawUserAccountVO.getMobileOrxkId());
        List<UserAccount> models = this.list(wrapper);
        //获得用户账户
        UserAccount userAccount = getUserAccount(models);

        //校验当前状态
        if(!Objects.equals(userAccount.getStatus(),UserStautsEnum.FREEZE.getCode())){
            throw new CustomException(CustomResultEnum.STATUS_ERROR.getCode(), CustomResultEnum.STATUS_ERROR.getMsg());
        }

        //密码校验
        if (!passwordEncoder.matches(thawUserAccountVO.getPassword(), userAccount.getPassword())) {
            throw new CustomException(CustomResultEnum.PASSWORD_ERROR.getCode(), CustomResultEnum.PASSWORD_ERROR.getMsg());
        }

        //用户信息校验
        validUserInfo(userAccount, thawUserAccountVO.getIdCard(), thawUserAccountVO.getUsername());
        //更新状态
        userAccount.setStatus(UserStautsEnum.NORMAL.getCode());
        this.updateById(userAccount);

        return BeanUtils.transfrom(userAccount,UserAccountBO.class);
    }

    private UserAccount getUserAccount(List<UserAccount> models) throws CustomException {
        //用户账户查询失败
        if (CollectionUtils.isEmpty(models)) {
            throw new CustomException(CustomResultEnum.FAIL.getCode(), CustomResultEnum.FAIL.getMsg());
        }
        //查询结果大于1
        if (CollectionUtils.isNotEmpty(models) && models.size() > 1) {
            throw new CustomException(CustomResultEnum.MULTIPLE_DATA.getCode(), CustomResultEnum.MULTIPLE_DATA.getMsg());
        }

        return models.get(0);
    }

    //校验用户信息数据
    @Transactional
    public void validUserInfo(UserAccount userAccount, String idCard, String username) throws CustomException {
        LambdaQueryWrapper<UserInfo> wr = new LambdaQueryWrapper<>();
        wr.eq(StringUtils.isNotBlank(userAccount.getCode()), UserInfo::getCode, userAccount.getCode());
        List<UserInfo> infos = userInfoService.list(wr);
        //用户信息查询失败
        if (CollectionUtils.isEmpty(infos)) {
            throw new CustomException(CustomResultEnum.INFO_QUERY_ERROR.getCode(), CustomResultEnum.INFO_QUERY_ERROR.getMsg());
        }
        //查询结果大于1
        if (CollectionUtils.isNotEmpty(infos) && infos.size() > 1) {
            throw new CustomException(CustomResultEnum.MULTIPLE_DATA.getCode(), CustomResultEnum.MULTIPLE_DATA.getMsg());
        }
        UserInfo userInfo = infos.get(0);

        //身份证校验
        if (Objects.equals(userInfo.getIdCard(), idCard)) {
            throw new CustomException(CustomResultEnum.IDCARD_VALID.getCode(), CustomResultEnum.IDCARD_VALID.getMsg());
        }

        //用户名校验
        if (Objects.equals(userInfo.getUsername(), username)) {
            throw new CustomException(CustomResultEnum.USERNAME_FAIL.getCode(), CustomResultEnum.USERNAME_FAIL.getMsg());
        }
    }

    @Transactional
    public UserAccountBO titleUserAccount(TitleUserAccountVO titleUserAccountVO) throws CustomException {
        LambdaQueryWrapper<UserAccount> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(titleUserAccountVO.getMobile()), UserAccount::getMobile, titleUserAccountVO.getMobile());
        List<UserAccount> models = this.list(wrapper);
        //获得用户账户
        UserAccount userAccount = getUserAccount(models);

        //判断当前状态 为封号状态
        if(Objects.equals(userAccount.getStatus(), UserStautsEnum.TITLE.getCode())){
            throw new CustomException(CustomResultEnum.STATUS_ERROR.getCode(), CustomResultEnum.STATUS_ERROR.getMsg());
        }

        //密码校验
        if (!passwordEncoder.matches(titleUserAccountVO.getPassword(), userAccount.getPassword())) {
            throw new CustomException(CustomResultEnum.PASSWORD_ERROR.getCode(), CustomResultEnum.PASSWORD_ERROR.getMsg());
        }

        //校验用户信息
        validUserInfo(userAccount,titleUserAccountVO.getIdCard(),titleUserAccountVO.getUsername());

        userAccount.setStatus(UserStautsEnum.NORMAL.getCode());
        this.updateById(userAccount);

        return BeanUtils.transfrom(userAccount, UserAccountBO.class);
    }

    @Transactional
    public UserAccountBO cancellationUserAccount(CancellationUserAccountVO cancellationUserAccountVO) throws CustomException {
        LambdaQueryWrapper<UserAccount> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(cancellationUserAccountVO.getMobile()), UserAccount::getMobile, cancellationUserAccountVO.getMobile());
        List<UserAccount> models = this.list(wrapper);
        //获得用户账户
        UserAccount userAccount = getUserAccount(models);
        if(Objects.equals(userAccount.getStatus(),UserStautsEnum.CANCELLATION.getCode())){
            throw new CustomException(CustomResultEnum.CANCELLATION.getCode(), CustomResultEnum.CANCELLATION.getMsg());
        }
        userAccount.setStatus(UserStautsEnum.CANCELLATION.getCode());
        this.updateById(userAccount);

        return BeanUtils.transfrom(userAccount, UserAccountBO.class);
    }

    @Transactional
    public UserAccountBO retrievePasswordUserAccount(RetrievePasswordUserAccountVO retrievePasswordUserAccountVO) throws CustomException {
        LambdaQueryWrapper<UserAccount> wrapper = new LambdaQueryWrapper<UserAccount>();
        wrapper.eq(StringUtils.isNotBlank(retrievePasswordUserAccountVO.getMobile()), UserAccount::getMobile, retrievePasswordUserAccountVO.getMobile());
        List<UserAccount> models = this.list(wrapper);

        //获得用户账户
        UserAccount userAccount = getUserAccount(models);
        if(!Objects.equals(userAccount.getXkId(), retrievePasswordUserAccountVO.getXkId())){
            throw new CustomException(CustomResultEnum.XKID_ERROR.getCode(), CustomResultEnum.XKID_ERROR.getMsg());
        }

        //加密
        String encode = passwordEncoder.encode(retrievePasswordUserAccountVO.getPassword());
        userAccount.setPassword(encode);
        this.updateById(userAccount);

        return BeanUtils.transfrom(userAccount, UserAccountBO.class);
    }

    public UserAccount getUserAccountByCode(String userAccountCode) throws CustomException {
        LambdaQueryWrapper<UserAccount> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserAccount::getCode, userAccountCode);
        List<UserAccount> models = this.list(wrapper);
        UserAccount userAccount = getUserAccount(models);
        validUserAccount(userAccount);
        return userAccount;
    }
}
