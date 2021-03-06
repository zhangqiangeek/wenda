package com.wenda.dao;

import com.wenda.model.Feed;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author evilhex.
 * @date 2018/9/26 下午4:58.
 */
@Mapper
public interface FeedDao {
    String TABLE_NAME = " feed ";
    String INSERT_FIELDS = " user_id, data, created_date, type ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({ "insert into ", TABLE_NAME, "(", INSERT_FIELDS, ") values (#{userId},#{data},#{createdDate},#{type})" })
    int addFeed(Feed feed);

    @Select({ "select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}" })
    Feed getFeedById(int id);

    List<Feed> selectUserFeeds(@Param("maxId") int maxId, @Param("userIds") List<Integer> userIds, @Param("count") int count);
}
