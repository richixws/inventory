package com.inventory.pe.services;

import com.inventory.pe.dao.CategoryRepository;
import com.inventory.pe.dao.ProductRepository;
import com.inventory.pe.model.Category;
import com.inventory.pe.model.Product;
import com.inventory.pe.response.CategoryResponseRest;
import com.inventory.pe.response.ProductResponseRest;
import com.inventory.pe.util.Util;
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

    /**
     *
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ProductResponseRest> search() {

        ProductResponseRest response=new ProductResponseRest();
        List<Product> list= new ArrayList<>();
        List<Product> listAux=new ArrayList<>();

        try {
            listAux =productRepository.findAll();
            if (listAux.size() > 0){
                listAux.stream().forEach((p) -> {
                   byte[] imageDescompressed = Util.decompressZlib(p.getPicture());
                   p.setPicture(imageDescompressed);
                   list.add(p);

                });
                response.getProductResponse().setProduct(list);
                response.setMetadata("Respuesta ok","00",  "Productos encontrados");
            }else{
                response.setMetadata("Respuesta nok","-1",  "Productos no encontrados");
                return new ResponseEntity<ProductResponseRest>(response,HttpStatus.NOT_FOUND);
            }

        }catch (Exception e){
            e.getStackTrace();
            response.setMetadata("Respuesta nook","-1","");
            return new ResponseEntity<ProductResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<ProductResponseRest>(response, HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ProductResponseRest> searchById(Long id) {

        ProductResponseRest response =new ProductResponseRest();
        List<Product> list=new ArrayList<>();

        try {
            Optional<Product> product=productRepository.findById(id);
            if(product.isPresent()){

                byte[] imageDescompressed = Util.decompressZlib(product.get().getPicture());
                product.get().setPicture(imageDescompressed);
                list.add(product.get());
                response.getProductResponse().setProduct(list);
                response.setMetadata("Respuesta Ok", "00","PRODUCTO ENCONTRADO" );
            }else{
                response.setMetadata("Respuesta nook","-1","Producto no encontrada");
                return new ResponseEntity<ProductResponseRest>(response,HttpStatus.NOT_FOUND);

            }
        }catch (Exception e){
            response.setMetadata("Respuesta nook","-1","Error al consultar por Id");
            e.getStackTrace();
            return new ResponseEntity<ProductResponseRest>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }


        return new ResponseEntity<ProductResponseRest>(response,HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ProductResponseRest> searchByName(String name) {

        ProductResponseRest response =new ProductResponseRest();
        List<Product> list=new ArrayList<>();
        List<Product> listAux=new ArrayList<>();

        try {

            //search producto by name
            listAux = productRepository.findByNameContainingIgnoreCase(name);

            if(listAux.size() > 0){

                listAux.stream().forEach( (p)-> {
                    byte[] imageDescompressed = Util.decompressZlib(p.getPicture());
                    p.setPicture(imageDescompressed);
                    list.add(p);
                });


                response.getProductResponse().setProduct(listAux);
                response.setMetadata("Respuesta Ok", "00","PRODUCTOS ENCONTRADOS" );
            }else{
                response.setMetadata("Respuesta nook","-1","Productos no encontrados");
                return new ResponseEntity<ProductResponseRest>(response,HttpStatus.NOT_FOUND);

            }
        }catch (Exception e){
            response.setMetadata("Respuesta nook","-1","Error al consultar por Id");
            e.getStackTrace();
            return new ResponseEntity<ProductResponseRest>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<ProductResponseRest>(response,HttpStatus.OK);
    }

    @Override
    @Transactional
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
    public ResponseEntity<ProductResponseRest> deleteById(Long id) {

        ProductResponseRest response =new ProductResponseRest();

        try {
            productRepository.deleteById(id);
            response.setMetadata("Respuesta Ok", "00","´producto eliminado" );

        }catch (Exception e){
            response.setMetadata("Respuesta nook","-1","Error al eliminar por Id");
            e.getStackTrace();
            return new ResponseEntity<ProductResponseRest>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<ProductResponseRest>(response,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ProductResponseRest> update(Long id, Product product) {
        return null;
    }


}
