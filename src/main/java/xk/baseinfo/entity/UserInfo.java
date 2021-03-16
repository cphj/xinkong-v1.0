package xk.baseinfo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.Instant;

@Data
@Accessors
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "base_user_info")
public class UserInfo extends BaseEntity {
    /**
     * 用户账号编码
     */
    private String usrAccountCode;
    /**
     * 性别 1 男 2 女
     */
    private Integer gender;
    /**
     * 身份证号
     */
    private String idCard;
    /**
     * 用户名
     */
    private String username;
    /**
     * 生日
     */
    private Instant birthday;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 昵称用指定编码表示
     */
    private String nicknameLetter;
    /**
     * 兴趣爱好
     */
    private String hobby;
    /**
     * 地址
     */
    private String address;
}
