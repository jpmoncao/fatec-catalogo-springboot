package com.catalogo.catalogo.controllers;

import com.catalogo.catalogo.models.CategoriaModel;
import com.catalogo.catalogo.services.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService service;

    @GetMapping
    public String listar(@RequestParam(required = false) String busca, Model model) {
        if (busca != null && !busca.isEmpty()) {
            model.addAttribute("categorias", service.buscarPorNome(busca));
        } else {
            model.addAttribute("categorias", service.listarTodos());
        }
        model.addAttribute("busca", busca);
        return "lista-categorias";
    }

    @GetMapping("/novo")
    public String exibirFormulario(Model model) {
        model.addAttribute("categoria", new CategoriaModel());
        return "cadastro-categoria";
    }

    @GetMapping("/editar/{id}")
    public String exibirEdicao(@PathVariable long id, Model model) {
        model.addAttribute("categoria", service.buscarPorId(id));
        return "cadastro-categoria";
    }

    @PostMapping("/salvar")
    public String salvarCategoria(
            @Valid @ModelAttribute("categoria") CategoriaModel categoria,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("categoria", categoria);
            return "cadastro-categoria";
        }

        try {
            service.salvar(categoria);
        } catch (IllegalArgumentException e) {
            model.addAttribute("erro", e.getMessage());
            return "cadastro-categoria";
        }

        return "redirect:/categorias";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable long id, RedirectAttributes redirectAttributes) {
        try {
            service.excluir(id);
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/categorias";
    }
}
