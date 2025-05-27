package es.upm.backend.infrastructure.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dummy")
@Tag(name = "Dummy", description = "Controllador destinado a probar la disponibilidad de la API en general")
public class DummyController {

    @GetMapping()
    public ResponseEntity<String> dummy(){
        return ResponseEntity.ok("¡La API funciona!");
    }
}
