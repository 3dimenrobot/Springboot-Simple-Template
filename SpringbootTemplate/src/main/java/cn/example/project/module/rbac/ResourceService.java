package cn.example.project.module.rbac;

import cn.example.project.module.base.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceService {


    @Autowired
    ResourceRepo repo;




    /**
     * 查询权限树
     */
    Resource loadRootTree(){
        Resource root = repo.queryFirstByUrlEquals(AppConstants.Root_Resource_Url);
        return root;
    }

}
