package org.bitvector.microservice3;

import java.util.List;

public interface ProductDAI {
    List<ProductEntity> getAllProducts();

    ProductEntity getProductById(Integer id);

    Boolean saveProduct(ProductEntity product);

    Boolean updateProduct(ProductEntity product);

    Boolean deleteProduct(ProductEntity product);
}
