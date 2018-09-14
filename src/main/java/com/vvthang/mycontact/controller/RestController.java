package com.vvthang.mycontact.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vvthang.mycontact.entity.Contact;
import com.vvthang.mycontact.service.ContactService;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class RestController {
    
    @Autowired
    private ContactService contactService;
    
    @RequestMapping(value="/findall")
    public ResponseEntity<Iterable<Contact>> findAll(){
        
        Iterable<Contact> contacts = contactService.findAll();
        
        return new ResponseEntity<Iterable<Contact>>(contacts, HttpStatus.OK);
        
    }

}
