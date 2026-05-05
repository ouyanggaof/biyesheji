package com.controller;

import java.util.*;
import com.alibaba.fastjson.JSONObject;
import com.service.TokenService;
import com.service.DictionaryService;
import com.annotation.IgnoreAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.entity.*;
import com.entity.view.*;
import com.service.*;
import com.utils.CommonUtil;
import com.utils.PageUtils;
import com.utils.R;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户咨询（商家与用户一对一聊天）
 * 后端接口
 */
@RestController
@Controller
@RequestMapping("/shangjiaChat")
public class ShangjiaChatController {
    private static final Logger logger = LoggerFactory.getLogger(ShangjiaChatController.class);

    @Autowired
    private ShangjiaChatService shangjiaChatService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private DictionaryService dictionaryService;
    @Autowired
    private YonghuService yonghuService;
    @Autowired
    private ShangjiaService shangjiaService;

    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        logger.debug("page:,,params:{}", JSONObject.toJSONString(params));
        String role = String.valueOf(request.getSession().getAttribute("role"));
        if ("用户".equals(role))
            params.put("yonghuId", request.getSession().getAttribute("userId"));
        if ("商家".equals(role))
            params.put("shangjiaId", request.getSession().getAttribute("userId"));
        CommonUtil.checkMap(params);
        PageUtils page = shangjiaChatService.queryPage(params);
        List<ShangjiaChatView> list = (List<ShangjiaChatView>) page.getList();
        for (ShangjiaChatView c : list)
            dictionaryService.dictionaryConvert(c, request);
        return R.ok().put("data", page);
    }

    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id, HttpServletRequest request) {
        ShangjiaChatEntity entity = shangjiaChatService.selectById(id);
        if (entity != null) {
            ShangjiaChatView view = new ShangjiaChatView(entity);
            YonghuEntity yonghu = yonghuService.selectById(entity.getYonghuId());
            if (yonghu != null) {
                view.setYonghuName(yonghu.getYonghuName());
                view.setYonghuPhoto(yonghu.getYonghuPhoto());
            }
            ShangjiaEntity shangjia = shangjiaService.selectById(entity.getShangjiaId());
            if (shangjia != null) {
                view.setShangjiaName(shangjia.getShangjiaName());
                view.setShangjiaPhone(shangjia.getShangjiaPhone());
            }
            dictionaryService.dictionaryConvert(view, request);
            return R.ok().put("data", view);
        }
        return R.error(511, "查不到数据");
    }

    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody ShangjiaChatEntity entity, HttpServletRequest request) {
        entity.setCreateTime(new Date());
        entity.setInsertTime(new Date());
        if (entity.getIssueTime() == null)
            entity.setIssueTime(new Date());
        if (entity.getZhuangtaiTypes() == null)
            entity.setZhuangtaiTypes(1); // 字典 zhuangtai_types：1 未回复
        shangjiaChatService.insert(entity);
        return R.ok();
    }

    /**
     * 后端修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody ShangjiaChatEntity entity, HttpServletRequest request) {
        ShangjiaChatEntity old = entity.getId() != null ? shangjiaChatService.selectById(entity.getId()) : null;
        String newReply = entity.getShangjiaChatReplyText();
        boolean replyNonEmpty = StringUtils.isNotBlank(newReply);
        if (old != null) {
            String oldReply = old.getShangjiaChatReplyText();
            boolean wasEmpty = StringUtils.isBlank(oldReply);
            if (replyNonEmpty && wasEmpty) {
                entity.setReplyTime(new Date());
                if (entity.getZhuangtaiTypes() == null)
                    entity.setZhuangtaiTypes(2); // 已回复
            } else if (replyNonEmpty && entity.getReplyTime() == null && old.getReplyTime() == null) {
                entity.setReplyTime(new Date());
                if (entity.getZhuangtaiTypes() == null)
                    entity.setZhuangtaiTypes(2);
            }
        } else {
            if (replyNonEmpty && entity.getReplyTime() == null)
                entity.setReplyTime(new Date());
            if (replyNonEmpty && entity.getZhuangtaiTypes() == null)
                entity.setZhuangtaiTypes(2);
        }
        shangjiaChatService.updateById(entity);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids, HttpServletRequest request) {
        shangjiaChatService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }

    /**
     * 前端列表
     */
    @IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        CommonUtil.checkMap(params);
        PageUtils page = shangjiaChatService.queryPage(params);
        List<ShangjiaChatView> list = (List<ShangjiaChatView>) page.getList();
        for (ShangjiaChatView c : list)
            dictionaryService.dictionaryConvert(c, request);
        return R.ok().put("data", page);
    }

    /**
     * 前端详情
     */
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Integer id, HttpServletRequest request) {
        ShangjiaChatEntity entity = shangjiaChatService.selectById(id);
        if (entity != null) {
            ShangjiaChatView view = new ShangjiaChatView(entity);
            YonghuEntity yonghu = yonghuService.selectById(entity.getYonghuId());
            if (yonghu != null) {
                view.setYonghuName(yonghu.getYonghuName());
                view.setYonghuPhoto(yonghu.getYonghuPhoto());
            }
            ShangjiaEntity shangjia = shangjiaService.selectById(entity.getShangjiaId());
            if (shangjia != null) {
                view.setShangjiaName(shangjia.getShangjiaName());
                view.setShangjiaPhone(shangjia.getShangjiaPhone());
            }
            dictionaryService.dictionaryConvert(view, request);
            return R.ok().put("data", view);
        }
        return R.error(511, "查不到数据");
    }

    /**
     * 前端保存（用户提问）
     */
    @RequestMapping("/add")
    public R add(@RequestBody ShangjiaChatEntity entity, HttpServletRequest request) {
        entity.setCreateTime(new Date());
        entity.setInsertTime(new Date());
        entity.setIssueTime(new Date());
        if (entity.getZhuangtaiTypes() == null)
            entity.setZhuangtaiTypes(1);
        shangjiaChatService.insert(entity);
        return R.ok();
    }
}
