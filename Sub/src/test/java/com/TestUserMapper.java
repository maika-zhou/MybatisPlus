package com;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mapper.UserMapper;
import com.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestUserMapper
{
    @Autowired
    private UserMapper userMapper;

    /**
     *  SELECT name,age FROM t_user WHERE (name LIKE ?) ORDER BY age DESC
     */
    @Test
    public void testSelectList()
    {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.like("name","Maika");
        //按照年龄倒排
        wrapper.orderByDesc("age");
        // 只显示name、age 字段
        wrapper.select("name","age");
        /**
         *  如果没传值，就代表查出所有数据
         *  List<User> users = this.userMapper.selectList(null);
         */
        List<User> users = this.userMapper.selectList(wrapper);

        for( User user: users)
        {
            System.out.println(user);
        }
    }

    /**
     *  SELECT id,name,password,age FROM t_user WHERE id=?
     */
    @Test
    public void testSelectById()
    {
        User user = this.userMapper.selectById(1);
        System.out.println(user);
    }

    /**
     *  SELECT id,name,password,age FROM t_user WHERE id IN ( ? , ? , ? )
     */
    @Test
    public void testSelectByIDs()
    {
        List<User> users = this.userMapper.selectBatchIds(Arrays.asList(3,4,5));
        System.out.println(users.size());
    }

    /**
     *  当查询结果 >1 的时候，就会报错
     *
     *  SELECT id,name,password,age FROM t_user WHERE (password = ?)
     */
    @Test
    public void testSelectOne()
    {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("password","654321" );   //第一个参数为DB中的字段
        //当查询结果 >1 的时候，就会报错
        User user = this.userMapper.selectOne(wrapper);
        System.out.println(user);
    }

    /**
     *  SELECT COUNT( * ) FROM t_user WHERE (age > ?)
     */
    @Test
    public void testSelectCount()
    {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.gt("age", 20);   //第一个参数为DB中的字段
        int count = this.userMapper.selectCount(wrapper);
        System.out.println(count);
    }


    /**
     *  SELECT id,name,password,age FROM t_user LIMIT ?,?
     */
    @Test
    public void testSelectPage()
    {
        //查询第一页，每页显示2条数据
        Page<User> page = new Page<>(1,2);

        IPage<User> iPage = userMapper.selectPage(page,null);
        System.out.println("数据总条数： "+iPage.getTotal());
        System.out.println("数据总页数： "+iPage.getPages());
        System.out.println("当前页数： "+iPage.getCurrent());

        //遍历iPage中所有的User Obj
        List<User> users = iPage.getRecords();
        for( User user: users)
        {
            System.out.println(user);
        }
    }



    @Test
    public void testSelectAllEq()
    {
        //设置条件
        Map<String,Object> map = new HashMap<>();
        map.put("name","maika");
        map.put("age",38);
        map.put("password",null);

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        /**
         *  如果参数值=null，那就作为 is null为查询条件
         *  SELECT id,name,password,age FROM t_user WHERE (password IS NULL AND name = ? AND age = ?)
         */
        //wrapper.allEq(map);

        /**
         *  如果参数值=null，那忽略此查询条件
         *  SELECT id,name,password,age FROM t_user WHERE (name = ? AND age = ?)
         */
        //wrapper.allEq(map,false);

        /**
         *  表示只取 map中的key为 age 或 name 为查询条件
         *  SELECT id,name,password,age FROM t_user WHERE (name = ? AND age = ?)
         */
        wrapper.allEq( (k,v)->(k.equals("age") || k.equals("name")) , map);
        List<User> users = this.userMapper.selectList(wrapper);

    }

    /**
     *  eq      =
     *  ne      <>
     *  gt      >
     *  ge      >=
     *  lt      <
     *  le      <=
     *  between
     *  notBetween
     *
     *  like        '%值%'
     *  notLike     '%值%'
     *  likeLeft    '%值'
     *  likeRight   '值%'
     *  SELECT id,name,password,age FROM t_user WHERE (name = ? AND age >= ? AND password IN (?,?,?))
     */
    @Test
    public void testSelectEq()
    {

        QueryWrapper<User> wrapper = new QueryWrapper<>();

        wrapper.eq("name","maika")
                .ge("age",20)
                .in("password","1","2","3");

        List<User> users = this.userMapper.selectList(wrapper);

    }


    /**
     *  表示只取 map中的key为 age 或 name 为查询条件
     *  SELECT id,name,password,age FROM t_user WHERE (name = ? OR age >= ?)
     */
    @Test
    public void testSelectOr()
    {
        QueryWrapper<User> wrapper = new QueryWrapper<>();

        wrapper.eq("name","maika")
                .or()
                .ge("age",20);

        List<User> users = this.userMapper.selectList(wrapper);
    }



    /**
     *  INSERT INTO t_user ( name, password, age ) VALUES ( ?, ?, ? )
     */
    @Test
    public void testInsert()
    {
        User user = new User();
        user.setName("Peter");
        user.setAge(22);
        user.setPassword("123456");
        user.setAddress("Hook Ave, CA");
        //DB受影响行数
        int result  = this.userMapper.insert(user);
        System.out.println("result = " + result);
        //自增长的ID会自动回填到user对象中
        System.out.println( "User ID -->"+user.getId());
    }


    /**
     *  UPDATE t_user SET name=?, age=? WHERE id=?
     */
    @Test
    public void testUpdateByID()
    {
        //  UPDATE t_user SET name=?, age=? WHERE id=?
        User user = new User();
        //条件，根据ID更新
        user.setId(4L);
        //更新的字段
        user.setName("PeterBackup");
        user.setAge(33);
        //DB受影响行数
        int result  = this.userMapper.updateById(user);
        System.out.println("result = " + result);
        System.out.println( "User -->"+user);
    }

    /**
     *  使用QueryWrapper
     *  把password 123456 改成 654321
     *  UPDATE t_user SET password=? WHERE (password = ?)
     */
    @Test
    public void testUpdate1()
    {
        User user = new User();
        //更新的字段
        user.setPassword("654321");

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("password","123456" );   //第一个参数为DB中的字段

        //DB受影响行数
        int result  = this.userMapper.update(user,wrapper);
        System.out.println("result = " + result);
    }

    /**
     *  使用UpdateWrapper
     *  UPDATE t_user SET password=?,age=? WHERE (name = ?)
     */
    @Test
    public void testUpdate2()
    {
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.set("password","123456" ).set("age",21) //更新字段
                .eq("name","Peter");        //更新条件

        //DB受影响行数
        int result  = this.userMapper.update(null,wrapper);
        System.out.println("result = " + result);
    }

    /**
     *  DELETE FROM t_user WHERE id=?
     */
    @Test
    public void testDeleteById()
    {
        int result  = this.userMapper.deleteById(4);
    }

    /**
     *  DELETE FROM t_user WHERE (password = ? AND name = ?)
     */
    @Test
    public void testDeleteByQueryWrapper()
    {
        // 用法 1
//        QueryWrapper<User> wrapper = new QueryWrapper<>();
//        wrapper.eq("password","123456" );
//        wrapper.eq("name","Perter" );

        // 用法 2
        User user = new User();
        user.setPassword("123456");
        user.setName("Peter");
        QueryWrapper<User> wrapper = new QueryWrapper<>(user);

        int result  = this.userMapper.delete(wrapper);
    }

    /**
     *  DELETE FROM t_user WHERE id IN ( ? , ? , ? )
     */
    @Test
    public void testDeleteByIDs()
    {
        int result  = this.userMapper.deleteBatchIds(Arrays.asList(3,4,5));
    }

    /**
     *  此查询是对应UserMapper.xml中的 findUserByName
     *  SELECT * FROM t_user WHERE name=?
     */
    @Test
    public void testFindUserByName()
    {
        User user = new User();
        user.setName("Maika");
        user = userMapper.findUserByName(user);
        System.out.println("user = " + user);
    }



}