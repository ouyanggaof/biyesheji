package com.service.impl;

import java.util.Map;
import com.dao.ShangjiaChatDao;
import com.entity.ShangjiaChatEntity;
import com.entity.view.ShangjiaChatView;
import com.service.ShangjiaChatService;
import com.utils.PageUtils;
import com.utils.Query;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("shangjiaChatService")
@Transactional
public class ShangjiaChatServiceImpl extends ServiceImpl<ShangjiaChatDao, ShangjiaChatEntity> implements ShangjiaChatService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<ShangjiaChatView> page = new Query<ShangjiaChatView>(params).getPage();
        page.setRecords(baseMapper.selectListView(page, params));
        return new PageUtils(page);
    }
}
