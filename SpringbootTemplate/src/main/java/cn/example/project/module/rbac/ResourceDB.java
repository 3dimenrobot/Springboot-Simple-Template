package cn.example.project.module.rbac;

import java.util.ArrayList;
import java.util.List;

public class ResourceDB {
    public static  List<Resource> WHITE_LIST = new ArrayList<>() ;

    /**
     * 查询所有资源
     * @return
     */
    public static List<Resource> findAllResource(){
//      Resource(Integer id,String name, ResourceLevel level, String atomUrl, String url, Resource parent)
        // 系统
        Resource r0 = new Resource(0,"系统",ResourceLevel.Dir_Module,"/","/",null);
        // rbac权限管理
        Resource r01 = new Resource(1,"rbac权限管理",ResourceLevel.Dir_Module,"rbac","/rbac",r0);
        // 用户管理
        Resource r011 = new Resource(11,"用户管理",ResourceLevel.Menu_Entity,"/user","/rbac/user",r01);
        // 用户管理
        Resource r0111 = new Resource(111,"增加用户",ResourceLevel.Button_Method,"","/rbac/user",r011,"GET","list");
        Resource r0112 = new Resource(111,"修改用户",ResourceLevel.Button_Method,"/{*}","/rbac/user/{*}",r011,"PUT","update");

        // 角色管理
        Resource r012 = new Resource(12,"角色管理",ResourceLevel.Menu_Entity,"/role","/rbac/role",r01);
        // 角色管理
        Resource r0121 = new Resource(121,"增加角色",ResourceLevel.Button_Method,"","/rbac/role",r011,"GET","list");
        Resource r0122 = new Resource(121,"修改角色",ResourceLevel.Button_Method,"/{*}","/rbac/role/{*}",r011,"PUT","update");

        // 登录，退出
        Resource r090 = new Resource(90,"登录",ResourceLevel.Button_Method,"/login","/login",r0,"POST","read");
        Resource r091 = new Resource(91,"退出",ResourceLevel.Button_Method,"/logout","/logout",r0,"POST","read");

        List<Resource> resources = new ArrayList<>();

        resources.add(r0);
        resources.add(r01);
        resources.add(r011);
        resources.add(r0111);
        resources.add(r0112);

        resources.add(r012);
        resources.add(r0121);
        resources.add(r0122);

        resources.add(r090);
        resources.add(r091);

       return resources;
    }

    /**
     * 查询所有资源
     * @return
     */
    public static List<Resource> find_RBAC_Resources(){
//      Resource(Integer id,String name, ResourceLevel level, String atomUrl, String url, Resource parent)
        // 系统
        Resource r0 = new Resource(0,"系统",ResourceLevel.Dir_Module,"/","/",null);
        // rbac权限管理
        Resource r01 = new Resource(1,"rbac权限管理",ResourceLevel.Dir_Module,"rbac","/rbac",r0);
        // 用户管理
        Resource r011 = new Resource(11,"用户管理",ResourceLevel.Menu_Entity,"/user","/rbac/user",r01);
        // 用户管理
        Resource r0111 = new Resource(111,"增加用户",ResourceLevel.Button_Method,"","/rbac/user",r011,"GET","list");
        Resource r0112 = new Resource(111,"修改用户",ResourceLevel.Button_Method,"/{*}","/rbac/user/{*}",r011,"PUT","update");

        // 角色管理
        Resource r012 = new Resource(12,"角色管理",ResourceLevel.Menu_Entity,"/role","/rbac/role",r01);
        // 角色管理
        Resource r0121 = new Resource(121,"增加角色",ResourceLevel.Button_Method,"","/rbac/role",r011,"GET","list");
        Resource r0122 = new Resource(121,"修改角色",ResourceLevel.Button_Method,"/{*}","/rbac/role/{*}",r011,"PUT","update");

        List<Resource> resources = new ArrayList<>();

        resources.add(r0);
        resources.add(r01);
        resources.add(r011);
        resources.add(r0111);
        resources.add(r0112);

        resources.add(r012);
        resources.add(r0121);
        resources.add(r0122);

        return resources;
    }
}
