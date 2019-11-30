package cn.example.project.module.rbac;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ResourceRepo extends JpaRepository<Resource,Integer> {
}
