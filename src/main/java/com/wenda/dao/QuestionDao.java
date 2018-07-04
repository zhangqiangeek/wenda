package com.wenda.dao;

import com.wenda.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 问题表数据库操作
 * Created by evilhex on 2017/10/6.
 */
@Mapper
public interface QuestionDao {
    String TABLE_NAME = " question ";
    String INSERT_FIELDS = " title, content, created_date, user_id, comment_count ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({ "insert into ", TABLE_NAME, "(", INSERT_FIELDS, ") values (#{title},#{content},#{createdDate},#{userId},#{commentCount})" })
    int addQuestion(Question question);

    @Select({ "select ", SELECT_FIELDS, "from ", TABLE_NAME, " where id=#{id} " })
    Question selectById(int id);

    @Update({ "update ", TABLE_NAME, " set comment_count = #{commentCount} where id=#{id}" })
    int updateCommentCount(@Param("id") int id, @Param("commentCount") int commentCount);

    List<Question> selectLatestQuestions(@Param("userId") int userId, @Param("offset") int offset,
            @Param("limit") int limit);
}
