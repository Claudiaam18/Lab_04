package ed.lab.ed1labo04.controller;

import ed.lab.ed1labo04.entity.CartEntity;
import ed.lab.ed1labo04.model.CreateCartRequest;
import ed.lab.ed1labo04.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<?> createCart(@RequestBody CreateCartRequest createCartRequest) {
        try {
            CartEntity createdCart = cartService.createCart(createCartRequest);
            return new ResponseEntity<>(createdCart, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCartById(@PathVariable Long id) {
        try {
            CartEntity cart = cartService.getCartById(id);
            return ResponseEntity.ok(cart);
        } catch (IllegalArgumentException e) {
            // Mensaje esperado: "Cart not found"
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}