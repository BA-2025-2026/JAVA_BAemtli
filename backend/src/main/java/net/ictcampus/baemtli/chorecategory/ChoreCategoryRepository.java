package net.ictcampus.baemtli.chorecategory;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChoreCategoryRepository extends CrudRepository<ChoreCategory, Integer> {
    List<ChoreCategory> findAll();

    Optional<ChoreCategory> findByName(String name);
}
