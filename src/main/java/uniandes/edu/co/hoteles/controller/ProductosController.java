package uniandes.edu.co.hoteles.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

import uniandes.edu.co.hoteles.modelo.Producto;
import uniandes.edu.co.hoteles.repositorio.ServicioRepository;

@Controller
public class ProductosController {

    @Autowired
    private ServicioRepository servicioRepository;

    @GetMapping("/servicios/{id}/productos")
    public String productos(@PathVariable("id") int id, Model model){
        model.addAttribute("servicio", servicioRepository.buscarPorId(id));
        model.addAttribute("productos", servicioRepository.buscaProductos(id));
        return "productos";
    } 

    @GetMapping("/servicios/{id}/productos/new")
    public String productoForm(@PathVariable("id") int id, Model model){
        model.addAttribute("producto", new Producto());
        model.addAttribute("servicio", servicioRepository.buscarPorId(id));
        return "productoNuevo";
    }

    @PostMapping("/servicios/{id}/productos/new/save")
    public String productoGuardar(@PathVariable("id") int id, @ModelAttribute Producto producto){
        int id_producto;
        List<Producto> productos = servicioRepository.buscaProductos(id);
        if (productos.size() == 0) {
            id_producto = 1;
        }
        else {
            id_producto = productos.get(productos.size()-1).getId()+1;
        }
        servicioRepository.insertarProducto(id, id_producto, producto.getNombre(), producto.getPrecio());
        return "redirect:/servicios/{id}/productos";
    }

    @GetMapping("/servicios/{id}/productos/{id_producto}/edit")
    public String productoEditarForm(@PathVariable("id") int id, @PathVariable("id_producto") int id_producto, Model model){
        Producto producto = servicioRepository.buscarProductoPorId(id, id_producto);
        if (producto != null){
            model.addAttribute("producto", producto);
            model.addAttribute("servicio", servicioRepository.buscarPorId(id));
            return "productoEditar";
        }
        else {
            return "redirect:/servicios/{id}/productos";
        }
    }

    @PostMapping("/servicios/{id}/productos/{id_producto}/edit/save")
    public String productoEditarGuardar(@PathVariable("id") int id, @PathVariable("id_producto") int id_producto, @ModelAttribute Producto producto){
        servicioRepository.actualizarProducto(id, id_producto, producto.getNombre(), producto.getPrecio());
        return "redirect:/servicios/{id}/productos";
    }

    @GetMapping("/servicios/{id}/productos/{id_producto}/delete")
    public String productoEliminar(@PathVariable("id") int id,  @PathVariable("id_producto") int id_producto) {
        servicioRepository.eliminarProducto(id, id_producto);
        return "redirect:/servicios/{id}/productos";
    } 

}
