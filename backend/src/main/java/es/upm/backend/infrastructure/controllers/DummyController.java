package es.upm.backend.infrastructure.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dummy")
public class DummyController {

    @GetMapping()
    public ResponseEntity<String> dummy(){
        return ResponseEntity.ok("¡La API funciona!");
    }
}
