package ra.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Catalog {
    private int catalogId;
    private String catalogName;
    private String descriptions;
    private List<Product> products;

    public Catalog() {
        this.products = new ArrayList<>();
    }

    public Catalog(int catalogId, String catalogName, String descriptions) {
        this.catalogId = catalogId;
        this.catalogName = catalogName;
        this.descriptions = descriptions;
    }

    public int getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(int catalogId) {
        this.catalogId = catalogId;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Catalog catalog = (Catalog) o;
        return catalogId == catalog.catalogId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(catalogId);
    }

    @Override
    public String toString() {
        return "Catalog ID: " + catalogId + ", Catalog Name: " + catalogName;
    }
}