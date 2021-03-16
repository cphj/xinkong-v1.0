package xk.baseinfo.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xk.baseinfo.bo.UserInfoBO;
import xk.baseinfo.enmu.CustomResultEnum;
import xk.baseinfo.entity.UserInfo;
import xk.baseinfo.mapper.UserInfoMapper;
import xk.baseinfo.vo.UserInfoVO;
import xk.common.exception.CustomException;
import xk.common.utils.BeanUtils;

import java.util.List;

@Slf4j
@Service
public class UserInfoService extends ServiceImpl<UserInfoMapper, UserInfo> implements BaseService<UserInfo> {

    @Transactional
    public UserInfoBO addUserInfo(UserInfoVO userInfoVO) throws CustomException {
        LambdaQueryWrapper<UserInfo> wrapper = new LambdaQueryWrapper<UserInfo>();
        wrapper.eq(StringUtils.isNotBlank(userInfoVO.getIdCard()), UserInfo::getIdCard, userInfoVO.getIdCard())
                .eq(StringUtils.isNotBlank(userInfoVO.getUserAccountCode()), UserInfo::getUsrAccountCode, userInfoVO.getUserAccountCode());
        List<UserInfo> models = this.list(wrapper);
        if (CollectionUtils.isNotEmpty(models)) {
            throw new CustomException(CustomResultEnum.IDCARD_REPEAT.getCode(), CustomResultEnum.IDCARD_REPEAT.getMsg());
        }
        UserInfo userInfo = BeanUtils.transfrom(userInfoVO, UserInfo.class);
        save(userInfo);
        UserInfoBO userInfoBO = BeanUtils.transfrom(userInfo, UserInfoBO.class);
        return userInfoBO;
    }

}
