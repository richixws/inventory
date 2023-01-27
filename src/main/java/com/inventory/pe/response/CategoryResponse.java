package com.inventory.pe.response;

import com.inventory.pe.model.Category;
import lombok.Data;

import java.util.List;

@Data
public class CategoryResponse {

    private List<Category> category;
}
