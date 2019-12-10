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


@Api(tags = "当前现状")
@RestController
@RequestMapping("/corn/presentSituation")
public class PresentSituationController {

    @Autowired
    private PresentSituationRepo repo;

    @PostMapping("")
    @ResponseBody
    public Message save(@RequestBody PresentSituation item) {
        repo.save(item);
        return new Message("success", item);
    }

    @GetMapping
    @ResponseBody
    public Message getPagedList(PageHelper page, PresentSituation entity) {
        ExampleMatcher matcher = PageHelper.getEntityLikeMatcher(entity);
        Pageable pagination = page.getPagination();

        Page<PresentSituation> result = null;
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
