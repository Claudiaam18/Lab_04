package ed.lab.ed1labo04.repository;

import ed.lab.ed1labo04.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<CartEntity, Long> {
}