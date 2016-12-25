package org.bitvector.microservice3;

import java.util.List;

public interface ProductDAI {
    List<ProductEntity> getAllProducts();

    ProductEntity getProductById(Integer id);

    void addProduct(ProductEntity product);

    void updateProduct(ProductEntity product);

    void deleteProduct(ProductEntity product);
}