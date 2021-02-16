package com.pojo;


import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_user")
public class User extends Model<User>
{
    /**
     *  因为在yml定义了global-config.db-config.id-type=auto，可以无需写以下注解
  *  @TableId(type= IdType.AUTO)
  */
    private Long id;
    @TableField(value = "name") //当DB中的列名和java中的名字不一致时可以使用此注解
    private String name;
    @TableField(fill = FieldFill.INSERT)
    private String password;
    private Integer age;
    @TableField(exist = false) //DB中没有address，但java中有此属性
    private String address;

    @TableLogic //   1代表删除，0代表未删除
    private int isDeleted;

}
