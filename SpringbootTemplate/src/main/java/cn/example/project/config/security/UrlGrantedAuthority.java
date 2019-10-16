package cn.example.project.config.security;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author zhoukebo
 * @date 2018/9/19
 */
public class UrlGrantedAuthority implements GrantedAuthority {
    /**
     * /rbac/user
     */
    @ApiModelProperty(value = "后端路径", example = "/rbac/user")
    private String permissionUrl;

    @ApiModelProperty(value = "后端方法", example = "add|delete")
    private String method;

    public UrlGrantedAuthority(String permissionUrl, String method) {
        this.permissionUrl = permissionUrl;
        this.method = method;
    }

    @Override
    public String getAuthority() {
        return this.method;
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
}
