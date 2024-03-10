package uniandes.edu.co.hoteles.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HotelesController {

    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @RequestMapping("/habitacionOcupada")
    public String habitacionOcupada(){
        return "habitacionOcupada";
    }

    @RequestMapping("/habitacionExistente")
    public String habitacionExistente(){
        return "habitacionExistente";
    }

    @RequestMapping("/fechaNoMayor")
    public String fechaNoMayor(){
        return "fechaNoMayor";
    }

    @RequestMapping("/habitacionNoExiste")
    public String habitacionNoExiste(){
        return "habitacionNoExiste";
    }  

}