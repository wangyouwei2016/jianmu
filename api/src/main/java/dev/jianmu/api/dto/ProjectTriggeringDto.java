package dev.jianmu.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Daihw
 * @class ProjectTriggeringDto
 * @description ProjectTriggeringDto
 * @create 2023/4/14 10:09 上午
 */
@Getter
@Schema(description = "项目触发Dto")
public class ProjectTriggeringDto {
    @Valid
    @Schema(description = "触发器参数")
    private List<TriggerParamVo> triggerParams;

    public Map<String, Object> toMap() {
        if (this.triggerParams == null) {
            return Map.of();
        }
        return this.triggerParams.stream()
                .filter(t -> t.getValue() != null)
                .collect(Collectors.toMap(TriggerParamVo::getName, TriggerParamVo::getValue, (k1, k2) -> k2));
    }

    @Getter
    public static class TriggerParamVo {
        @NotBlank(message = "参数名称不能为空")
        private String name;
        private Object value;
    }
}
