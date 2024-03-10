package uniandes.edu.co.hoteles.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import uniandes.edu.co.hoteles.repositorio.HabitacionRepository;

import java.time.LocalDate;

@Controller
public class Dinero_recolectadoController {

    @Autowired
    private HabitacionRepository habitacionRepository;

    @GetMapping("/habitaciones/dineroRecolectado")
    public String dineroRecolectado(Model model){
        String fecha_fin =  LocalDate.now().toString();
        Integer anio  = Integer.parseInt(fecha_fin.substring(0, 4)) ;
        String fecha_inicio = String.valueOf(anio) + "-01-01";
        model.addAttribute("dineroRecolectado", habitacionRepository.darDineroRecolectado(fecha_inicio, fecha_fin));
        return "dineroRecolectado";
    }
    
}
