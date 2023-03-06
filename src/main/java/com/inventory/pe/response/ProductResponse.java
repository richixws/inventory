package com.inventory.pe.response;

import com.inventory.pe.model.Product;
import lombok.Data;

import java.util.List;

@Data
public class ProductResponse {

      private List<Product> product;

}
