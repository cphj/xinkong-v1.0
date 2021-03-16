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
@TableName(value = "base_user_account")
public class FriendGroup extends BaseEntity {

    /**
     * 好友分组
     */
    private String friendGroupName;

    /**
     * 好友分组创建人编码
     */
    private String createUserAccountCode;
}
