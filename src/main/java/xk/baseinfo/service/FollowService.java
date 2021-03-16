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
import org.springframework.transaction.annotation.Transactional;
import xk.baseinfo.bo.FollowBO;
import xk.baseinfo.enmu.BusinessResultEnum;
import xk.baseinfo.enmu.CustomResultEnum;
import xk.baseinfo.enmu.TureFalseEnum;
import xk.baseinfo.entity.EachOtherFollow;
import xk.baseinfo.entity.Fans;
import xk.baseinfo.entity.Follow;
import xk.baseinfo.entity.UserAccount;
import xk.baseinfo.mapper.FollowMapper;
import xk.baseinfo.vo.CancelFollowVO;
import xk.baseinfo.vo.FollowVO;
import xk.baseinfo.vo.PageFollowVO;
import xk.common.exception.BusinessException;
import xk.common.exception.CustomException;
import xk.common.utils.BeanUtils;
import xk.common.utils.IDUtils;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class FollowService extends ServiceImpl<FollowMapper, Follow> implements BaseService<Follow> {

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private FansService fansService;

    @Autowired
    private EachOtherFollowService eachOtherFollowService;

    @Transactional
    public FollowBO addFollow(FollowVO followVO) throws CustomException {

        fansService.validUserAccount(followVO.getUserAccountCode(), followVO.getFollowCode());

        //校验当前用户和被关注用户
        Follow follow = getFollow(followVO.getUserAccountCode(), followVO.getFollowCode());
        if(Objects.nonNull(follow)){
            follow.setDeleted(TureFalseEnum.FALSE_VALUE.isValue());
            this.updateById(follow);
        }else {
            //保存到关注表
            follow = saveFollow(followVO);
        }

        //保存到粉丝表
        Fans fans = getFans(followVO.getFollowCode(), followVO.getUserAccountCode());
        if(Objects.nonNull(fans)) {
            fans.setDeleted(TureFalseEnum.FALSE_VALUE.isValue());
            fansService.updateById(fans);
        } else {
            saveFans(followVO);
        }

        //判断是否相互关注
        Follow model = getFollow(followVO.getFollowCode(), followVO.getUserAccountCode());
        if(Objects.nonNull(model)){
            EachOtherFollow eachOther = new EachOtherFollow();

            eachOther.setDeleted(TureFalseEnum.FALSE_VALUE.isValue());
            eachOther.setEnable(TureFalseEnum.FALSE_VALUE.isValue());

            eachOther.setCode(IDUtils.getGeneratID());
            eachOther.setUserAccountCode(model.getUserAccountCode());
            eachOther.setEachUserCode(model.getFollowCode());
            eachOther.setEachUserNickname(model.getFollowNickname());
            eachOtherFollowService.save(eachOther);

            eachOther.setCode(IDUtils.getGeneratID());
            eachOther.setUserAccountCode(model.getFollowCode());
            eachOther.setEachUserCode(model.getUserAccountCode());
            eachOther.setEachUserNickname(fans.getFansNickname());
            eachOtherFollowService.save(eachOther);
        }

        return BeanUtils.transfrom(follow, FollowBO.class);
    }

    private Follow getFollow(String UserAccountCode, String followCode) {
        LambdaQueryWrapper<Follow> wrapper =
                new QueryWrapper<Follow>().lambda()
                        .eq(Follow::getUserAccountCode, UserAccountCode)
                        .eq(Follow::getFollowCode, followCode);
        return this.getOne(wrapper);
    }

    private Fans getFans(String followCode, String userAccountCode){
        LambdaQueryWrapper<Fans> wrapper =
                new QueryWrapper<Fans>().lambda()
                        .eq(Fans::getUserAccountCode, followCode)
                        .eq(Fans::getFansCode, userAccountCode);
        return fansService.getOne(wrapper);
    }

    private void saveFans(FollowVO followVO) {
        Fans fans = new Fans();
        fans.setCode(IDUtils.getGeneratID());
        fans.setFansNickname(followVO.getFollowNickname());
        fans.setFansCode(followVO.getUserAccountCode());
        fans.setUserAccountCode(followVO.getFollowCode());
        fans.setDeleted(TureFalseEnum.FALSE_VALUE.isValue());
        fans.setEnable(TureFalseEnum.FALSE_VALUE.isValue());
        fansService.save(fans);
    }

    private Follow saveFollow(FollowVO followVO) {
        Follow follow = BeanUtils.transfrom(followVO, Follow.class);
        follow.setCode(IDUtils.getGeneratID());
        follow.setDeleted(TureFalseEnum.FALSE_VALUE.isValue());
        follow.setEnable(TureFalseEnum.FALSE_VALUE.isValue());
        this.save(follow);
        return follow;
    }

    public IPage<FollowBO> listFollow(PageFollowVO pageFollowVO) throws BusinessException{
        try{
            UserAccount userAccount = userAccountService.getUserAccountByCode(pageFollowVO.getUserAccountCode());
        } catch (CustomException e){
            log.info("用户账户 code {}， msg {}", e.getCode(), e.getMsg());
            throw new BusinessException(e.getCode(), e.getMsg());
        }

        IPage<Follow> page = new Page<Follow>(pageFollowVO.getPageNo(), pageFollowVO.getPageSize());

        LambdaQueryWrapper<Follow> wrapper =
                new QueryWrapper<Follow>().lambda()
                        .eq(Follow::getUserAccountCode, pageFollowVO.getUserAccountCode());
        IPage<Follow> followPage = this.page(page, wrapper);

        IPage followBOPage = BeanUtils.transfrom(followPage, IPage.class);
        if(CollectionUtils.isNotEmpty(followPage.getRecords())){
            List<FollowBO> records = BeanUtils.transfromList(followPage.getRecords(), FollowBO.class);
            followBOPage.setRecords(records);
        }
        return followBOPage;
    }

    @Transactional
    public void cancelFollow(CancelFollowVO cancelFollowVO) throws CustomException {
        try{
            UserAccount userAccount = userAccountService.getUserAccountByCode(cancelFollowVO.getUserAccountCode());
        } catch (CustomException e){
            log.info("用户账户 code {}， msg {}", e.getCode(), e.getMsg());
            throw new BusinessException(e.getCode(), e.getMsg());
        }
        //删除关注
        deleteFollow(cancelFollowVO.getUserAccountCode(),cancelFollowVO.getFollowCode());

        //删除粉丝
        deleteFans(cancelFollowVO.getUserAccountCode(),cancelFollowVO.getFollowCode());

        //删除互关
        fansService.updateEachOtherFollow(cancelFollowVO.getUserAccountCode(),cancelFollowVO.getFollowCode());
    }

    public void deleteFans(String userAccountCode, String followCode) {
        Fans fans = getFans(followCode, userAccountCode);
        if(Objects.isNull(fans)){
            throw new CustomException(CustomResultEnum.NO_FANS.getCode(), CustomResultEnum.NO_FANS.getMsg());
        }
        fans.setDeleted(TureFalseEnum.TURE_VALUE.isValue());
        fansService.updateById(fans);
    }

    public void deleteFollow(String userAccountCode, String followCode) {
        Follow follow = getFollow(userAccountCode, followCode);
        if(Objects.isNull(follow)){
            throw new CustomException(CustomResultEnum.NO_FOLLOW.getCode(),CustomResultEnum.NO_FOLLOW.getMsg());
        }
        follow.setDeleted(TureFalseEnum.TURE_VALUE.isValue());
        this.updateById(follow);
    }
}
