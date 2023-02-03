package com.inventory.pe.controller;

import com.inventory.pe.model.Category;
import com.inventory.pe.response.CategoryResponseRest;
import com.inventory.pe.services.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/v1")
public class CategoryRestController {

    @Autowired
    private ICategoryService iCategoryService;

    @GetMapping("/categories")
    public ResponseEntity<CategoryResponseRest> searhCategories(){

        ResponseEntity<CategoryResponseRest> response=iCategoryService.search();
        return response;
    }

    @GetMapping("/categories/{id}")
    public  ResponseEntity<CategoryResponseRest> searchById(@PathVariable Long id){

        ResponseEntity<CategoryResponseRest> responseId=iCategoryService.searchById(id);
        return responseId;
    }

    @PostMapping("/categories")
    public ResponseEntity<CategoryResponseRest> save(@RequestBody Category category){

        ResponseEntity<CategoryResponseRest> response=iCategoryService.save(category);
        return response;

    }


    @PutMapping("/categories/{id}")
    public  ResponseEntity<CategoryResponseRest> update(@PathVariable Long id, @RequestBody Category category){

        ResponseEntity<CategoryResponseRest> response=iCategoryService.update(id,category);
        return response;
    }

    /**
     *delete Category
     * @param id
     * @return
     */
    @DeleteMapping("/categories/{id}")
    public ResponseEntity<CategoryResponseRest> delete(@PathVariable Long id){

        ResponseEntity<CategoryResponseRest> response=iCategoryService.deleteById(id);
        return  response;
    }

}
