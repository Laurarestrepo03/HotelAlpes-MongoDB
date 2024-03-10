package uniandes.edu.co.hoteles.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import uniandes.edu.co.hoteles.repositorio.HabitacionRepository;
import uniandes.edu.co.hoteles.repositorio.ServicioRepository;

@Controller
public class Clientes_consumoController {

    @Autowired
    private HabitacionRepository habitacionRepository;

    @Autowired
    private ServicioRepository servicioRepository;

    @GetMapping("/consumos/clientes")
    public String ningunConsumo(Model model, Integer servicio, String fecha, Integer veces, String num_documento, 
    String tipo_documento, String nombre, String correo){

        if (fecha == null) {
            fecha = "";
        }
        if (num_documento == null) {
            num_documento = "";
        }
        if (tipo_documento == null) {
            tipo_documento = "";
        }
        if (nombre == null) {
            nombre = "";
        }
        if (correo == null) {
            correo = "";
        }
        model.addAttribute("clientesConsumo", habitacionRepository.darClientesConsumo(servicio, fecha, veces, num_documento,
        tipo_documento, nombre, correo));
        model.addAttribute("servicios", servicioRepository.buscar());
        return "clientesConsumo";
    }
    
}
