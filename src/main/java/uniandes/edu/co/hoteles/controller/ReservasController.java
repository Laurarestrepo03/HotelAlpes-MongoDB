package uniandes.edu.co.hoteles.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

import uniandes.edu.co.hoteles.grupos.ReservaGrupo;
import uniandes.edu.co.hoteles.repositorio.HabitacionRepository;
import uniandes.edu.co.hoteles.repositorio.SecuenciaRepository;

import uniandes.edu.co.hoteles.modelo.Reserva;
import uniandes.edu.co.hoteles.modelo.Habitacion;

import java.util.List;
import java.time.LocalDate;

@Controller
public class ReservasController {

    @Autowired
    private HabitacionRepository habitacionRepository;

    @Autowired
    private SecuenciaRepository secuenciaRepository;

    @GetMapping("/reservas")
    public String reservas(Model model){
        model.addAttribute("reservas", habitacionRepository.buscarReservas());
        return "reservas";
    }

    @GetMapping("/reservas/new")
    public String reservaForm (Model model){
        model.addAttribute("reserva", new ReservaGrupo());
        return "reservaNuevo";
    }

    @PostMapping("/reservas/new/save")
    public String reservaGuardar(@ModelAttribute ReservaGrupo reserva, @ModelAttribute("num_documento") String num_documento,
    @ModelAttribute("tipo_documento") String tipo_documento, @ModelAttribute("nombre") String nombre, @ModelAttribute("correo") String correo){
        Habitacion habitacionCheck = habitacionRepository.buscarPorId(reserva.getHabitacion());
        if (habitacionCheck != null) {
            if (LocalDate.parse(reserva.getFecha_salida()).isAfter(LocalDate.parse(reserva.getFecha_entrada()))) { 
                List<Reserva> reservaCheck = habitacionRepository.verificarDisponibilidad(reserva.getHabitacion(), reserva.getFecha_entrada(), reserva.getFecha_salida());
                if (reservaCheck.size() == 0){
                    int codigo = secuenciaRepository.buscarPorId(3).getId_actual()+1;
                    secuenciaRepository.actualizarIdActual(3, codigo);
                    habitacionRepository.insertarReserva(reserva.getHabitacion(), codigo, reserva.getFecha_entrada(), reserva.getFecha_salida(),
                    reserva.getEstado(), reserva.getNum_huespedes(), num_documento, tipo_documento, nombre, correo);
                    return "redirect:/reservas";
                }
                else {
                    return "redirect:/habitacionOcupada";
                }
            }
            else {
               return "redirect:/fechaNoMayor"; 
            }
        }
        else {
            return "redirect:/habitacionNoExiste?redirect=reservas";
        }
    }

    @GetMapping("/reservas/{codigo}/edit")
    public String reservaEditarForm(@PathVariable("codigo") int codigo, Model model){
        ReservaGrupo reserva = habitacionRepository.buscarReservaPorCodigo(codigo);
        if (reserva != null){
            model.addAttribute("reserva", reserva);
            return "reservaEditar";
        }
        else {
            return "redirect:/reservas";
        }
    }

    @PostMapping("/reservas/{codigo}/edit/save")
    public String reservaEditarGuardar(@PathVariable("codigo") int codigo, @ModelAttribute ReservaGrupo reserva, @ModelAttribute("habitacion") int habitacion, 
    @ModelAttribute("num_documento") String num_documento, @ModelAttribute("tipo_documento") String tipo_documento, @ModelAttribute("nombre") String nombre, 
    @ModelAttribute("correo") String correo){
        Habitacion habitacionCheck = habitacionRepository.buscarPorId(reserva.getHabitacion());
        if (habitacionCheck != null) {
            if (LocalDate.parse(reserva.getFecha_salida()).isAfter(LocalDate.parse(reserva.getFecha_entrada()))) {
                List<Reserva> reservaCheck = habitacionRepository.verificarDisponibilidad(reserva.getHabitacion(), reserva.getFecha_entrada(), reserva.getFecha_salida());
                if (reservaCheck.size() == 0 || (reservaCheck.size() == 1 && reservaCheck.get(0).getCodigo() == codigo)){ 
                    int habitacion_vieja = habitacionRepository.buscarReservaPorCodigo(codigo).getHabitacion();
                    habitacionRepository.actualizarReserva(habitacion_vieja, codigo, reserva.getFecha_entrada(), reserva.getFecha_salida(), reserva.getEstado(), 
                    reserva.getNum_huespedes(), num_documento, tipo_documento, nombre, correo);
                    if (habitacion_vieja != habitacion) {
                        habitacionRepository.eliminarReserva(habitacion_vieja, codigo);
                        habitacionRepository.insertarReserva(habitacion, codigo, reserva.getFecha_entrada(), reserva.getFecha_salida(), reserva.getEstado(), 
                        reserva.getNum_huespedes(), num_documento, tipo_documento, nombre, correo);
                    }
                    return "redirect:/reservas";
                }
                else {
                    return "redirect:/habitacionOcupada";
                }
            }
            else {
                return "redirect:/fechaNoMayor";
            }
        }
        else {
            return "redirect:/habitacionNoExiste?redirect=reservas";
        }
    }

    @GetMapping("/reservas/{codigo}/delete")
    public String tipoHabitacionEliminar(@PathVariable("codigo") int codigo){
        int id_habitacion = habitacionRepository.buscarReservaPorCodigo(codigo).getHabitacion();
        habitacionRepository.eliminarReserva(id_habitacion, codigo);
        return "redirect:/reservas";
    } 
    
}
