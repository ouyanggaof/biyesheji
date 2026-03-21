package com.controller;

import com.alibaba.fastjson.JSONObject;
import com.service.DictionaryService;
import com.utils.CommonUtil;
import com.utils.PageUtils;
import com.utils.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.entity.ShangjiaEntity;
import com.service.ShangjiaService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 商家 后端接口（供下拉与详情）
 */
@RestController
@Controller
@RequestMapping("/shangjia")
public class ShangjiaController {
    private static final Logger logger = LoggerFactory.getLogger(ShangjiaController.class);

    @Autowired
    private ShangjiaService shangjiaService;
    @Autowired
    private DictionaryService dictionaryService;

    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        CommonUtil.checkMap(params);
        PageUtils page = shangjiaService.queryPage(params);
        return R.ok().put("data", page);
    }

    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id, HttpServletRequest request) {
        ShangjiaEntity entity = shangjiaService.selectById(id);
        if (entity != null)
            return R.ok().put("data", entity);
        return R.error(511, "查不到数据");
    }
}
