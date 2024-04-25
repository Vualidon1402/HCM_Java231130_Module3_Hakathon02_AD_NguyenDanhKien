package ra.service;

import ra.model.CartItem;
import ra.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CartService {
    private ProductService productService;
    private List<CartItem> cartItems;

    public CartService(ProductService productService) {
        this.productService = productService;
        this.cartItems = new ArrayList<>();
    }

    public void displayAllProducts() {
        productService.displayAllProducts();
    }

    public void addProductToCart(String productId, int quantity) {
        Product product = productService.getProductById(productId);
        if (product == null) {
            System.out.println("Không thấy sách.");
            return;
        }
        if (product != null && product.getStock() >= quantity) {
            CartItem existingCartItem = findCartItemByProductId(productId);
            if (existingCartItem != null) {
                existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
            } else {
                cartItems.add(new CartItem(cartItems.size() + 1, product.getProductName(), product.getProductPrice(), quantity));
            }
            product.setStock(product.getStock() - quantity);
        } else {
            System.out.println("Hết sách.");
        }
    }

    public void displayCartItems() {
        for (CartItem cartItem : cartItems) {
            System.out.println(cartItem);
        }
    }

    public void updateCartItemQuantity(int cartItemId) {
        CartItem cartItem = findCartItemById(cartItemId);
        if (cartItem == null) {
            System.out.println("Không tìm thấy giỏ hàng.");
            return;
        }

        System.out.print("Nhập số lượng muốn thêm: ");
        Scanner scanner = new Scanner(System.in);
        int additionalQuantity = scanner.nextInt();

        int newQuantity = cartItem.getQuantity() + additionalQuantity;
        cartItem.setQuantity(newQuantity);

        Product product = productService.getProductById(cartItem.getProductId());
        if (product == null) {
            System.out.println("Không tìm thấy sách.");
            return;
        }

        if (product.getStock() < additionalQuantity) {
            System.out.println("Hết sách.");
            return;
        }

        product.setStock(product.getStock() - additionalQuantity);
    }

    public void removeCartItem(int cartItemId) {
        CartItem cartItem = findCartItemById(cartItemId);
        if (cartItem == null) {
            System.out.println("Không tìm thấy giỏ hàng.");
            return;
        }

        int newQuantity = cartItem.getQuantity() - 1;
        if (newQuantity <= 0) {
            cartItems.remove(cartItem);
        } else {
            cartItem.setQuantity(newQuantity);
        }

        Product product = productService.getProductById(cartItem.getProductId());
        if (product == null) {
            System.out.println("Không tìm thấy sách.");
            return;
        }

        product.setStock(product.getStock() + 1);
    }

    public void removeAllCartItems() {
        for (CartItem cartItem : cartItems) {
            Product product = productService.getProductByName(cartItem.getProduct());
            if (product != null) {
                product.setStock(product.getStock() + cartItem.getQuantity());
            }
        }
        cartItems.clear();
    }

    private CartItem findCartItemByProductId(String productId) {
        for (CartItem cartItem : cartItems) {
            Product product = productService.getProductById(productId);
            if (product != null && cartItem.getProduct().equals(product.getProductName())) {
                return cartItem;
            }
        }
        return null;
    }

    private CartItem findCartItemById(int cartItemId) {
        for (CartItem cartItem : cartItems) {
            if (cartItem.getCartItemId() == cartItemId) {
                return cartItem;
            }
        }
        return null;
    }
}