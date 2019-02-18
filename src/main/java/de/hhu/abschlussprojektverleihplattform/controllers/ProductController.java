package de.hhu.abschlussprojektverleihplattform.controllers;

import de.hhu.abschlussprojektverleihplattform.model.UserEntity;
import de.hhu.abschlussprojektverleihplattform.repository.UserRepository;
import de.hhu.abschlussprojektverleihplattform.service.CookieUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProductController {

    // PostMapping fehlt noch

    @Autowired
    UserRepository userRepository;

    @Autowired
    CookieUserService cookieUserService;

    @GetMapping("/addproduct")
    public String getAddProduct(Model model, HttpServletRequest httpServletRequest) {
        UserEntity user;

        try {
            user = cookieUserService.getUserFromRequest(httpServletRequest);
        }catch (Exception e){
            return "login";
        }

        return "addproduct";
    }

    @GetMapping("/editproduct")
    public String getEditProduct(Model model, HttpServletRequest httpServletRequest) {

        UserEntity user;

        try {
            cookieUserService.getUserFromRequest(httpServletRequest);
        }catch (Exception e){
            return "login";
        }

        return "editproduct";
    }

    @GetMapping("/removeproduct")
    public String getRemoveProduct(Model model, HttpServletRequest httpServletRequest) {
        UserEntity user;

        try {
            cookieUserService.getUserFromRequest(httpServletRequest);
        }catch (Exception e){
            return "login";
        }

        return "removeproduct";
    }

    @GetMapping("/productdetail")
    public String getProductDetails(
        Model model,
        HttpServletRequest httpServletRequest
    ) throws Exception{
        UserEntity user = cookieUserService.getUserFromRequest(httpServletRequest);

        model.addAttribute("user",user);
        return "productdetailedview";
    }
}

