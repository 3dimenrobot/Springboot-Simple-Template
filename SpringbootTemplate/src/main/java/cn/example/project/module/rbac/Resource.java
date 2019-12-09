package cn.example.project.module.rbac;

import cn.example.project.module.base.AbsAudit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 资源：
 * 对应后端服务的模块、实体功能模块、方法级功能
 * 如
 * /rbac/user
 * /rbac/user/{id} delete
 * 前端资源的存储文件夹，对应vue页面的菜单和组件名称，按钮
 * 如 /rbac/user.vue delete
 *
 */
@ApiModel(description = "菜单：对应后端服务的url，前端资源的存储路径，对应vue页面的菜单,route路由")
@Entity
public class Resource extends AbsAudit {
    @ApiModelProperty(value = "id", example = "12")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ApiModelProperty(value = "菜单名称", example = "用户管理")
    private String name;

    @ApiModelProperty(value = "类型：目录,菜单,按钮", example = "系统管理，用户管理 添加")
    @Enumerated(EnumType.STRING)
    private ResourceLevel level;

    @ApiModelProperty(value = "资源元素url", example = "/user")
    private String atomUrl;

    @ApiModelProperty(value = "资源访问url", example = "/rbac/user/{id}")
    private String url;

    // 按钮级别的属性
    @ApiModelProperty(value = "请求方法，对应后端的方法", example = "如All|GET|POST|PUT|DETELE")
    private String method;

    @ApiModelProperty(value = "crud 动作，行为", example = "如all|list|read|update|delete")
    private String crudName;

    @ApiModelProperty(value = "父级菜单id", example = "rbac")
    @OneToOne(fetch=FetchType.LAZY)
    private Resource parent;

    @ApiModelProperty(value = "子菜单集合", example = "rbac")
    @OneToMany(fetch=FetchType.LAZY)
    private List<Resource> children = new ArrayList<>();

    @ApiModelProperty(value = "备注", example = "菜单功能描述")
    private String remark;


    public Resource() {
    }

    public Resource(Integer id) {
        this.id = id;
    }


    public Resource(Integer id,String name, ResourceLevel level, String atomUrl, String url, Resource parent) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.atomUrl = atomUrl;
        this.url = url;
        this.parent = parent;
    }

    public Resource(Integer id,String name, ResourceLevel level, String atomUrl, String url, Resource parent, String method, String crudName) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.atomUrl = atomUrl;
        this.url = url;
        this.method = method;
        this.crudName = crudName;
        this.parent = parent;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ResourceLevel getLevel() {
        return level;
    }

    public void setLevel(ResourceLevel level) {
        this.level = level;
    }

    public String getAtomUrl() {
        return atomUrl;
    }

    public void setAtomUrl(String atomUrl) {
        this.atomUrl = atomUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getCrudName() {
        return crudName;
    }

    public void setCrudName(String crudName) {
        this.crudName = crudName;
    }

    public Resource getParent() {
        return parent;
    }

    public void setParent(Resource parent) {
        this.parent = parent;
    }

    public List<Resource> getChildren() {
        return children;
    }

    public void setChildren(List<Resource> children) {
        this.children = children;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
