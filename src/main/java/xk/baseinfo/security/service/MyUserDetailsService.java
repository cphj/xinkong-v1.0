package xk.baseinfo.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import xk.baseinfo.entity.UserAccount;
import xk.baseinfo.security.entity.JwtUserDetails;
import xk.baseinfo.service.UserAccountService;
import xk.baseinfo.vo.UserAccountVO;
import xk.common.utils.BeanUtils;

@Component
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserAccountService userAccountService;

    @Override
    public JwtUserDetails loadUserByUsername(String mobile) throws UsernameNotFoundException {
        UserAccountVO userAccountVO = new UserAccountVO();
        userAccountVO.setMobile(mobile);
        UserAccount userAccount = userAccountService.getUserAccountSecurity(userAccountVO);
//        BeanUtils.copyProperties(userAccount, jwtUserDetails);
        return BeanUtils.transfrom(userAccount, JwtUserDetails.class);
    }
}
