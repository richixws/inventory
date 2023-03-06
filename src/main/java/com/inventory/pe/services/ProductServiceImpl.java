package com.inventory.pe.services;

import com.inventory.pe.dao.CategoryRepository;
import com.inventory.pe.dao.ProductRepository;
import com.inventory.pe.model.Category;
import com.inventory.pe.model.Product;
import com.inventory.pe.response.CategoryResponseRest;
import com.inventory.pe.response.ProductResponseRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements IProductService{

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ProductResponseRest> search() {

        ProductResponseRest response=new ProductResponseRest();
        try {
            List<Product> products=productRepository.findAll();
            response.getProductResponse().setProduct(products);
            response.setMetadata("Respuesta ok","00",  "Respuesta Exitosa");
        }catch (Exception e){
            response.setMetadata("Respuesta nook","-1","Respuesta Incorrecta");
            e.getStackTrace();
            return new ResponseEntity<ProductResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<ProductResponseRest>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ProductResponseRest> searchById(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<ProductResponseRest> save(Product product, Long id) {

        ProductResponseRest response=new ProductResponseRest();
        List<Product> list=new ArrayList<>();
        try {
           //search category to set in the product object
            Optional<Category> category=categoryRepository.findById(id);
            if(category.isPresent()){
               product.setCategory(category.get());
            }else{
                response.setMetadata("Respuesta nook","-1","Categoria no encontrada asociada al producto");
                return new ResponseEntity<ProductResponseRest>(response,HttpStatus.NOT_FOUND);
            }
            //saved the product
            Product productsave=productRepository.save(product);
            if(productsave !=null){
                list.add(productsave);
                response.getProductResponse().setProduct(list);
                response.setMetadata("Respuesta ok","00","Producto Guardado");
            }else{
                response.setMetadata("Respuesta nook","-1","Producto no Guardado");
                return new ResponseEntity<ProductResponseRest>(response,HttpStatus.BAD_REQUEST);
            }

        }catch (Exception e){
            response.setMetadata("Respuesta nook","-1","Error al consultar producto");
            e.getStackTrace();
            return new ResponseEntity<ProductResponseRest>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<ProductResponseRest>(response,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ProductResponseRest> update(Long id, Product product) {
        return null;
    }

    @Override
    public ResponseEntity<ProductResponseRest> deleteById(Long id) {
        return null;
    }

}
