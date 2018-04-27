package com.wenda.service;

import org.springframework.stereotype.Service;

/**
 * @author evilhex
 *         2016/7/10
 */
@Service
public class WendaService {
    public String getMessage(int userId) {
        return "Hello Message:" + String.valueOf(userId);
    }
}
