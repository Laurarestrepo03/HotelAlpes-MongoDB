package uniandes.edu.co.hoteles.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

import uniandes.edu.co.hoteles.grupos.ConsumoGrupo;
import uniandes.edu.co.hoteles.grupos.ReservaGrupo;
import uniandes.edu.co.hoteles.modelo.Habitacion;
import uniandes.edu.co.hoteles.modelo.Servicio;
import uniandes.edu.co.hoteles.repositorio.HabitacionRepository;
import uniandes.edu.co.hoteles.repositorio.ServicioRepository;
import uniandes.edu.co.hoteles.repositorio.Tipo_habitacionRepository;

@Controller
public class HabitacionesController {
    
    @Autowired
    private HabitacionRepository habitacionRepository;

    @Autowired
    private Tipo_habitacionRepository tipo_habitacionRepository;

    @Autowired
    private ServicioRepository servicioRepository;

    @GetMapping("/habitaciones")
    public String habitaciones(Model model){
        model.addAttribute("habitaciones", habitacionRepository.buscar());
        return "habitaciones";
    }

    @GetMapping("/habitaciones/new")
    public String habitacionForm(Model model){
        model.addAttribute("habitacion", new Habitacion());
        model.addAttribute("tiposHabitacion", tipo_habitacionRepository.buscar());
        return "habitacionNuevo";
    }

    @PostMapping("/habitaciones/new/save")
    public String habitacionGuardar(@ModelAttribute Habitacion habitacion){
        Habitacion habitacionCheck = habitacionRepository.buscarPorId(habitacion.getId());
        if (habitacionCheck == null) {
            Habitacion habitacionInsertar = new Habitacion(habitacion.getId(), habitacion.getCosto_noche(),
            habitacion.getTipo());
            habitacionRepository.insertar(habitacionInsertar);
            return "redirect:/habitaciones";
        }
        else {
            return "redirect:/habitacionExistente";
        }    
    }

    @GetMapping("/habitaciones/{id}/edit")
    public String habitacionEditarForm(@PathVariable("id") Integer id, Model model){
        Habitacion habitacion = habitacionRepository.buscarPorId(id);
        if (habitacion != null){
            model.addAttribute("habitacion", habitacion);
            model.addAttribute("tiposHabitacion", tipo_habitacionRepository.buscar());
            return "habitacionEditar";
        }
        else {
            return "redirect:/habitaciones";
        }
    }

    @PostMapping("/habitaciones/{id}/edit/save")
    public String habitacionEditarGuardar(@PathVariable("id") Integer id, @ModelAttribute Habitacion habitacion){
        habitacionRepository.actualizar(id, habitacion.getCosto_noche(), habitacion.getTipo());
        if (habitacion.getId() != id) {
            Habitacion habitacionCheck = habitacionRepository.buscarPorId(habitacion.getId());
            if (habitacionCheck == null) {
                List<ConsumoGrupo> consumos = habitacionRepository.buscarConsumosHabitacion(id);
                List<ReservaGrupo> reservas = habitacionRepository.buscarReservasHabitacion(id);
                habitacionRepository.insertar(habitacion);
                for (ConsumoGrupo consumo : consumos) {
                    Servicio servicio = servicioRepository.buscarPorNombre(consumo.getServicio());
                    habitacionRepository.insertarConsumo(habitacion.getId(), consumo.getId(), servicio.getId(), consumo.getCliente(), 
                    consumo.getFecha(), consumo.getHora());
                }
                for (ReservaGrupo reserva : reservas) {
                    habitacionRepository.insertarReserva(habitacion.getId(), reserva.getCodigo(), reserva.getFecha_entrada(), reserva.getFecha_salida(), 
                    reserva.getEstado(), reserva.getNum_huespedes(), reserva.getCliente().getNum_documento(), reserva.getCliente().getTipo_documento(), 
                    reserva.getCliente().getNombre(), reserva.getCliente().getCorreo());
                }
                habitacionRepository.deleteById(id); 
            }
            else {
                return "redirect:/habitacionExistente";
            }           
        }
        return "redirect:/habitaciones";
    }

    @GetMapping("/habitaciones/{id}/delete")
    public String habitacionEliminar(@PathVariable("id") Integer id){
        habitacionRepository.eliminar(id);
        return "redirect:/habitaciones";
    }   
}