package com.inventory.pe.controller;

import com.inventory.pe.model.Product;
import com.inventory.pe.response.ProductResponseRest;
import com.inventory.pe.services.IProductService;
import com.inventory.pe.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/v1")
public class ProductRestController {

    @Autowired
    private IProductService productService;

    /**
     *
     * @param picture
     * @param name
     * @param price
     * @param account
     * @param categoryId
     * @return
     * @throws IOException
     */
    @PostMapping("/products")
    public ResponseEntity<ProductResponseRest> save(@RequestParam("picture") MultipartFile picture,
                                                    @RequestParam("name") String name,
                                                    @RequestParam("price") int price,
                                                    @RequestParam("account") int account,
                                                    @RequestParam("categoryId") Long categoryId) throws IOException {

        Product product=new Product();
        product.setName(name);
        product.setAccount(account);
        product.setPrice(price);
        product.setPicture(Util.compressZlib(picture.getBytes()));

        ResponseEntity<ProductResponseRest> response=productService.save(product,categoryId);
        return response;

    }

}
