package cn.example.project.config.sec;

import org.springframework.security.core.GrantedAuthority;

/**
 * restful 风格授权
 * <p>
 * url +  method
 */
public class RestfulGrantedAuthority implements GrantedAuthority {

    private String permissionUrl;

    private String method;

    public RestfulGrantedAuthority(String permissionUrl, String method) {
        this.permissionUrl = permissionUrl;
        this.method = method;
    }

    public String getPermissionUrl() {
        return permissionUrl;
    }

    public void setPermissionUrl(String permissionUrl) {
        this.permissionUrl = permissionUrl;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    // 返回权限匹配码 // curl -X POST "http://127.0.0.1:9600/rbac/user/111"
    @Override
    public String getAuthority() {
        // 参考 DELETE /rbac/user/111
        return this.method + " " + this.permissionUrl;
    }

    public final static String LOGIN_AUTHORITY = "POST /login";
    public final static String URL_NOT_FOUND = "url_not_found";
    public final static String WHITE_RESOURCE = "white_resource";
}
