package uniandes.edu.co.hoteles.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

import uniandes.edu.co.hoteles.modelo.Dotacion;
import uniandes.edu.co.hoteles.repositorio.Tipo_habitacionRepository;

import java.util.List;

@Controller
public class DotacionController {

    @Autowired
    private Tipo_habitacionRepository tipo_habitacionRepository;

    @GetMapping("/tiposHabitacion/{id}/dotacion")
    public String dotacion(@PathVariable("id") int id, Model model){
        model.addAttribute("tipo_habitacion", tipo_habitacionRepository.buscarPorId(id));
        model.addAttribute("dotaciones", tipo_habitacionRepository.buscarDotaciones(id));
        return "dotacion";
    } 

    @GetMapping("/tiposHabitacion/{id}/dotacion/new")
    public String dotacionForm(@PathVariable("id") int id, Model model){
        model.addAttribute("dotacionIncluida", new Dotacion());
        model.addAttribute("tipo_habitacion", tipo_habitacionRepository.buscarPorId(id));
        return "dotacionNuevo";
    }

    @PostMapping("/tiposHabitacion/{id}/dotacion/new/save")
    public String dotacionGuardar(@PathVariable("id") int id, @ModelAttribute Dotacion dotacion){
        int id_dotacion;
        List<Dotacion> dotaciones = tipo_habitacionRepository.buscarDotaciones(id);
        if (dotaciones.size() == 0) {
            id_dotacion = 1;
        }
        else {
            id_dotacion = dotaciones.get(dotaciones.size()-1).getId()+1;
        }
        tipo_habitacionRepository.insertarDotacion(id, id_dotacion, dotacion.getNombre(), dotacion.getCantidad());
        return "redirect:/tiposHabitacion/{id}/dotacion";
    }

    @GetMapping("/tiposHabitacion/{id}/dotacion/{id_dotacion}/edit")
    public String dotacionEditarForm(@PathVariable("id") int id, @PathVariable("id_dotacion") int id_dotacion, Model model){
        Dotacion dotacion = tipo_habitacionRepository.buscarDotacionPorId(id, id_dotacion);
        if (dotacion != null){
            model.addAttribute("dotacionIncluida", dotacion);
            model.addAttribute("tipo_habitacion", tipo_habitacionRepository.buscarPorId(id));
            return "dotacionEditar";
        }
        else {
            return "redirect:/tiposHabitacion/{id}/dotacion";
        }
    } 

    @PostMapping("/tiposHabitacion/{id}/dotacion/{id_dotacion}/edit/save")
    public String dotacionEditarGuardar(@PathVariable("id") int id, @PathVariable("id_dotacion") int id_dotacion, @ModelAttribute Dotacion dotacion){
        tipo_habitacionRepository.actualizarDotacion(id, id_dotacion, dotacion.getNombre(), dotacion.getCantidad());
        return "redirect:/tiposHabitacion/{id}/dotacion";
    }

    @GetMapping("/tiposHabitacion/{id}/dotacion/{id_dotacion}/delete")
    public String dotacionEliminar(@PathVariable("id") int id,  @PathVariable("id_dotacion") int id_dotacion) {
        tipo_habitacionRepository.eliminarDotacion(id, id_dotacion);
        return "redirect:/tiposHabitacion/{id}/dotacion";
    } 

}
