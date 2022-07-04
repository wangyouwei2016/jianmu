package dev.jianmu.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Ethan Liu
 * @class JwtResponse
 * @description JwtResponse
 * @create 2021-05-18 09:48
 */
@Getter
@Builder
@Schema(description = "JwtResponse")
public class JwtResponse {
    private final String type = "Bearer";
    private String token;
    private String id;
    private String username;
    private String avatarUrl;
}
