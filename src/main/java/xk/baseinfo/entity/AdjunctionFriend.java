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
@TableName(value = "base_adjunction_friend")
public class AdjunctionFriend extends BaseEntity {

    /**
     * 用户账户编码
     */
    private String userAccountCode;

    /**
     * 好友用户账户编码
     */
    private String friendCode;

    /**
     * 好友昵称
     */
    private String friendNickname;

    /**
     * 添加好友状态
     */
    private Integer status;
}
