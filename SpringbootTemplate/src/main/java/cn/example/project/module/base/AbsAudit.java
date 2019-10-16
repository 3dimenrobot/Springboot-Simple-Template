package cn.example.project.module.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.util.Date;


@ApiModel(description = "审计信息，将来可以扩展为数据权限")
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbsAudit {
    @ApiModelProperty(value = "创建时间", example = "2019-10-14 00:02:00")
    @CreatedDate
    private Date createTime;

    @ApiModelProperty(value = "修改时间", example = "2019-10-14 00:02:00")
    @LastModifiedDate
    private Date updateTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}