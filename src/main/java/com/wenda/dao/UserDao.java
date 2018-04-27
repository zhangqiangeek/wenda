package com.wenda.dao;

import com.wenda.model.User;
import org.apache.ibatis.annotations.*;

/**
 * 用户表数据库操作
 * Created by zq on 2017/10/6.
 */
@Mapper
public interface UserDao {
    String TABLE_NAME = " user ";
    String INSERT_FIELDS = " name, password, salt, head_url ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({ "insert into ", TABLE_NAME, "(", INSERT_FIELDS, ") values (#{name},#{password},#{salt},#{headUrl})" })
    int addUser(User user);

    @Select({ "select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}" })
    User selectById(int id);

    /**
     * 通过用户名称查找用户
     *
     * @param name
     * @return
     */
    @Select({ "select ", SELECT_FIELDS, " from ", TABLE_NAME, " where name=#{name}" })
    User selectByName(String name);

    @Update({ "update ", TABLE_NAME, " set password=#{password} where id=#{id}" })
    void updatePassword(User user);

    @Delete({ "delete from ", TABLE_NAME, " where id=#{id}" })
    void deleteById(int id);
}
