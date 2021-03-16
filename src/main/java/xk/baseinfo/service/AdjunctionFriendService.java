package xk.baseinfo.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xk.baseinfo.entity.AdjunctionFriend;
import xk.baseinfo.mapper.AdjunctionFriendMapper;


@Slf4j
@Service
public class AdjunctionFriendService extends ServiceImpl<AdjunctionFriendMapper, AdjunctionFriend> implements BaseService<AdjunctionFriend>{
}
