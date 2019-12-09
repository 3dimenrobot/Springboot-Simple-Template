package cn.example.project.module.rbac;

import cn.example.project.module.base.AbsAudit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@ApiModel(description = "角色")
@Entity
public class Role extends AbsAudit {
    @ApiModelProperty(value = "id", example = "12")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ApiModelProperty(value = "角色中文名", example = "系统管理管")
    private String cnname;

    @ApiModelProperty(value = "角色名", example = "admin")
    private String name;

    @ApiModelProperty(value = "资源名", example = "资源集合")
    @ManyToMany(fetch=FetchType.EAGER)
    private List<Resource> resources = new ArrayList<>();

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
