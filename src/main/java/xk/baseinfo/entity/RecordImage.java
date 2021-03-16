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
@TableName(value = "base_record_image")
public class RecordImage extends BaseEntity {

    /**
     * 模块编码 该模块中有图片展示
     */
    private String modelCode;

    /**
     * 用户账户编码
     */
    private String userAccountCode;

    /**
     * 展示路径
     */
    private String showPath;

    /**
     * 图片路径
     */
    private String imageUrl;
}
