package cn.example.project.module.corn;

import cn.example.project.module.base.Message;
import cn.example.project.module.base.PageHelper;
import cn.example.project.module.rbac.Resource;
import cn.hutool.log.StaticLog;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(tags = "影响因子")
@RestController
@RequestMapping("/corn/factor")
public class FactorController {

    @Autowired
    private FactorRepo repo;

    @GetMapping("/test")
    @ResponseBody
    public Message test() {
        return new Message("success", "test message");
    }

    @PostMapping("")
    @ResponseBody
    public Message save(@RequestBody Factor item) {
        repo.save(item);
        return new Message("success", item);
    }

    @GetMapping
    @ResponseBody
    public Message getPagedList(PageHelper page, Factor entity) {
        ExampleMatcher matcher = PageHelper.getEntityLikeMatcher(entity);
        Pageable pagination = page.getPagination();

        Page<Factor> result = null;
        if (matcher != null) {
            result = repo.findAll(Example.of(entity, matcher), pagination);
        } else {
            result = repo.findAll(pagination);
        }
        // 重新将content 生成tree
        return new Message("success", result);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public Message delete(@PathVariable("id") Integer id) {
        repo.deleteById(id);
        return new Message("success", null);
    }
}
