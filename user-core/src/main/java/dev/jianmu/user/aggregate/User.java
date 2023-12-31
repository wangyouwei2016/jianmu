package dev.jianmu.user.aggregate;

/**
 * @author huangxi
 * @class User
 * @description User
 * @create 2022-06-29 15:51
 */
public class User {
    /**
     * 第三方平台用户id
     */
    private String id;

    /**
     * 头像
     */
    private String avatarUrl;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 数据
     */
    private String data;

    /**
     * 用户名
     */
    private String username;

    public User() {
    }


    public User(String id, String avatarUrl, String nickname, String data, String username) {
        this.id = id;
        this.avatarUrl = avatarUrl;
        this.nickname = nickname;
        this.data = data;
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static class Builder {
        private String id;
        private String avatarUrl;
        private String nickname;
        private String data;
        private String username;

        public Builder() {
        }

        public static Builder aReference() {
            return new Builder();
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder avatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
            return this;
        }

        public Builder nickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public Builder data(String data) {
            this.data = data;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public User build() {
            var user = new User();
            user.id = this.id;
            user.avatarUrl = this.avatarUrl;
            user.nickname = this.nickname;
            user.data = this.data;
            user.username = this.username;
            return user;
        }
    }
}
