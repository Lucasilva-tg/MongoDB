package com.example.Clima.controller;

import com.example.Clima.model.ClimaEntity;
import com.example.Clima.service.ClimaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clima")
public class ClimaController {
    @Autowired
    private ClimaService climaService;

    @GetMapping
    public List<ClimaEntity>obterTodos() {return climaService.obterTodos();}

    @GetMapping("/{id}")
    public ClimaEntity obterPorId(@PathVariable String id) {return climaService.obterPorId(id);}

    @PostMapping
    public ClimaEntity inserir(@RequestBody ClimaEntity clima) {return climaService.inserir(clima);}

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable String id) {climaService.excluir(id); }

    @GetMapping("/prever")
    public String preverTempo() {
        return climaService.preverTempo();
    }
}
