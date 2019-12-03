package cn.example.project.module.rbac;

import cn.example.project.module.base.AbsAudit;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@ApiModel(description = "用户")
@Entity
public class User extends AbsAudit {
    @ApiModelProperty(value = "id", example = "12")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ApiModelProperty(value = "中文名", example = "系统管理管")
    private String cnname;

    @ApiModelProperty(value = "登录名,不允许重复", example = "user")
    @Column(unique = true)
    private String username;

    @ApiModelProperty(value = "密码", example = "123456")
    private String password;

    @ApiModelProperty(value = "是否启用", example = "true")
    private boolean enabled;

    @ApiModelProperty(value = "角色集合", example = "[admin,predictor]")
    @ManyToMany(fetch=FetchType.EAGER)
    private List<Role> roles = new ArrayList<>();

    @ApiModelProperty(value = "备注", example = "系统管理员管理系统资源")
    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCnname() {
        return cnname;
    }

    public void setCnname(String cnname) {
        this.cnname = cnname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
