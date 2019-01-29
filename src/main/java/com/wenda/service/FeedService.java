package com.wenda.service;

import com.wenda.dao.FeedDao;
import com.wenda.model.Feed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author evilhex.
 * @date 2018/9/26 下午4:27.
 */
@Service
public class FeedService {

    @Autowired
    private FeedDao feedDao;

    /**
     * 拉模式获取所有的值
     *
     * @param maxId
     * @param userIds
     * @param count
     * @return
     */
    public List<Feed> getUserFeeds(int maxId, List<Integer> userIds, int count) {
        return feedDao.selectUserFeeds(maxId, userIds, count);
    }

    public boolean addFeed(Feed feed) {
        feedDao.addFeed(feed);
        return (feed.getId() > 0);
    }

    public Feed getById(int id) {
        return feedDao.getFeedById(id);
    }
}
