package xk.baseinfo.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xk.baseinfo.bo.EachOtherFollowBO;
import xk.baseinfo.bo.FansBO;
import xk.baseinfo.enmu.CustomResultEnum;
import xk.baseinfo.enmu.TureFalseEnum;
import xk.baseinfo.entity.EachOtherFollow;
import xk.baseinfo.entity.Fans;
import xk.baseinfo.entity.UserAccount;
import xk.baseinfo.mapper.EachOtherFollowMapper;
import xk.baseinfo.vo.CancelEachOtherFollowVO;
import xk.baseinfo.vo.PageEachOtherFollowVO;
import xk.common.exception.CustomException;
import xk.common.utils.BeanUtils;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class EachOtherFollowService extends ServiceImpl<EachOtherFollowMapper, EachOtherFollow> implements BaseService<EachOtherFollow> {

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private FansService fansService;

    @Autowired
    private FollowService followService;

    public IPage<EachOtherFollowBO> listEachOtherFollow(PageEachOtherFollowVO pageEachOtherFollowVO) throws CustomException {
        //校验用户账户是否合法
        UserAccount userAccount = userAccountService.getUserAccountByCode(pageEachOtherFollowVO.getUserAccountCode());

        IPage<EachOtherFollow> page = new Page<>(pageEachOtherFollowVO.getPageNo(), pageEachOtherFollowVO.getPageSize());
        LambdaQueryWrapper<EachOtherFollow> wrapper =
                new QueryWrapper<EachOtherFollow>().lambda()
                        .eq(EachOtherFollow::getUserAccountCode, userAccount.getCode())
                        .eq(EachOtherFollow::isDeleted, TureFalseEnum.FALSE_VALUE.isValue())
                        .eq(EachOtherFollow::isEnable, TureFalseEnum.FALSE_VALUE.isValue());
        IPage<EachOtherFollow> fansPage = this.page(page, wrapper);
        IPage fansBOPage = BeanUtils.transfrom(fansPage, IPage.class);
        if(CollectionUtils.isNotEmpty(fansPage.getRecords())) {
            List<EachOtherFollowBO> records = BeanUtils.transfromList(fansPage.getRecords(), EachOtherFollowBO.class);
            fansBOPage.setRecords(records);
        }

        return fansBOPage;
    }

    public void cancelEachOtherFollow(CancelEachOtherFollowVO cancelEachOtherFollowVO) throws CustomException {
        //校验用户
        fansService.validUserAccount(cancelEachOtherFollowVO.getUserAccountCode(), cancelEachOtherFollowVO.getEachUserCode());
        //删除粉丝用户
        followService.deleteFans(cancelEachOtherFollowVO.getUserAccountCode(),cancelEachOtherFollowVO.getEachUserCode());
        //删除关注
        followService.deleteFollow(cancelEachOtherFollowVO.getUserAccountCode(), cancelEachOtherFollowVO.getEachUserCode());

        deleteEachOtherFollow(cancelEachOtherFollowVO.getUserAccountCode(), cancelEachOtherFollowVO.getEachUserCode());
        deleteEachOtherFollow(cancelEachOtherFollowVO.getEachUserCode(), cancelEachOtherFollowVO.getUserAccountCode());
    }

    private void deleteEachOtherFollow(String userAccountCode, String eachUserCode){
        LambdaQueryWrapper<EachOtherFollow> wrapper =
                new QueryWrapper<EachOtherFollow>().lambda()
                .eq(EachOtherFollow::getUserAccountCode, userAccountCode)
                .eq(EachOtherFollow::getEachUserCode, eachUserCode);
        EachOtherFollow one = this.getOne(wrapper);
        if(Objects.isNull(one)){
            throw new CustomException(CustomResultEnum.NO_EACH_OTHER_FOLLOW.getCode(),CustomResultEnum.NO_EACH_OTHER_FOLLOW.getMsg());
        }
        one.setDeleted(TureFalseEnum.TURE_VALUE.isValue());
        this.updateById(one);
    }
}
