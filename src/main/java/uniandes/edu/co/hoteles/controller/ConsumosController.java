package uniandes.edu.co.hoteles.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

import uniandes.edu.co.hoteles.grupos.ConsumoGrupo;
import uniandes.edu.co.hoteles.modelo.Servicio;
import uniandes.edu.co.hoteles.modelo.Habitacion;
import uniandes.edu.co.hoteles.repositorio.HabitacionRepository;
import uniandes.edu.co.hoteles.repositorio.SecuenciaRepository;
import uniandes.edu.co.hoteles.repositorio.ServicioRepository;

@Controller
public class ConsumosController {

    @Autowired
    private HabitacionRepository habitacionRepository;

    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private SecuenciaRepository secuenciaRepository;

    @GetMapping("/consumos")
    public String consumos(Model model){
        model.addAttribute("consumos", habitacionRepository.buscarConsumos());
        return "consumos";
    }

    @GetMapping("/consumos/new")
    public String consumoForm (Model model){
        model.addAttribute("consumo", new ConsumoGrupo());
        model.addAttribute("servicios", servicioRepository.buscar());
        return "consumoNuevo";
    }

    @PostMapping("/consumos/new/save")
    public String consumoGuardar(@ModelAttribute ConsumoGrupo consumo){
        Habitacion habitacionCheck = habitacionRepository.buscarPorId(consumo.getHabitacion());
        if (habitacionCheck != null) {
            int id = secuenciaRepository.buscarPorId(4).getId_actual()+1;
            secuenciaRepository.actualizarIdActual(4, id);
            Servicio servicio = servicioRepository.buscarPorNombre(consumo.getServicio());
            habitacionRepository.insertarConsumo(consumo.getHabitacion(), id, servicio.getId(), consumo.getCliente(), consumo.getFecha(), consumo.getHora());
            return "redirect:/consumos";
        }
        else {
            return "redirect:/habitacionNoExiste?redirect=consumos";
        }
        
    }

    @GetMapping("/consumos/{id}/edit")
    public String consumoEditarForm(@PathVariable("id") int id, Model model){
        ConsumoGrupo consumo = habitacionRepository.buscarConsumoPorId(id);
        if (consumo != null){
            model.addAttribute("consumo", consumo);
            model.addAttribute("servicios", servicioRepository.buscar());
            return "consumoEditar";
        }
        else {
            return "redirect:/consumos";
        }
    }

    @PostMapping("/consumos/{id}/edit/save")
    public String consumoEditarGuardar(@PathVariable("id") int id, @ModelAttribute ConsumoGrupo consumo, @ModelAttribute("habitacion") int habitacion){
        int habitacion_vieja = habitacionRepository.buscarConsumoPorId(id).getHabitacion();
        Servicio servicio = servicioRepository.buscarPorNombre(consumo.getServicio());
        habitacionRepository.actualizarConsumo(habitacion_vieja, id, servicio.getId(), consumo.getCliente(), consumo.getFecha(), consumo.getHora());
        Habitacion habitacionCheck = habitacionRepository.buscarPorId(habitacion);
        if (habitacionCheck != null) {
            if (habitacion_vieja != habitacion) {
                habitacionRepository.eliminarConsumo(habitacion_vieja, id);
                habitacionRepository.insertarConsumo(habitacion, id, servicio.getId(), consumo.getCliente(), consumo.getFecha(), consumo.getHora());
            }
            return "redirect:/consumos";
        }
        else {
            return "redirect:/habitacionNoExiste?redirect=consumos";
        }  
    }

    @GetMapping("/consumos/{id}/delete")
    public String consumoEliminar(@PathVariable("id") int id){
        int id_habitacion = habitacionRepository.buscarConsumoPorId(id).getHabitacion();
        habitacionRepository.eliminarConsumo(id_habitacion, id);
        return "redirect:/consumos";
    } 
    
}
