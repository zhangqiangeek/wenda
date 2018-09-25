package com.wenda.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Spring DeferredResult应用
 *
 * @author evilhex.
 * @date 2018/9/12 下午6:53.
 */
@RestController
@RequestMapping("/async")
public class AsyncController {
    final Map deferredResultMap = new ConcurrentHashMap();

    @RequestMapping("/longPolling")
    public DeferredResult longPolling() {
        DeferredResult deferredResult = new DeferredResult(0L);
        deferredResultMap.put(deferredResult.hashCode(), deferredResult);
        deferredResult.onCompletion(() -> {
            deferredResultMap.remove(deferredResult.hashCode());
            System.out.println("还剩余" + deferredResultMap.size() + "个deferredResult未响应");
        });
        return deferredResult;
    }

}
