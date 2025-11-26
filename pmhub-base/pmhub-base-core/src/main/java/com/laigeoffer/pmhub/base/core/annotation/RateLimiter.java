package com.laigeoffer.pmhub.base.core.annotation;


import com.laigeoffer.pmhub.base.core.constant.CacheConstants;
import com.laigeoffer.pmhub.base.core.enums.LimitType;

import java.lang.annotation.*;

/**
 * 限流注解
 *
 * @author canghe
 */
@Target(ElementType.METHOD) //指明注解的使用目标
@Retention(RetentionPolicy.RUNTIME) //指明注解的保留策略 RUNTIME表示在运行时注解仍然存在 可以通过反射得到
@Documented
public @interface RateLimiter {
    /**
     * 限流key
     */
    public String key() default CacheConstants.RATE_LIMIT_KEY;

    /**
     * 限流时间,单位秒
     */
    public int time() default 60;

    /**
     * 限流次数
     */
    public int count() default 100;

    /**
     * 限流类型
     */
    public LimitType limitType() default LimitType.DEFAULT;
}
