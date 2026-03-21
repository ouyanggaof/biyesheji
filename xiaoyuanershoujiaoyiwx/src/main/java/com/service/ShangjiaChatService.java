package com.service;

import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.ShangjiaChatEntity;
import java.util.Map;

public interface ShangjiaChatService extends IService<ShangjiaChatEntity> {

    PageUtils queryPage(Map<String, Object> params);
}
