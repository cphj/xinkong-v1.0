package xk.baseinfo.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xk.baseinfo.bo.FriendGroupBO;
import xk.baseinfo.enmu.CustomResultEnum;
import xk.baseinfo.enmu.TureFalseEnum;
import xk.baseinfo.entity.FriendGroup;
import xk.baseinfo.entity.UserAccount;
import xk.baseinfo.mapper.FriendGroupMapper;
import xk.baseinfo.vo.DeleteFriendGroupVO;
import xk.baseinfo.vo.FriendGroupVO;
import xk.baseinfo.vo.ListFriendGroupVO;
import xk.baseinfo.vo.UpdateFriendGroupVO;
import xk.common.exception.CustomException;
import xk.common.utils.BeanUtils;
import xk.common.utils.IDUtils;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class FriendGroupService extends ServiceImpl<FriendGroupMapper, FriendGroup> implements BaseService<FriendGroup> {

    @Autowired
    private UserAccountService userAccountService;

    @Transactional
    public FriendGroupBO addFriendGroup(FriendGroupVO friendGroupVO) throws CustomException {
        //校验
        userAccountService.getUserAccountByCode(friendGroupVO.getCreateUserAccountCode());

        FriendGroup friendGroup = new FriendGroup();
        //分组名
        friendGroup.setFriendGroupName(friendGroupVO.getFriendGroupName());
        friendGroup.setCode(IDUtils.getGeneratID());
        friendGroup.setDeleted(TureFalseEnum.FALSE_VALUE.isValue());
        friendGroup.setEnable(TureFalseEnum.FALSE_VALUE.isValue());
        save(friendGroup);
        return BeanUtils.transfrom(friendGroup, FriendGroupBO.class);
    }

    @Transactional
    public FriendGroupBO updateFriendGroup(UpdateFriendGroupVO updateFriendGroupVO) throws CustomException {
        LambdaQueryWrapper<FriendGroup> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(updateFriendGroupVO.getCode()),FriendGroup::getCode,updateFriendGroupVO.getCode());
        FriendGroup friendGroup = this.getOne(wrapper);
        //好友查询失败
        if(Objects.isNull(friendGroup)) {
            throw new CustomException(CustomResultEnum.FRIEND_GROUP_QUERY_FAIL.getCode(), CustomResultEnum.FRIEND_GROUP_QUERY_FAIL.getMsg());
        }

        if(Objects.equals(friendGroup.isDeleted(), TureFalseEnum.TURE_VALUE.isValue())){
            throw new CustomException(CustomResultEnum.FRIEND_GROUP_DELETED.getCode(), CustomResultEnum.FRIEND_GROUP_DELETED.getMsg());
        }

        friendGroup.setFriendGroupName(updateFriendGroupVO.getFriendGroupName());
        this.updateById(friendGroup);
        return BeanUtils.transfrom(friendGroup, FriendGroupBO.class);
    }

    @Transactional
    public void deleteFriendGroup(DeleteFriendGroupVO deleteFriendGroupVO) throws CustomException {
        LambdaQueryWrapper<FriendGroup> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FriendGroup::getCode,deleteFriendGroupVO.getCode());
        FriendGroup friendGroup = this.getOne(wrapper);
       //好友查询失败
        if(Objects.isNull(friendGroup)) {
            throw new CustomException(CustomResultEnum.FRIEND_GROUP_QUERY_FAIL.getCode(), CustomResultEnum.FRIEND_GROUP_QUERY_FAIL.getMsg());
        }

        friendGroup.setDeleted(TureFalseEnum.TURE_VALUE.isValue());
        //TODO 删除好友分组后，该分组好友转入默认分组

        this.updateById(friendGroup);
    }

    public List<FriendGroupBO> listFriendGroup(ListFriendGroupVO listFriendGroupVO) {
        //校验
        userAccountService.getUserAccountByCode(listFriendGroupVO.getUserAccountCode());

        LambdaQueryWrapper<FriendGroup> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FriendGroup::getCreateUserAccountCode,listFriendGroupVO.getUserAccountCode());
        List<FriendGroup> friendGroups = this.list(wrapper);
        return BeanUtils.transfromList(friendGroups, FriendGroupBO.class);
    }
}
