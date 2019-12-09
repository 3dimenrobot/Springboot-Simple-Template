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

    private Integer page = 0;

    private Integer size = 10;

    // "createTime desc"
    private String direction = "desc";
    private String sort = "createTime"; // name,createTime

    public Pageable getPagination() {
        sort = !StringUtils.isEmpty(sort) ? sort : "createTime";
        String[] order_props = sort.split(",");
        page = page <0 ? 0: page;
        return PageRequest.of(page, size, new Sort(Sort.Direction.fromString(direction), Arrays.asList(order_props)));
    }

    /**
     * 根据对象的json字符串构造对象的查询比较器 以contains作为条件
     *
     * @return
     */
    public  ExampleMatcher getEntityLikeMatcher() {
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

    /**
     * 根据对象的json字符串构造对象的查询比较器 以contains作为条件
     *
     * @return
     */
    public static ExampleMatcher getEntityLikeMatcher(Object entity) {
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

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
