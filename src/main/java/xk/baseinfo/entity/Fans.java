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
@TableName(value = "base_fans")
public class Fans extends BaseEntity {

    /**
     * 编码
     */
    private String code;

    /**
     * 用户账户编码
     */
    private String userAccountCode;

    /**
     * 粉丝编码（粉丝用户账户编码）
     */
    private String fansCode;

    /**
     * 粉丝昵称
     */
    private String fansNickname;

}
