package ed.lab.ed1labo04.repository;

import ed.lab.ed1labo04.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import ed.lab.ed1labo04.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
@Repository

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

}