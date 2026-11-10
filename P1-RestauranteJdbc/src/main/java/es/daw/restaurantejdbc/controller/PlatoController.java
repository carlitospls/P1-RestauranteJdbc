package es.daw.restaurantejdbc.controller;

import es.daw.restaurantejdbc.dto.PlatoForm;
import es.daw.restaurantejdbc.model.Plato;
import es.daw.restaurantejdbc.service.CategoriaService;
import es.daw.restaurantejdbc.service.PlatoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// URLs: GET /platos | GET /platos/nuevo | GET /platos/editar/{codigo}
//       POST /platos/guardar | POST /platos/borrar/{codigo}
@Controller
@RequestMapping("/platos")
@RequiredArgsConstructor
public class PlatoController {

    private final PlatoService platoService;
    private final CategoriaService categoriaService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("platos", platoService.listar());
        return "platos/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("platoForm", new PlatoForm());
        model.addAttribute("categorias", categoriaService.listar());
        return "platos/formulario";
    }

    @GetMapping("/editar/{codigo}")
    public String editar(@PathVariable Integer codigo, Model model) {
        Plato plato = platoService.buscarPorCodigo(codigo);
        model.addAttribute("platoForm", PlatoForm.toDto(plato));
        model.addAttribute("categorias", categoriaService.listar());
        return "platos/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("platoForm") PlatoForm platoForm,
                          BindingResult result,
                          Model model,
                          RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("categorias", categoriaService.listar());
            return "platos/formulario";
        }

        if (platoForm.getCodigo() == null) {
            Plato creado = platoService.crear(platoForm.toModelEntity());
            redirectAttributes.addFlashAttribute("mensaje",
                    "Plato creado con código " + creado.getCodigo());
        } else {
            platoService.actualizar(platoForm.toModelEntity());
            redirectAttributes.addFlashAttribute("mensaje",
                    "Plato " + platoForm.getCodigo() + " actualizado");
        }
        return "redirect:/platos";
    }

    @PostMapping("/borrar/{codigo}")
    public String borrar(@PathVariable Integer codigo, RedirectAttributes redirectAttributes) {
        platoService.borrar(codigo);
        redirectAttributes.addFlashAttribute("mensaje", "Plato " + codigo + " borrado");
        return "redirect:/platos";
    }
}
