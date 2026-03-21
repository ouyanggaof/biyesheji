package com.service;

import com.baomidou.mybatisplus.service.IService;
import com.entity.ShangjiaEntity;
import com.utils.PageUtils;
import java.util.Map;

public interface ShangjiaService extends IService<ShangjiaEntity> {

    PageUtils queryPage(Map<String, Object> params);
}
