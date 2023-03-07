package com.inventory.pe.services;

import com.inventory.pe.model.Product;
import com.inventory.pe.response.ProductResponseRest;
import org.springframework.http.ResponseEntity;

public interface IProductService {

    public ResponseEntity<ProductResponseRest> save(Product product, Long categoryId);
    public ResponseEntity<ProductResponseRest> searchById(Long id);
    public ResponseEntity<ProductResponseRest> searchByName(String name);
    public ResponseEntity<ProductResponseRest> deleteById(Long id);

    public ResponseEntity<ProductResponseRest> search();
    public ResponseEntity<ProductResponseRest> update( Product product,Long categoryId, Long id);


}
