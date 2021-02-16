package com.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pojo.User;

public interface UserMapper extends BaseMapper<User>
{
    User findUserByName(User user);

}
