package com.internetplus.bankpunishment.crawler.pipeline.handler;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;

/**
 * @author Yunthin.Chow
 * @version 1.0
 * Created by Yunthin.Chow on 2021/10/20
 */
public interface PipelineHandler {
    /**
     * 对
     * @param resultItems
     * @param task
     */
    void process(ResultItems resultItems, Task task);
}
