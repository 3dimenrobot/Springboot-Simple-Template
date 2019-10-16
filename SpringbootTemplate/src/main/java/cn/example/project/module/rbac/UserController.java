package cn.example.project.module.rbac;

import cn.example.project.module.base.Message;
import cn.example.project.module.base.PageHelper;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@Api(tags = "100 用户")
@RestController
@RequestMapping("/rbac/user")
public class UserController {

    @Autowired
    private UserRepo repo;

    @PostMapping("/")
    @ResponseBody
    public Message save(User entity) {
        repo.save(entity);
        return new Message("success", entity);
    }

    @GetMapping
    @ResponseBody
    public Message getPagedList(PageHelper<User> page) {
        User entity = page.getEntity();
        ExampleMatcher matcher = page.getEntityLikeMatcher();
        Pageable pagination = page.getPagination();

        Page<User> result = null;
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
