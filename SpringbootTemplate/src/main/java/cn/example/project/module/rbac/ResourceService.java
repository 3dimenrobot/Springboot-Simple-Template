package cn.example.project.module.rbac;

import cn.example.project.module.base.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ResourceService {


    @Autowired
    ResourceRepo repo;


    // 清空关联属性
    public void emptyJoinRes(List<Resource> content){
        // 防止查询引用，转为json产生循环
        for (Resource resource : content) {
            if(resource.getParent() != null){
                resource.setParent(new Resource(resource.getParent().getId()));
            }
            resource.setChildren(null);
        }
    }

    // 清空关联属性
    public void emptyJoinRoles(List<Role> content){
        for (Role role : content) {
            // 防止查询引用，转为json产生循环
            emptyJoinRes(role.getResources());
        }
    }

    // 清空关联属性
    public void emptyJoinUsers(List<User> content){
        for (User user : content) {
            // 防止查询引用，转为json产生循环
            emptyJoinRoles(user.getRoles());
        }
    }


    // 资源集合生成树
    public void setNodeToParent(Resource child,List<Resource> content){
        if(child.getParent() == null){
            return;
        }
        for (Resource parent : content) {
            if(
                    String.valueOf(child.getParent().getId()).equals(String.valueOf(parent.getId()))
            ){
                child.setParent(new Resource(parent.getId()));

                List<Resource> children = parent.getChildren();
                if(children == null){
                    children = new ArrayList();
                }
                children.add(child);
                parent.setChildren(children);
                break;
            }
        }
    }
    public List<Resource> tree(List<Resource> content) {
        for (Resource resource : content) {
            setNodeToParent(resource,content);
        }
        List<Resource> zeroLevels = new ArrayList<>();
        for (Resource resource : content) {
            if(resource.getParent() == null){
                zeroLevels.add(resource);
            }
        }

        return zeroLevels;
    }


    /**
     * 创建菜单树，按钮权限暂时不考虑
     * @return
     */
    public List<Resource> createMenuTree(List<Role> roles){

        Set<Integer> resIds = new HashSet<>();
        List<Resource> filterResouces = new ArrayList<>();
        for (Role role : roles) {
            List<Resource> resources = role.getResources();
            for (Resource resource : resources) {
                // 保存过一次，并且不是按钮
                if(!resIds.contains(resource.getId()) && resource.getLevel() != ResourceLevel.Button_Method){
                    filterResouces.add(resource);
                }
            }
        }
        emptyJoinRes(filterResouces);
        filterResouces = tree(filterResouces);
        // 得到目录和菜单级别的数据
        return filterResouces;
    }

}
