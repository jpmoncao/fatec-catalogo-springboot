package com.catalogo.catalogo.config;

import com.catalogo.catalogo.security.UsuarioPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class NavModelAdvice {

    @ModelAttribute
    public void navContext(HttpServletRequest request, Model model) {
        String path = pathWithinApplication(request);
        model.addAttribute("navArea", "");
        model.addAttribute("navProdutosSub", "");
        model.addAttribute("navCategoriasSub", "");
        model.addAttribute("nomeUsuarioExibicao", nomeParaBarra());

        if (path.equals("/painel") || path.startsWith("/painel?") || path.equals("/painel/")) {
            model.addAttribute("navArea", "painel");
        } else if (path.startsWith("/produtos")) {
            model.addAttribute("navArea", "produtos");
            if (path.startsWith("/produtos/editar")) {
                model.addAttribute("navProdutosSub", "editar");
            } else if (path.startsWith("/produtos/novo")) {
                model.addAttribute("navProdutosSub", "novo");
            } else {
                model.addAttribute("navProdutosSub", "lista");
            }
        } else if (path.startsWith("/categorias")) {
            model.addAttribute("navArea", "categorias");
            if (path.startsWith("/categorias/editar")) {
                model.addAttribute("navCategoriasSub", "editar");
            } else if (path.startsWith("/categorias/novo")) {
                model.addAttribute("navCategoriasSub", "novo");
            } else {
                model.addAttribute("navCategoriasSub", "lista");
            }
        } else if (path.startsWith("/cadastro-usuario")) {
            model.addAttribute("navArea", "usuarios");
        }
    }

    private static String nomeParaBarra() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth instanceof AnonymousAuthenticationToken) {
            return "";
        }
        if (auth.getPrincipal() instanceof UsuarioPrincipal usuarioPrincipal) {
            return usuarioPrincipal.getNomeExibicao();
        }
        return auth.getName();
    }

    private static String pathWithinApplication(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String context = request.getContextPath();
        if (context != null && !context.isEmpty() && uri.startsWith(context)) {
            return uri.substring(context.length());
        }
        return uri;
    }
}
