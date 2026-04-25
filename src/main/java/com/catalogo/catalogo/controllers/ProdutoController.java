package com.catalogo.catalogo.controllers;

import com.catalogo.catalogo.models.ProdutoModel;
import com.catalogo.catalogo.services.CategoriaService;
import com.catalogo.catalogo.services.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/novo")
    public String exibirFormulario(Model model) {
        model.addAttribute("produto", new ProdutoModel());
        model.addAttribute("categorias", categoriaService.listarTodos());
        return "cadastro-produto";
    }

    @GetMapping("/editar/{id}")
    public String exibirEdicao(@PathVariable long id, Model model) {
        model.addAttribute("produto", service.buscarPorId(id));
        model.addAttribute("categorias", categoriaService.listarTodos());
        return "editar-produto";
    }

    @PostMapping("/salvar")
    public String salvarProduto(
            @Valid @ModelAttribute("produto") ProdutoModel produto,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("produto", produto);
            model.addAttribute("categorias", categoriaService.listarTodos());
            return produto.getIdProduto() != null ? "editar-produto" : "cadastro-produto";
        }

        try {
            service.salvar(produto);
        } catch (IllegalArgumentException e) {
            model.addAttribute("erro", e.getMessage());
            model.addAttribute("categorias", categoriaService.listarTodos());
            return produto.getIdProduto() != null ? "editar-produto" : "cadastro-produto";
        }

        return "redirect:/produtos";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable long id) {
        service.excluir(id);
        return "redirect:/produtos";
    }

    @GetMapping
    public String listarProdutos(
            @RequestParam(required = false) String busca,
            @RequestParam(required = false) Long idCategoria,
            Model model) {

        model.addAttribute("produtos", service.listarComFiltros(busca, idCategoria));
        model.addAttribute("busca", busca);
        model.addAttribute("idCategoria", idCategoria);
        model.addAttribute("categorias", categoriaService.listarTodos());
        return "lista-produtos";
    }
}
