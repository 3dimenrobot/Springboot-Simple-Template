package cn.example.project.module.rbac;

import cn.example.project.module.base.AbsAudit;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

//https://github.com/lenve/vhr
/*
 routes: [
    {
      path: '/',
      name: 'Login',
      component: Login,
      hidden: true
    }, {
      path: '/home',
      name: '主页',
      component: Home,
      hidden: true,
      meta: {
        requireAuth: true
      },
      children: [
        {
          path: '/chat',
          name: '消息',
          component: Chat,
          hidden: true,
          meta: {
            keepAlive: false,
            requireAuth: true
          }
        }
      ]
    }
  ]
 */

@ApiModel(description = "菜单：对应vue页面的菜单,route路由")
@Entity
public class Menu extends AbsAudit {
    @ApiModelProperty(value = "id", example = "12")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ApiModelProperty(value = "资源访问url", example = "http://localhost:8080/#/rbac/user")
    private String url;

    @ApiModelProperty(value = "route的path", example = "/rbac/user")
    private String path;

    @ApiModelProperty(value = "菜单组件名称", example = "User")
    private String component;

    @ApiModelProperty(value = "菜单名称", example = "用户管理")
    private String name;

    @ApiModelProperty(value = "菜单样式", example = "fa fa-address-card-o")
    private String iconCls;

    @ApiModelProperty(value = "父级菜单id", example = "rbac")
    private Integer parentId;

    @ApiModelProperty(value = "子菜单集合", example = "rbac")
    @OneToMany
    private List<Menu> children = new ArrayList<>();

    @ApiModelProperty(value = "菜单转台，是否需要权限", example = "{ keepAlive: false,requireAuth: true}")
    private MenuMeta meta;

    @ApiModelProperty(value = "备注", example = "菜单功能描述")
    private String remark;


    @ApiModelProperty(value = "按钮级别的操作权限", example = "[add,update]")  // 相当于shiro User:add
    @ManyToMany
    private List<Permission> permissions = new ArrayList<>();

    public class MenuMeta implements Serializable {

        private boolean keepAlive;
        private boolean requireAuth;

        public boolean isKeepAlive() {
            return keepAlive;
        }

        public void setKeepAlive(boolean keepAlive) {
            this.keepAlive = keepAlive;
        }

        public boolean isRequireAuth() {
            return requireAuth;
        }

        public void setRequireAuth(boolean requireAuth) {
            this.requireAuth = requireAuth;
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public List<Menu> getChildren() {
        return children;
    }

    public void setChildren(List<Menu> children) {
        this.children = children;
    }

    public MenuMeta getMeta() {
        return meta;
    }

    public void setMeta(MenuMeta meta) {
        this.meta = meta;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
