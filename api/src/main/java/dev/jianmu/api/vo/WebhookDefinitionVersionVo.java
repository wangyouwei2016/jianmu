package dev.jianmu.api.vo;

import dev.jianmu.trigger.aggregate.custom.webhook.CustomWebhookDefinitionVersion;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Schema(description = "WebhookDefinitionVersionVo")
public class WebhookDefinitionVersionVo {
    /**
     * 主键
     */
    private String id;
    /**
     * 自定义Webhook定义id
     */
    private String definitionId;
    /**
     * 自定义Webhook定义唯一标识
     */
    private String ref;
    /**
     * 自定义Webhook定义拥有者唯一标识
     */
    private String ownerRef;
    /**
     * 版本号
     */
    private String version;
    /**
     * 创建者唯一标识
     */
    private String creatorRef;
    /**
     * 创建者名称
     */
    private String creatorName;
    /**
     * 事件集
     */
    private List<CustomWebhookDefinitionVersion.Event> events;
    /**
     * DSL文本
     */
    private String dslText;
    /**
     * 创建时间
     */
    private LocalDateTime createdTime;
    /**
     * 最后修改时间
     */
    private LocalDateTime lastModifiedTime;
}