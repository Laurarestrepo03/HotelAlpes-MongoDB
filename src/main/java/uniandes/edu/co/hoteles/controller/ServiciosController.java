package uniandes.edu.co.hoteles.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

import uniandes.edu.co.hoteles.modelo.Servicio;
import uniandes.edu.co.hoteles.repositorio.SecuenciaRepository;
import uniandes.edu.co.hoteles.repositorio.ServicioRepository;

@Controller
public class ServiciosController {
    
    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private SecuenciaRepository secuenciaRepository;

    @GetMapping("/servicios")
    public String servicios(Model model){
        model.addAttribute("servicios", servicioRepository.buscar());
        return "servicios";
    }

    @GetMapping("/servicios/new")
    public String servicioForm(Model model){
        model.addAttribute("servicio", new Servicio());
        return "servicioNuevo";
    }

    @PostMapping("/servicios/new/save")
    public String servicioGuardar(@ModelAttribute Servicio servicio){
        int id = secuenciaRepository.buscarPorId(2).getId_actual()+1;
        secuenciaRepository.actualizarIdActual(2, id);
        Servicio servicioInsertar = new Servicio(id, servicio.getNombre(), servicio.getTipo_cobro(), servicio.getPrecio(), 
        servicio.getCapacidad(), servicio.getEstilo(), servicio.getCosto_adicional());
        servicioRepository.insertar(servicioInsertar);
        return "redirect:/servicios";
    }

    @GetMapping("/servicios/{id}/edit")
    public String servicioEditarForm(@PathVariable("id") int id, Model model){
        Servicio servicio = servicioRepository.buscarPorId(id);
        if (servicio != null){
            model.addAttribute("servicio", servicio);
            return "servicioEditar";
        }
        else {
            return "redirect:/servicios";
        }
    }

    @PostMapping("/servicios/{id}/edit/save")
    public String servicioEditarGuardar(@PathVariable("id") int id, @ModelAttribute Servicio servicio){
        servicioRepository.actualizar(id, servicio.getNombre(), servicio.getTipo_cobro(), servicio.getPrecio(), 
        servicio.getCapacidad(), servicio.getEstilo(), servicio.getCosto_adicional());
        return "redirect:/servicios";
    }

    @GetMapping("/servicios/{id}/delete")
    public String servicioEliminar(@PathVariable("id") int id){
        servicioRepository.eliminar(id);
        return "redirect:/servicios";
    } 

}
