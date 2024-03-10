package uniandes.edu.co.hoteles.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import uniandes.edu.co.hoteles.repositorio.HabitacionRepository;

@Controller
public class ConsumosCliente {

    @Autowired
    private HabitacionRepository habitacionRepository;

    @GetMapping("/consumos/cliente")
    public String consumoCliente(Model model, String cliente, String fecha_inicio, String fecha_fin){
        model.addAttribute("consumoCliente", habitacionRepository.darConsumosCliente(cliente, fecha_inicio, fecha_fin));
        return "consumoCliente";
    }
    
}
