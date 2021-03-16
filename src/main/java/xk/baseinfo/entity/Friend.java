package xk.baseinfo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "base_friend")
public class Friend extends BaseEntity {

    /**
     * 用户账户编码
     */
    private String userAccountCode;

    /**
     * 好友分组编码
     */
    private String friendGroupCode;

    /**
     * 好友分组名称
     */
    private String friendGroupName;

    /**
     * 好友用户账户编码
     */
    private String friendCode;

    /**
     * 好友
     */
    private String friendNickname;

    /**
     * 好友图像
     */
    private String friendHeadPortraitUrl;

    /**
     * 在线
     */
    private String online;

    /**
     * 签名
     */
    private String friendSigner;

    /**
     * 好友备注
     */
    private String friendRemark;
}
