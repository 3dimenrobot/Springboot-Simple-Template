package cn.example.project.module.corn;

import cn.example.project.module.base.Message;
import cn.example.project.module.base.PageHelper;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@Api(tags = "新闻")
@RestController
@RequestMapping("/newsItem")
public class NewsItemController {

    @Autowired
    private NewsItemRepo repo;

    @PostMapping("/")
    @ResponseBody
    public Message save(NewsItem item) {
        repo.save(item);
        return new Message("success", item);
    }

    @GetMapping
    @ResponseBody
    public Message getPagedList(PageHelper<NewsItem> page) {
        NewsItem entity = page.getEntity();
        ExampleMatcher matcher = page.getEntityLikeMatcher();
        Pageable pagination = page.getPagination();

        Page<NewsItem> result = null;
        if (matcher != null) {
            result = repo.findAll(Example.of(entity, matcher), pagination);
        } else {
            result = repo.findAll(pagination);
        }
        return new Message("success", result);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public Message delete(@PathVariable("id") Integer id) {
        repo.deleteById(id);
        return new Message("success", null);
    }
}
