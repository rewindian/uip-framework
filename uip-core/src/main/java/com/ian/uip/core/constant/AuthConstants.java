package com.ian.uip.core.constant;

public final class AuthConstants {

    private AuthConstants() {
    }

    public static final String PRIVATE_SECRET_KEY = "OzzjiK0UDKShOQzbhgQhwf7MyhMORj03HdOckHpmHlN9tSO53zEAxccJ74zSaxvLeml1wv" +
            "IOeoNdr5iNZRQw2vzR0yQdO0IPz29";


    public static final String HEADER_TOKEN = "Token";

    public static final String PARAMETER_TOKEN = "token";

    /**
     * token过期时间（秒）
     */
    public static final long TOKEN_EXPIRE_TIME = 60 * 30;

    /**
     * redis中token的key前缀
     */
    public static final String TOKEN_CACHE_PREFIX = "userToken:";

    /**
     * 超级管理员
     */
    public static final String SUPER_ADMIN_PARAM_CODE = "SUPER_ADMIN";

    /**
     * 默认密码
     */
    public static final String DEFAULT_PWD_PARAM_CODE = "DEFAULT_PWD";
}
