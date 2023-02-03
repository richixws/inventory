package com.inventory.pe.services;

import com.inventory.pe.dao.CategoryRepository;
import com.inventory.pe.model.Category;
import com.inventory.pe.response.CategoryResponse;
import com.inventory.pe.response.CategoryResponseRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements ICategoryService{

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<CategoryResponseRest> search() {

        CategoryResponseRest response = new CategoryResponseRest();
        try {
            List<Category> category=categoryRepository.findAll();
            response.getCategoryResponse().setCategory(category);
            response.setMetadata("Respuesta ok","00",  "Respuesta Exitosa");
        }catch (Exception e){
            response.setMetadata("Respuesta nook","-1","Respuesta Incorrecta");
            e.getStackTrace();
            return new ResponseEntity<CategoryResponseRest>(response,HttpStatus.INTERNAL_SERVER_ERROR);

        }
        return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<CategoryResponseRest> searchById(Long id) {

        CategoryResponseRest response=new CategoryResponseRest();
        List<Category> list= new ArrayList<>();

        try {

            Optional<Category> category=categoryRepository.findById(id);
            if (category.isPresent()){
                list.add(category.get());
                response.getCategoryResponse().setCategory(list);
                response.setMetadata("Respuesta ok","00","Categoria encontrada");
            }else{
                response.setMetadata("Respuesta nook","-1","Categoria no encontrada");
                return new ResponseEntity<CategoryResponseRest>(response,HttpStatus.NOT_FOUND);
            }

        }catch (Exception e){
            response.setMetadata("Respuesta nook","-1","Error al consultar por Id");
            e.getStackTrace();
            return new ResponseEntity<CategoryResponseRest>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<CategoryResponseRest> save(Category category) {

        CategoryResponseRest response=new CategoryResponseRest();
        List<Category> list=new ArrayList<>();

        try {
           Category categorySaved= categoryRepository.save(category);
           if(categorySaved !=null){
              list.add(categorySaved);
              response.getCategoryResponse().setCategory(list);
              response.setMetadata("Respuesta ok","00","Categoria Guardada");
           }else{
               response.setMetadata("Respuesta nook","-1","Categoria no encontrada");
               return new ResponseEntity<CategoryResponseRest>(response,HttpStatus.BAD_REQUEST);
           }

        }catch (Exception e){
            response.setMetadata("Respuesta nook","-1","Error al consultar por Id");
            e.getStackTrace();
            return new ResponseEntity<CategoryResponseRest>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<CategoryResponseRest>(response,HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<CategoryResponseRest> update(Long id, Category category) {

        CategoryResponseRest response = new CategoryResponseRest();
        Category categorynew=new Category();
        List<Category> list=new ArrayList<>();

        try {
            Optional<Category> seachCategory=categoryRepository.findById(id);
            if (seachCategory.isPresent()){
                seachCategory.get().setName(category.getName());
                seachCategory.get().setDescription(category.getDescription());
                Category categoryToUpdate=categoryRepository.save(seachCategory.get());
                if(categoryToUpdate != null){
                    list.add(categoryToUpdate);
                    response.getCategoryResponse().setCategory(list);
                    response.setMetadata("Respuesta ok","00","Categoria Actualizada");
                }else {
                    response.setMetadata("Respuesta nook","-1","Categoria no actualizada");
                    return new ResponseEntity<CategoryResponseRest>(response,HttpStatus.BAD_REQUEST);
                }

            }else{
                response.setMetadata("Respuesta nook","-1","Categoria no encontrada");
                return new ResponseEntity<CategoryResponseRest>(response,HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            response.setMetadata("Respuesta nook","-1","Error al Actualizar categoria");
            e.getStackTrace();
            return new ResponseEntity<CategoryResponseRest>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<CategoryResponseRest>(response,HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<CategoryResponseRest> deleteById(Long id) {

           CategoryResponseRest responseRest = new CategoryResponseRest();
           List<Category> list=new ArrayList<>();

           try {
               categoryRepository.deleteById(id);
               responseRest.setMetadata("respuesta ok","00","Registro eliminado");

           }catch (Exception e){
              responseRest.setMetadata("Respuesta nok","-1","Error al eliminar");
              e.getStackTrace();
              return  new ResponseEntity<CategoryResponseRest>(responseRest,HttpStatus.INTERNAL_SERVER_ERROR);
           }
           return new ResponseEntity<CategoryResponseRest>(responseRest,HttpStatus.OK);
    }

}
