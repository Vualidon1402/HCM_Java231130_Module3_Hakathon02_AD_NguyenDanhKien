package ra.service;

import ra.model.Catalog;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CatalogService implements IGenericService<Catalog, Integer> {
    private List<Catalog> catalogs = new ArrayList<>();

    @Override
    public List<Catalog> getAll() {
        return catalogs;
    }

    @Override
    public void save(Catalog catalog) {
        int index = catalogs.indexOf(catalog);
        if (index != -1) {
            catalogs.set(index, catalog);
        } else {
            catalogs.add(catalog);
        }
    }

    @Override
    public Catalog findById(Integer id) {
        for (Catalog catalog : catalogs) {
            if (catalog.getCatalogId() == id) {
                return catalog;
            }
        }
        return null;
    }

    @Override
    public void delete(Integer id) {
        Catalog catalog = findById(id);
        if (catalog != null) {
            if (catalog.getProducts().isEmpty()) {
                catalogs.remove(catalog);
            } else {
                System.out.println("Không thể xóa danh mục có id là " + id + " vì nó có sản phẩm liên quan.");
            }
        } else {
            System.out.println("Không tìm thấy danh mục " + id + " để xóa.");
        }
    }

    public Catalog getCatalogByName(String name) {
        for (Catalog catalog : catalogs) {
            if (catalog.getCatalogName().equals(name)) {
                return catalog;
            }
        }
        return null;
    }

    public void addCatalogs(int numCatalogs) {
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < numCatalogs; i++) {
            Catalog catalog = new Catalog();
            System.out.print("Mời bạn nhập mã danh mục (Chỉ được nhập số): ");
            catalog.setCatalogId(scanner.nextInt());
            scanner.nextLine();

            String catalogName;
            do {
                System.out.print("Mời bạn thêm tên danh mục (Không được để trống): ");
                catalogName = scanner.nextLine();
            } while (catalogName.isEmpty());

            catalog.setCatalogName(catalogName);

            String descriptions;
            do {
                System.out.print("Mời bạn mô tả danh mục (không được để trống): ");
                descriptions = scanner.nextLine();
            } while (descriptions.isEmpty());

            catalog.setDescriptions(descriptions);

            save(catalog);
            System.out.println("Đã thêm danh mục thành công");
        }
    }

    public void displayAllCatalogs() {
        List<Catalog> allCatalogs = getAll();
        if (allCatalogs.isEmpty()) {
            System.out.println("Danh sách danh mục rỗng.");
        } else {
            for (Catalog catalog : allCatalogs) {
                System.out.println(catalog);
            }
        }
    }

    public void editCatalogName(int id, String newName) {
        Catalog catalog = findById(id);
        if (catalog != null) {
            catalog.setCatalogName(newName);
            save(catalog);
            System.out.println("Đã sửa thành công");
            displayAllCatalogs();
        }
    }

    public void deleteCatalog(int id) {
        delete(id);
    }
}