package uniandes.edu.co.hoteles.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

import uniandes.edu.co.hoteles.modelo.Tipo_habitacion;
import uniandes.edu.co.hoteles.repositorio.SecuenciaRepository;
import uniandes.edu.co.hoteles.repositorio.Tipo_habitacionRepository;

@Controller
public class Tipos_habitacionController {
    
    @Autowired
    private Tipo_habitacionRepository tipo_habitacionRepository;

    @Autowired
    private SecuenciaRepository secuenciaRepository;

    @GetMapping("/tiposHabitacion")
    public String tiposHabitacion(Model model){
        model.addAttribute("tiposHabitacion", tipo_habitacionRepository.buscar());
        return "tiposHabitacion";
    }

    @GetMapping("/tiposHabitacion/new")
    public String tipoHabitacionForm(Model model){
        model.addAttribute("tipoHabitacion", new Tipo_habitacion());
        return "tipoHabitacionNuevo";
    }

    @PostMapping("/tiposHabitacion/new/save")
    public String tipoHabitacionGuardar(@ModelAttribute Tipo_habitacion tipo_habitacion){
        int id = secuenciaRepository.buscarPorId(1).getId_actual()+1;
        secuenciaRepository.actualizarIdActual(1, id);
        Tipo_habitacion tipoHabitacionInsertar = new Tipo_habitacion(id, tipo_habitacion.getNombre(), tipo_habitacion.getCapacidad());
        tipo_habitacionRepository.insertar(tipoHabitacionInsertar);
        return "redirect:/tiposHabitacion";
    }

    @GetMapping("/tiposHabitacion/{id}/edit")
    public String tipoHabitacionEditarForm(@PathVariable("id") int id, Model model){
        Tipo_habitacion tipo_habitacion = tipo_habitacionRepository.buscarPorId(id);
        if (tipo_habitacion != null){
            model.addAttribute("tipoHabitacion", tipo_habitacion);
            return "tipoHabitacionEditar";
        }
        else {
            return "redirect:/tiposHabitacion";
        }
    }

    @PostMapping("/tiposHabitacion/{id}/edit/save")
    public String tipoHabitacionEditarGuardar(@PathVariable("id") int id, @ModelAttribute Tipo_habitacion tipo_habitacion){
        tipo_habitacionRepository.actualizar(id, tipo_habitacion.getNombre(), tipo_habitacion.getCapacidad());
        return "redirect:/tiposHabitacion";
    }

    @GetMapping("/tiposHabitacion/{id}/delete")
    public String tipoHabitacionEliminar(@PathVariable("id") int id){
        tipo_habitacionRepository.eliminar(id);
        return "redirect:/tiposHabitacion";
    } 

}
