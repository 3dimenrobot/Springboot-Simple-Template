package cn.example.project.module.corn;

import cn.example.project.module.base.AbsAudit;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.MappedSuperclass;


@MappedSuperclass
public class RegionEntity extends AbsAudit {

    @ApiModelProperty(value = "大地区",example = "华北地区")
    private String region;

    @ApiModelProperty(value = "省/直接市/自治区/特区",example = "河北省")
    private String province;

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}