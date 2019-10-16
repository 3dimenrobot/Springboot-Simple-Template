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


@Api(tags = "影响因子")
@RestController
@RequestMapping("/factor")
public class FactorController {

    @Autowired
    private FactorRepo repo;

    @PostMapping("/")
    @ResponseBody
    public Message save(Factor item) {
        repo.save(item);
        return new Message("success", item);
    }

    @GetMapping("/")
    @ResponseBody
    public Message getPagedList(PageHelper<Factor> page) {
        Factor entity = page.getEntity();
        ExampleMatcher matcher = page.getEntityLikeMatcher();
        Pageable pagination = page.getPagination();

        Page<Factor> result = null;
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
