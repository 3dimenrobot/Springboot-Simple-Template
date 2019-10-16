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
@RequestMapping("/presentSituation")
public class PresentSituationController {

    @Autowired
    private PresentSituationRepo repo;

    @PostMapping("/")
    @ResponseBody
    public  Message save(PresentSituation item) {
        repo.save(item);
        return new Message("success", item);
    }

    @GetMapping
    @ResponseBody
    public Message getPagedList(PageHelper<PresentSituation> page) {
        PresentSituation entity = page.getEntity();
        ExampleMatcher matcher = page.getEntityLikeMatcher();
        Pageable pagination = page.getPagination();

        Page<PresentSituation> result = null;
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
