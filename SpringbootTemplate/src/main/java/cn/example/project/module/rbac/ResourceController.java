package cn.example.project.module.rbac;

import cn.example.project.module.base.Message;
import cn.example.project.module.base.PageHelper;
import cn.hutool.log.StaticLog;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Api(tags = "102 菜单")
@RestController
@RequestMapping("/rbac/resource")
public class ResourceController {
    @Autowired
    private ResourceRepo repo;

    @Autowired
    private ResourceService service;

    @PostMapping("")
    @ResponseBody
    public Message create(@RequestBody Resource entity) {
        repo.save(entity);
        return new Message("success", entity);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public Message update(@PathVariable("id") Integer id, @RequestBody Resource entity) {
        repo.save(entity);
        return new Message("success", entity);
    }

    @GetMapping
    @ResponseBody
    public Message getPagedList(PageHelper page,Resource entity) {
        ExampleMatcher matcher = PageHelper.getEntityLikeMatcher(entity);
        Pageable pagination = page.getPagination();

        Page<Resource> result = null;
        if (matcher != null) {
            result = repo.findAll(Example.of(entity, matcher), pagination);
        } else {
            result = repo.findAll(pagination);
        }
        List<Resource> content = result.getContent();
        service.emptyJoinRes(content);
        content = service.tree(content);
        StaticLog.info(JSON.toJSONString(content,true));
        // 重新将content 生成tree
        result = new PageImpl(content,pagination,result.getTotalElements());
        return new Message("success", result);
    }





    @DeleteMapping("/{id}")
    @ResponseBody
    public Message delete(@PathVariable("id") Integer id) {
        repo.deleteById(id);
        return new Message("success", null);
    }
}
