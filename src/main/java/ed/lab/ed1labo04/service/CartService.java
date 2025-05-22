package ed.lab.ed1labo04.service;

import ed.lab.ed1labo04.entity.CartEntity;
import ed.lab.ed1labo04.entity.CartItemEntity;
import ed.lab.ed1labo04.entity.ProductEntity;
import ed.lab.ed1labo04.model.CartItemRequest;
import ed.lab.ed1labo04.model.CreateCartRequest;
import ed.lab.ed1labo04.repository.CartRepository;
import ed.lab.ed1labo04.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public CartEntity createCart(CreateCartRequest createCartRequest) {
        List<CartItemEntity> cartItems = new ArrayList<>();
        double totalPrice = 0;

        for (CartItemRequest itemRequest : createCartRequest.getCartItems()) {
            if (itemRequest.getQuantity() <= 0) {
                throw new IllegalArgumentException("Quantity must be greater than zero");
            }

            ProductEntity product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + itemRequest.getProductId()));

            if (product.getQuantity() < itemRequest.getQuantity()) {
                throw new IllegalArgumentException("Insufficient stock for product id: " + itemRequest.getProductId());
            }

            product.setQuantity(product.getQuantity() - itemRequest.getQuantity());
            productRepository.save(product);

            CartItemEntity cartItem = new CartItemEntity();
            cartItem.setProductId(product.getId());
            cartItem.setName(product.getName());
            cartItem.setPrice(product.getPrice());
            cartItem.setQuantity(itemRequest.getQuantity());

            cartItems.add(cartItem);
            totalPrice += product.getPrice() * itemRequest.getQuantity();
        }

        CartEntity cart = new CartEntity();
        cart.setCartItems(cartItems);
        cart.setTotalPrice(totalPrice);

        for (CartItemEntity cartItem : cartItems) {
            cartItem.setCart(cart);
        }

        return cartRepository.save(cart);
    }

    @Transactional
    public CartEntity getCartById(Long id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found"));
    }
}