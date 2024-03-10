package uniandes.edu.co.hoteles.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;

import uniandes.edu.co.hoteles.repositorio.HabitacionRepository;

@Controller
public class ClienteController {

    @Autowired
    private HabitacionRepository habitacionRepository;

    @GetMapping("/reservas/{codigo}/cliente")
    public String clienteReserva(@PathVariable("codigo") int codigo, Model model){
        model.addAttribute("cliente", habitacionRepository.darClienteReserva(codigo));
         model.addAttribute("codigo_reserva", codigo);
        return "cliente";
    }

    
}
