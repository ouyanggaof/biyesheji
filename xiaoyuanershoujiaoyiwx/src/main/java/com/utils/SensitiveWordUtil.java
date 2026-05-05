package com.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * 论坛等内容违禁词简单校验（可扩展为词库表或配置文件）
 */
public final class SensitiveWordUtil {

    private static final String[] DEFAULT_WORDS = {
            "赌博", "色情", "法轮功", "枪支", "毒品", "诈骗", "台独", "疆独"
    };

    private SensitiveWordUtil() {
    }

    /**
     * @return 若包含违禁词则返回该词，否则返回 null
     */
    public static String findSensitive(String text) {
        if (StringUtils.isBlank(text)) {
            return null;
        }
        String t = text.toLowerCase();
        for (String w : DEFAULT_WORDS) {
            if (StringUtils.isBlank(w)) {
                continue;
            }
            if (t.contains(w.toLowerCase())) {
                return w;
            }
        }
        return null;
    }

    public static boolean containsSensitive(String text) {
        return findSensitive(text) != null;
    }
}
