package cn.example.project.module.corn;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@ApiModel(description = "新闻")
@Entity
public class NewsItem extends RegionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 新闻内容
     */
    @ApiModelProperty(value = "新闻内容",example = "新闻内容")
    @Column(columnDefinition="TEXT")
    private String text;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
