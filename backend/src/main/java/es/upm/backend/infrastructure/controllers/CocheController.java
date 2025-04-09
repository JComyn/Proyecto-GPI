package es.upm.backend.infrastructure.controllers;

import es.upm.backend.application.dto.CreateCocheDto;
import es.upm.backend.application.dto.ListaCoches;
import es.upm.backend.application.services.CocheService;
import es.upm.backend.domain.entities.Coche;
import es.upm.backend.infrastructure.mapper.CocheMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coches")
@Tag(name = "API de Coches", description = "Operaciones CRUD y consultas sencillas sobre entidades Coche")
public class CocheController {

    private final CocheService cocheService;

    public CocheController(CocheService cocheService){
        this.cocheService = cocheService;
    }

    @GetMapping()
    public ResponseEntity<ListaCoches> findAll(){
        List<Coche> coches = cocheService.findAll();
        return ResponseEntity.ok(new ListaCoches(coches));
    }

    @GetMapping("/{idOficina}")
    public ResponseEntity<ListaCoches> findByIdOficina(@PathVariable Long idOficina){
        List<Coche> coches = cocheService.findAllByOficinaId(idOficina);
        return ResponseEntity.ok(new ListaCoches(coches));
    }

    @PostMapping("/{idOficina}")
    public ResponseEntity<Coche> create(@PathVariable Long idOficina, @RequestBody CreateCocheDto newCoche){
        Coche coche = cocheService.create(CocheMapper.createDto2Entity(newCoche), idOficina);
        return ResponseEntity.ok(coche);
    }

    @DeleteMapping("/{idCoche}")
    public ResponseEntity<Void> delete(@PathVariable Long idCoche){
        cocheService.delete(idCoche);
        return ResponseEntity.ok().build();
    }


}
