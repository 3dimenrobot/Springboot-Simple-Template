package cn.example.project.module.rbac;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceService {
    @Autowired
    ResourceRepo repo;


}
