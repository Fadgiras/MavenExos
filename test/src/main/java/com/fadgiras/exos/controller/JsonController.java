package com.fadgiras.exos.controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.*;

//Utile pour forcer le type de retour
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RestController;

import com.fadgiras.exos.model.Employee;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;



@RestController
public class JsonController{

        @RequestMapping(value="/json", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String index()/*Mettre le Throws ici pour ObjectMapper */ throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        
        List<String> list=new ArrayList<String>();  
        list.add("Hello");
        list.add("World !");
        
        return objectMapper.writeValueAsString(list);
	}

        
        //Version sale du serializer
        public String customJson(List<String> list){
                String res = new String("");
                res += "{"; 
                int i = 0;
                for (Object item : list) {
                        res += "\"";
                        res += Integer.toString(i);
                        res += "\"";
                        res += ":";
                        res += "\"";
                        res += item;
                        res += "\"";
                        res += ",";
                        i++;
                }
                res = res.substring(0,res.length()-1); 
                res +="}";
                return res;
        }

        @RequestMapping(value = "/employee/toJSON", method = RequestMethod.POST)
        public String transformer(Model model, @ModelAttribute("Employee") Employee employee) throws JsonProcessingException{
                ObjectMapper objectMapper= new ObjectMapper();

                model.addAttribute("Employee", employee);
                
                System.out.println(objectMapper.writeValueAsString(employee));
                
                return objectMapper.writeValueAsString(employee);
        }
}