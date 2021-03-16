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
@TableName(value = "base_follow")
public class Follow extends BaseEntity {

    /**
     * 用户账户编码
     */
    private String userAccountCode;

    /**
     * 关注用户账户编码
     */
    private String followCode;

    /**
     * 关注用户账户昵称
     */
    private String followNickname;
}
