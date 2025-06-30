package com.example.SmartPayroll.controllers;

import com.example.SmartPayroll.models.Descuento;
import com.example.SmartPayroll.repositories.DescuentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/descuentos")
public class DescuentoController {

    @Autowired
    private DescuentoRepository descuentoRepository;

    @GetMapping
    public List<Descuento> listarDescuentos() {
        return descuentoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Descuento obtenerDescuento(@PathVariable Integer id) {
        return descuentoRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Descuento crearDescuento(@RequestBody Descuento descuento) {
        return descuentoRepository.save(descuento);
    }

    @PutMapping("/{id}")
    public Descuento actualizarDescuento(@PathVariable Integer id, @RequestBody Descuento descuento) {
        descuento.setIdDescuento(id);
        return descuentoRepository.save(descuento);
    }

    @DeleteMapping("/{id}")
    public void eliminarDescuento(@PathVariable Integer id) {
        descuentoRepository.deleteById(id);
    }
}
