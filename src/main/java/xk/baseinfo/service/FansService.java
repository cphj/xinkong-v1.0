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
import xk.baseinfo.bo.FansBO;
import xk.baseinfo.enmu.BusinessResultEnum;
import xk.baseinfo.enmu.CustomResultEnum;
import xk.baseinfo.enmu.TureFalseEnum;
import xk.baseinfo.entity.EachOtherFollow;
import xk.baseinfo.entity.Fans;
import xk.baseinfo.entity.Follow;
import xk.baseinfo.entity.UserAccount;
import xk.baseinfo.mapper.FansMapper;
import xk.baseinfo.vo.DeleteFansVO;
import xk.baseinfo.vo.PageFansVO;
import xk.common.exception.BusinessException;
import xk.common.exception.CustomException;
import xk.common.utils.BeanUtils;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class FansService extends ServiceImpl<FansMapper, Fans> implements BaseService<Fans> {

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private FollowService followService;

    @Autowired
    private EachOtherFollowService eachOtherFollowService;

    public IPage<FansBO> listFans(PageFansVO pageFansVO) throws CustomException {
        //校验用户账户是否合法
        UserAccount userAccount = userAccountService.getUserAccountByCode(pageFansVO.getUserAccountCode());

        IPage<Fans> page = new Page<>(pageFansVO.getPageNo(), pageFansVO.getPageSize());
        LambdaQueryWrapper<Fans> wrapper =
                new QueryWrapper<Fans>().lambda()
                        .eq(Fans::getUserAccountCode, userAccount.getCode())
                        .eq(Fans::isDeleted, TureFalseEnum.FALSE_VALUE.isValue())
                        .eq(Fans::isEnable, TureFalseEnum.FALSE_VALUE.isValue());
        IPage<Fans> fansPage = this.page(page, wrapper);
        IPage fansBOPage = BeanUtils.transfrom(fansPage, IPage.class);
        if(CollectionUtils.isNotEmpty(fansPage.getRecords())) {
            List<FansBO> records = BeanUtils.transfromList(fansPage.getRecords(), FansBO.class);
            fansBOPage.setRecords(records);
        }

        return fansBOPage;
    }

    public void deleteFans(DeleteFansVO deleteFansVO) throws CustomException {
        validUserAccount(deleteFansVO.getUserAccountCode(), deleteFansVO.getFansCode());
        //删除粉丝
        updateFans(deleteFansVO);

        //关注表要删除关注
        deleteFollow(deleteFansVO);

        //判断互关
        updateEachOtherFollow(deleteFansVO.getUserAccountCode(), deleteFansVO.getFansCode());
    }

    public void validUserAccount(String userAccountCcode, String fanCode) {
        //校验用户账户是否合法
        try {
            userAccountService.getUserAccountByCode(userAccountCcode);
        }catch (CustomException e){
            throw new CustomException(e.getCode(),e.getMsg());
        }

        try{
            userAccountService.getUserAccountByCode(fanCode);
        }catch (CustomException e){
            throw new BusinessException(BusinessResultEnum.FOLLOW_ACCOUNT_ERROR.getCode(),BusinessResultEnum.FOLLOW_ACCOUNT_ERROR.getMsg());
        }
    }

    public void updateEachOtherFollow(String userAccountCode, String fansCode) throws CustomException {
        EachOtherFollow one = getEachOtherFollow(userAccountCode, fansCode);
        if(Objects.nonNull(one)) {
            one.setDeleted(TureFalseEnum.TURE_VALUE.isValue());
            eachOtherFollowService.updateById(one);
        }
        EachOtherFollow two = getEachOtherFollow(fansCode, userAccountCode);
        if(Objects.nonNull(two)) {
            one.setDeleted(TureFalseEnum.TURE_VALUE.isValue());
            eachOtherFollowService.updateById(two);
        }
    }

    private EachOtherFollow getEachOtherFollow(String userAccountcode, String fansCode) {
        LambdaQueryWrapper<EachOtherFollow> wrapper =
                new QueryWrapper<EachOtherFollow>().lambda()
                        .eq(EachOtherFollow::getEachUserCode, userAccountcode)
                        .eq(EachOtherFollow::getUserAccountCode, fansCode);
        return eachOtherFollowService.getOne(wrapper);
    }

    private void updateFans(DeleteFansVO deleteFansVO) throws CustomException {
        LambdaQueryWrapper<Fans> wrapper =
                new QueryWrapper<Fans>().lambda()
                        .eq(Fans::getUserAccountCode, deleteFansVO.getUserAccountCode())
                        .eq(Fans::getFansCode,deleteFansVO.getFansCode());
        Fans fans = this.getOne(wrapper);
        if(Objects.isNull(fans)){
            throw new CustomException(CustomResultEnum.NO_FANS.getCode(),CustomResultEnum.NO_FANS.getMsg());
        }

        fans.setDeleted(TureFalseEnum.TURE_VALUE.isValue());
        this.updateById(fans);
    }

    private void deleteFollow(DeleteFansVO deleteFansVO) throws CustomException{
        LambdaQueryWrapper<Follow> wrapper =
                new QueryWrapper<Follow>().lambda()
                        .eq(Follow::getUserAccountCode, deleteFansVO.getFansCode())
                        .eq(Follow::getFollowCode,deleteFansVO.getUserAccountCode());
        Follow follow = followService.getOne(wrapper);
        if(Objects.isNull(follow)){
            throw new CustomException(CustomResultEnum.NO_FOLLOW.getCode(),CustomResultEnum.NO_FOLLOW.getMsg());
        }

        follow.setDeleted(TureFalseEnum.TURE_VALUE.isValue());
        followService.updateById(follow);
    }

}
