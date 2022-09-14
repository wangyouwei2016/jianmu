package dev.jianmu.oauth2.api.impl.vo.gitee;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.jianmu.oauth2.api.config.OAuth2Properties;
import dev.jianmu.oauth2.api.exception.UnknownException;
import dev.jianmu.oauth2.api.util.AESEncryptionUtil;
import dev.jianmu.oauth2.api.util.ApplicationContextUtil;
import dev.jianmu.oauth2.api.vo.ITokenVo;
import lombok.Getter;
import lombok.Setter;

/**
 * @author huangxi
 * @class GiteeTokenVo
 * @description 请求gitee的token的vo
 * @create 2021-06-30 14:08
 */
@Getter
@Setter
public class TokenVo implements ITokenVo {
    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("expires_in")
    private long expiresIn;

    @JsonProperty("refresh_token")
    private String refreshToken;

    private String scope;

    @JsonProperty("created_at")
    private long createdAt;

    @Override
    public String getAccessToken() {
        return this.accessToken;
    }

    @Override
    public long getExpireInMs() {
        return this.expiresIn * 1000;
    }
}