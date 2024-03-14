package mart.mono.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface CartRepository extends JpaRepository<CartItemEntity, UUID> {

}