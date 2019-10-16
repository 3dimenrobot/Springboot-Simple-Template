package cn.example.project.module.corn;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface NewsItemRepo extends JpaRepository<NewsItem,Integer> {
}
