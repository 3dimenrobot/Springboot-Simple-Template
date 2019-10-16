package cn.example.project.module.base;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import java.util.Arrays;

@ApiModel(description = "分页通用工具类")
public class PageHelper<T extends Object> {

    @ApiModelProperty(value = "参数实体")
    private T entity;

    private Integer pageNo = 0;

    private Integer pageSize = 10;

    // "createTime desc"
    private String direction = "desc";
    private String orders = "createTime"; // name,createTime

    public Pageable getPagination() {
        orders = !StringUtils.isEmpty(orders) ? orders : "createTime";
        String[] order_props = orders.split(",");
        pageNo = pageNo <0 ? 0: pageNo;
        return PageRequest.of(pageNo, pageSize, new Sort(Sort.Direction.fromString(direction), Arrays.asList(order_props)));
    }

    /**
     * 根据对象的json字符串构造对象的查询比较器 以contains作为条件
     *
     * @return
     */
    public ExampleMatcher getEntityLikeMatcher() {
        if (entity == null) return null;
        JSONObject jprops = JSONObject.parseObject(JSONObject.toJSONString(entity));
        ExampleMatcher matcher = ExampleMatcher.matching();
        for (String prop : jprops.keySet()) {
            if (!StringUtils.isEmpty(prop)) {
                matcher = matcher.withMatcher(prop, ExampleMatcher.GenericPropertyMatchers.contains());
            }
        }
        return matcher;
    }

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getOrders() {
        return orders;
    }

    public void setOrders(String orders) {
        this.orders = orders;
    }
}
