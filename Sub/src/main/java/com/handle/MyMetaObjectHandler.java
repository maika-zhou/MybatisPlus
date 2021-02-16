package com.handle;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler
{

    //Insert时 自动填充
    @Override
    public void insertFill(MetaObject metaObject)
    {
        //如果password字段为空，设置初始密码888888
        Object password = getFieldValByName("password",metaObject);
        if(password==null)
        {
            setFieldValByName("password","888888",metaObject);
        }

    }

    //Update时 自动填充
    @Override
    public void updateFill(MetaObject metaObject)
    {

    }
}
