package uniandes.edu.co.hoteles.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import uniandes.edu.co.hoteles.repositorio.HabitacionRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Controller
public class Indice_ocupacionController {
    
    @Autowired
    private HabitacionRepository habitacionRepository;

    @GetMapping("/habitaciones/indiceOcupacion")
    public String indiceOcupacion(Model model){
        String fecha_fin =  LocalDate.now().toString();
        Integer anio  = Integer.parseInt(fecha_fin.substring(0, 4)) ;
        String fecha_inicio = String.valueOf(anio) + "-01-01";
        LocalDate fecha_inicio_date = LocalDate.parse(fecha_inicio);
        LocalDate fecha_fin_date = LocalDate.parse(fecha_fin);
        long dias = ChronoUnit.DAYS.between(fecha_inicio_date, fecha_fin_date);
        model.addAttribute("indiceOcupacion", habitacionRepository.darIndice_ocupacion(fecha_inicio, fecha_fin, dias));
        return "indiceOcupacion";
    }
}
