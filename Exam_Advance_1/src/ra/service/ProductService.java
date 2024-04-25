package ra.service;

import java.util.*;

import ra.model.Catalog;
import ra.model.Product;

public class ProductService implements IGenericService<Product, String> {
    private List<Product> products = new ArrayList<>();
    private CatalogService catalogService;

    public ProductService(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @Override
    public List<Product> getAll() {
        return products;
    }

    @Override
    public void save(Product product) {
        int index = products.indexOf(product);
        if (index != -1) {
            products.set(index, product);
        } else {
            products.add(product);
        }
    }

    @Override
    public Product findById(String id) {
        for (Product product : products) {
            if (product.getProductId().equals(id)) {
                return product;
            }
        }
        return null;
    }

    @Override
    public void delete(String id) {
        Product product = findById(id);
        if (product != null) {
            products.remove(product);
        }
    }

    public void addProducts(int numProducts) {
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < numProducts; i++) {
            Product product = new Product();
            String productId;
            do {
                System.out.print("Mời bạn nhập mã sách (bắt đầu bằng chữ P và thêm 4 ký tự số): ");
                productId = scanner.nextLine();
            } while (!productId.matches("P\\d{4}"));

            product.setProductId(productId);

            String productName;
            do {
                System.out.print("Mời bạn nhập tên sách (Không được để trống): ");
                productName = scanner.nextLine();
            } while (productName.isEmpty());

            product.setProductName(productName);

            double productPrice;
            do {
                System.out.print("Mời bạn nhập giá (phải lớn hơn 0): ");
                productPrice = scanner.nextDouble();
            } while (productPrice <= 0);

            product.setProductPrice(productPrice);

            int stock;
            do {
                System.out.print("Mời bạn nhập số lượng sách (ít nhất là 10): ");
                stock = scanner.nextInt();
                scanner.nextLine(); // Add this line to consume the newline
            } while (stock < 10);

            product.setStock(stock);

            System.out.print("Mời bạn nhập mô tả sách: ");
            product.setDescription(scanner.nextLine());

            Catalog catalog = null;
            while (catalog == null) {
                System.out.print("Mời bạn nhập tên danh mục mà bạn muốn bỏ sách vào (không được để null):");
                String catalogName = scanner.next();
                catalog = catalogService.getCatalogByName(catalogName);
                if (catalog == null) {
                    System.out.println("Không tìm thấy danh mục. Tạo mới danh mục.");
                    Catalog newCatalog = new Catalog();
                    newCatalog.setCatalogName(catalogName);
                    catalogService.save(newCatalog);
                    catalog = newCatalog;
                }
            }

            product.setCatalog(catalog);

            System.out.print("Mời bạn nhập trạng thái sách (true/false): ");
            product.setStatus(scanner.nextBoolean());

            save(product);
            System.out.println("Thêm sách thành công!");
        }
    }


    public void displayAllProducts() {
        List<Product> allProducts = getAll();
        if (allProducts.isEmpty()) {
            System.out.println("Không có sản phẩm nào trong danh sách.");
        } else {
            for (Product product : allProducts) {
                System.out.println(product);
            }
        }
    }

    public void sortProductsByPrice() {
        Collections.sort(products, Comparator.comparingDouble(Product::getProductPrice).reversed());
        System.out.println("Đã sắp xếp xong.");
    }

    public void deleteProduct(String id) {
        Product product = findById(id);
        if (product != null) {
            delete(id);
            System.out.println("Sản phẩm có ID là " + id + " đã bị xóa.");
        } else {
            System.out.println("không tìm thấy sản phẩm có ID là " + id + ".");
        }
    }

    public void searchProductByName(String name) {
        for (Product product : getAll()) {
            if (product.getProductName().equals(name)) {
                System.out.println(product);
            }
        }
    }

    public void editProduct(String id, String newName, double newPrice, String newDescription, int newStock, Catalog newCatalog, boolean newStatus) {
        Product product = findById(id);
        if (product != null) {
            product.setProductName(newName);
            product.setProductPrice(newPrice);
            product.setDescription(newDescription);
            product.setStock(newStock);
            product.setCatalog(newCatalog);
            product.setStatus(newStatus);
            save(product);
        }
    }

    public Product getProductById(String productId) {
        for (Product product : products) {
            if (product.getProductId().equals(productId)) {
                return product;
            }
        }
        return null;
    }

    public Product getProductByName(String productName) {
        for (Product product : products) {
            if (product.getProductName().equals(productName)) {
                return product;
            }
        }
        return null;
    }
}