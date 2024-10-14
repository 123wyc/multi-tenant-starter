package com.example;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * 业务场景类，通过 bizId, tenant, scenario 唯一定位一个用户场景
 */
public class BizScenario {
    public final static String DEFAULT_BIZ_ID = "#defaultBizId#";
    public final static String DEFAULT_TENANT = "#defaultTenant#";
    public final static String DEFAULT_SCENARIO = "#defaultScenario#";
    private final static String DOT_SEPARATOR = ".";

    /**
     * bizId 用于唯一确定一块业务
     */
    private String bizId = DEFAULT_BIZ_ID;

    /**
     * tenant 用于唯一确定一个租户
     */
    private String tenant = DEFAULT_TENANT;

    /**
     * scenario 用于唯一确定某块业务下的细分场景
     */
    private String scenario = DEFAULT_SCENARIO;

    private BizScenario() {
    }

    public static BizScenario valueOf(String bizId, String tenant, String scenario) {
        Assert.notNull(bizId, "bizId 不能为空");
        BizScenario bizScenario = new BizScenario();
        bizScenario.bizId = bizId;
        bizScenario.tenant = tenant;
        bizScenario.scenario = scenario;
        return bizScenario;
    }

    public static BizScenario valueOf(String bizId, String tenant) {
        Assert.notNull(bizId, "bizId 不能为空");
        if (StringUtils.hasText(tenant)) {
            return BizScenario.valueOf(bizId, tenant, DEFAULT_SCENARIO);
        }
        //返回默认租户
        return BizScenario.valueOf(bizId);
    }

    public static BizScenario valueOf(String bizId) {
        Assert.notNull(bizId, "bizId 不能为空");
        return BizScenario.valueOf(bizId, DEFAULT_TENANT, DEFAULT_SCENARIO);
    }

    public static BizScenario newDefault() {
        return BizScenario.valueOf(DEFAULT_BIZ_ID, DEFAULT_TENANT, DEFAULT_SCENARIO);
    }

    public String getIdentityWithDefaultScenario() {
        return bizId + DOT_SEPARATOR + tenant + DOT_SEPARATOR + DEFAULT_SCENARIO;
    }

    public String getIdentityWithDefaultTenant() {
        return bizId + DOT_SEPARATOR + DEFAULT_TENANT + DOT_SEPARATOR + DEFAULT_SCENARIO;
    }

    /**
     * 获取唯一标识
     */
    public String getUniqueIdentity() {
        return bizId + DOT_SEPARATOR + tenant + DOT_SEPARATOR + scenario;
    }
}

