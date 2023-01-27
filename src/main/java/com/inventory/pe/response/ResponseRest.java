package com.inventory.pe.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


public class ResponseRest{

    private ArrayList<HashMap<String, String>> metadata=new ArrayList<>();

    public ArrayList<HashMap<String, String>> getMetadata(){
         return  metadata;
    }

    public void setMetadata(String type, String code, String date){
         HashMap<String, String> map =new HashMap<>();
         map.put("type",type);
         map.put("code",code);
         map.put("date",date);
    }



}
