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
@TableName(value = "base_each_other_follow")
public class EachOtherFollow extends BaseEntity {

    /**
     * 用户账户编码
     */
    private String userAccountCode;

    /**
     * 被关注人编码
     */
    private String eachUserCode;

    /**
     * 被关注人姓名
     */
    private String eachUserNickname;
}
