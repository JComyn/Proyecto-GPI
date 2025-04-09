package es.upm.backend.infrastructure.controllers;

import es.upm.backend.application.dto.CreateOficinaDto;
import es.upm.backend.application.dto.ListaOficinas;
import es.upm.backend.application.services.OficinaService;
import es.upm.backend.domain.entities.Oficina;
import es.upm.backend.infrastructure.mapper.OficinaMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/oficinas")
@Tag(name = "API de Oficinas", description = "CRUD basico para entidades Oficina")
public class OficinaController {

    private final OficinaService oficinaService;

    public OficinaController(OficinaService oficinaService){
        this.oficinaService = oficinaService;
    }

    @GetMapping()
    public ResponseEntity<ListaOficinas> findAll(){
        List<Oficina> oficinas = oficinaService.findAll();
        return ResponseEntity.ok(new ListaOficinas(oficinas));
    }

    @DeleteMapping("/{idOficina}")
    public ResponseEntity<Void> deleteById(@PathVariable Long idOficina){
        oficinaService.delete(idOficina);
        return ResponseEntity.ok().build();
    }

    @PostMapping()
    public ResponseEntity<Oficina> create(@RequestBody CreateOficinaDto newOficina){
        Oficina oficina = oficinaService.create(OficinaMapper.createDto2Entity(newOficina));
        return ResponseEntity.ok(oficina);
    }
}
