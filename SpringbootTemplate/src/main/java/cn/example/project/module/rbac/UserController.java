package cn.example.project.module.rbac;

import cn.example.project.module.base.Message;
import cn.example.project.module.base.PageHelper;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.rest.webmvc.support.DefaultedPageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * https://stackoverflow.com/questions/671118/what-exactly-is-restful-programming
 * <p>
 * --> /
 * /rbac
 * /rbac/
 * /rbac/user /rbac/role
 * /rbac/user get  {users:[]}
 * /rbac/user post create
 * /rbac/user/1 put update
 * /rbac/user/1 get read
 * /rbac/user/1 delete delete
 */
@Api(tags = "100 用户")
@Secured("ROLE_RBAC")
@RestController
@RequestMapping("/rbac/user")
public class UserController {

    @Autowired
    private UserRepo repo;

//
//


    @GetMapping("")
    @ResponseBody
    public Message list(PageHelper page,User entity) {
        ExampleMatcher matcher = PageHelper.getEntityLikeMatcher(entity);
        Pageable pagination = page.getPagination();

        Page<User> result = null;
        if (matcher != null) {
            result = repo.findAll(Example.of(entity, matcher), pagination);
        } else {
            result = repo.findAll(pagination);
        }
        return new Message("success", result);
    }

   /* @ResponseBody
    public Message list(@PageableDefault(size = 10, direction = Sort.Direction.DESC, sort = "someField") Pageable pageable,User entity) {
        ExampleMatcher matcher = PageHelper.getEntityLikeMatcher(entity);
        Page<User> result = null;
        if (matcher != null) {
            result = repo.findAll(Example.of(entity, matcher), pageable);
        } else {
            result = repo.findAll(pageable);
        }
        return new Message("success", result);
    }*/




    @PostMapping("")
    @ResponseBody
    public Message create(User entity) {
        repo.save(entity);
        return new Message("success", entity);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public Message update(@PathVariable("id") Integer id, User entity) {
        repo.save(entity);
        return new Message("success", entity);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Message read(@PathVariable("id") Integer id) {
        return new Message("success", repo.getOne(id));
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public Message delete(@PathVariable("id") Integer id) {
        repo.deleteById(id);
        return new Message("success", null);
    }


    @DeleteMapping("/testUrlmatcher/{id}") // "/rbac/user/111"
    @ResponseBody
    public Message testUrlmatcher(HttpServletRequest request,@PathVariable("id") Integer id) {
//        AntPathRequestMatcher m = new AntPathRequestMatcher("/rbac/user/testUrlmatcher/{id}");
        AntPathRequestMatcher m = new AntPathRequestMatcher("/rbac/user/testUrlmatcher");
        System.out.println(request.getRequestURI());
        // "/rbac/user/{id}"
        System.out.println(m.matches(request)); //true
        return new Message("success", null);
    }


}
