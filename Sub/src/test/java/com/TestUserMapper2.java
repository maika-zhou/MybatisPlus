package com;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestUserMapper2
{
    /**
     *  SELECT id,name,password,age FROM t_user WHERE id=?
     */
    @Test
    public void testSelectById()
    {
        User user = new User();
        user.setId(2L);
        User t = user.selectById();
    }

    /**
     *  INSERT INTO t_user ( name, password, age ) VALUES ( ?, ?, ? )
     */
    @Test
    public void testInsert()
    {
        User user = new User();
        user.setName("Leon");
        //user.setPassword("123456");
        user.setAge(22);
        boolean isSuccess = user.insert();
    }


    /**
     *      先查是否有存在记录
     *      SELECT id,name,password,age FROM t_user WHERE id=?
     *      有的话，就update
     *      UPDATE t_user SET name=?, password=?, age=? WHERE id=?
     */
    @Test
    public void testInsertOrUpdate()
    {
        User user = new User();
        user.setId(8L);
        user.setName("Leon");
        user.setPassword("1111");
        user.setAge(33);
        boolean isSuccess = user.insertOrUpdate();
    }

    /**
     *      DELETE FROM t_user WHERE id=?
     */
    @Test
    public void testDelete()
    {
        User user = new User();
        user.setId(10L);
        boolean isSuccess = user.deleteById(); //user.deleteById(8L);
    }

    /**
     *      SELECT id,name,password,age FROM t_user WHERE (age >= ?)
     */
    @Test
    public void testSelect()
    {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.ge("age",20);

        User user = new User();
        List<User> users = user.selectList(wrapper);
    }


}
