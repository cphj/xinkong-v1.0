package xk.baseinfo.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.commons.util.IdUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xk.baseinfo.bo.AdjunctionFriendBO;
import xk.baseinfo.enmu.CustomResultEnum;
import xk.baseinfo.enmu.FriendStatusEnum;
import xk.baseinfo.enmu.TureFalseEnum;
import xk.baseinfo.entity.AdjunctionFriend;
import xk.baseinfo.entity.Friend;
import xk.baseinfo.mapper.FriendMapper;
import xk.baseinfo.vo.AcquiescenceFriendVO;
import xk.baseinfo.vo.AdjunctionFriendVO;
import xk.baseinfo.vo.DeleteFriendVO;
import xk.baseinfo.vo.RefuseFriendVO;
import xk.common.exception.CustomException;
import xk.common.utils.BeanUtils;
import xk.common.utils.IDUtils;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;


@Slf4j
@Service
public class FriendService extends ServiceImpl<FriendMapper, Friend> implements BaseService<Friend>{

    @Autowired
    private FansService fansService;

    @Autowired
    private AdjunctionFriendService adjunctionFriendService;

    @Transactional
    public AdjunctionFriendBO addFriend(AdjunctionFriendVO adjunctionFriendVO) throws CustomException {
        //校验编码用户编码和好友编码
        fansService.validUserAccount(adjunctionFriendVO.getUserAccountCode(), adjunctionFriendVO.getFriendCode());
        //多次添加 24小时只能加一次
        AdjunctionFriend adjunctionFriend = getAdjunctionFriend(adjunctionFriendVO.getUserAccountCode(), adjunctionFriendVO.getFriendCode());
        if(Objects.nonNull(adjunctionFriend)){
            if(Objects.nonNull(adjunctionFriend.getUpdateTime())){
                if (Duration.between(adjunctionFriend.getUpdateTime(), Instant.now()).toMillis() < 24 * 60) {
                    throw new CustomException(CustomResultEnum.ADD_FRIEND.getCode(), CustomResultEnum.ADD_FRIEND.getMsg());
                }
            } else {
                if (Duration.between(adjunctionFriend.getCreateTime(), Instant.now()).toMillis() < 24 * 60) {
                    throw new CustomException(CustomResultEnum.ADD_FRIEND.getCode(), CustomResultEnum.ADD_FRIEND.getMsg());
                }
            }
            adjunctionFriendService.updateById(adjunctionFriend);
            return BeanUtils.transfrom(adjunctionFriend, AdjunctionFriendBO.class);
        }

        AdjunctionFriend friend = BeanUtils.transfrom(adjunctionFriendVO, AdjunctionFriend.class);
        friend.setCode(IDUtils.getGeneratID());
        friend.setDeleted(TureFalseEnum.FALSE_VALUE.isValue());
        friend.setEnable(TureFalseEnum.FALSE_VALUE.isValue());
        friend.setStatus(FriendStatusEnum.ACQUIESCENCE.getCode());
        adjunctionFriendService.save(friend);

        return BeanUtils.transfrom(friend, AdjunctionFriendBO.class);
    }

    @Transactional
    public void acquiescenceFriend(AcquiescenceFriendVO acquiescenceFriendVO) throws CustomException {
        //校验编码用户编码和好友编码
        fansService.validUserAccount(acquiescenceFriendVO.getUserAccountCode(), acquiescenceFriendVO.getFriendCode());
        AdjunctionFriend adjunctionFriend = getAdjunctionFriend(acquiescenceFriendVO.getFriendCode(), acquiescenceFriendVO.getUserAccountCode());

        //24小时只能添加一次好友
        if(Objects.nonNull(adjunctionFriend)){
            if(Objects.nonNull(adjunctionFriend.getUpdateTime())){
                if (Duration.between(adjunctionFriend.getUpdateTime(), Instant.now()).toMillis() > 24 * 60) {
                    throw new CustomException(CustomResultEnum.BE_OVERDUE.getCode(), CustomResultEnum.BE_OVERDUE.getMsg());
                }
            } else {
                if (Duration.between(adjunctionFriend.getCreateTime(), Instant.now()).toMillis() > 24 * 60) {
                    throw new CustomException(CustomResultEnum.BE_OVERDUE.getCode(), CustomResultEnum.BE_OVERDUE.getMsg());
                }
            }
        }

        adjunctionFriend.setStatus(FriendStatusEnum.AGREE.getCode());
        adjunctionFriendService.updateById(adjunctionFriend);
        Friend friend = BeanUtils.transfrom(acquiescenceFriendVO,Friend.class);
        friend.setCode(IDUtils.getGeneratID());
        friend.setDeleted(TureFalseEnum.FALSE_VALUE.isValue());
        friend.setEnable(TureFalseEnum.FALSE_VALUE.isValue());
        save(friend);

        friend.setCode(IDUtils.getGeneratID());
        friend.setFriendCode(acquiescenceFriendVO.getUserAccountCode());
        friend.setUserAccountCode(acquiescenceFriendVO.getFriendCode());
        friend.setFriendNickname(acquiescenceFriendVO.getNickname());
//        friend.setFriendGroupCode();
//        friend.setFriendGroupName();
        save(friend);
    }

    private AdjunctionFriend getAdjunctionFriend(String friendCode, String userAccountCode) {
        LambdaQueryWrapper<AdjunctionFriend> wrapper =
                new QueryWrapper<AdjunctionFriend>().lambda()
                .eq(AdjunctionFriend::getFriendCode, userAccountCode)
                .eq(AdjunctionFriend::getUserAccountCode, friendCode);

        return adjunctionFriendService.getOne(wrapper);
    }

    @Transactional
    public AdjunctionFriendBO refuseFriend(RefuseFriendVO refuseFriendVO) throws CustomException {
        //校验编码用户编码和好友编码
        fansService.validUserAccount(refuseFriendVO.getUserAccountCode(), refuseFriendVO.getFriendCode());
        AdjunctionFriend adjunctionFriend = getAdjunctionFriend(refuseFriendVO.getFriendCode(), refuseFriendVO.getUserAccountCode());
        adjunctionFriend.setStatus(FriendStatusEnum.REFUSE.getCode());
        adjunctionFriendService.updateById(adjunctionFriend);
        return BeanUtils.transfrom(adjunctionFriend, AdjunctionFriendBO.class);
    }

    @Transactional
    public void deleteFriend(DeleteFriendVO deleteFriendVO) throws CustomException {
        //校验编码用户编码和好友编码
        fansService.validUserAccount(deleteFriendVO.getUserAccountCode(), deleteFriendVO.getFriendCode());

        Friend one = getFriend(deleteFriendVO.getUserAccountCode(), deleteFriendVO.getFriendCode());
        if(Objects.isNull(one)){
            throw new CustomException(1,"");
        }

        Friend two = getFriend(deleteFriendVO.getFriendCode(), deleteFriendVO.getUserAccountCode());
        if(Objects.isNull(two)){
            throw new CustomException(1,"");
        }
        one.setDeleted(TureFalseEnum.TURE_VALUE.isValue());
        this.updateById(one);
        two.setDeleted(TureFalseEnum.TURE_VALUE.isValue());
        this.updateById(two);
    }

    private Friend getFriend(String friendCode, String userAccountCode) throws CustomException {
        LambdaQueryWrapper<Friend> wrapper =
                new QueryWrapper<Friend>().lambda()
                .eq(Friend::getUserAccountCode, userAccountCode)
                .eq(Friend::getFriendCode, friendCode)
                .eq(Friend::isDeleted, TureFalseEnum.FALSE_VALUE.isValue())
                .eq(Friend::isEnable, TureFalseEnum.FALSE_VALUE.isValue());
        return this.getOne(wrapper);
    }
}
