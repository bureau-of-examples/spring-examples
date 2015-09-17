package zhy2002.springexamples.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import zhy2002.springexamples.common.StringUtils;
import zhy2002.springexamples.domain.*;

/**
 * A test controller.
 */
@Controller
@RequestMapping
public class HelloController {

    @RequestMapping(value = "/hello/{name}", method = RequestMethod.GET)
    public String sayHello(@PathVariable String name, Model model){

        model.addAttribute("name", name);
        return "hello";
    }

    @RequestMapping(value = "/accessSecret", method = RequestMethod.POST)
    public String accessSecret(@RequestParam("username") String username, @RequestParam("password") String password, Model model){

        String secret;
        if("Ned".equals(username) && "Stark".equals(password)){
            secret = "Revealed";
        } else {
            secret = "Hidden";
        }
        model.addAttribute("secret", secret);
        return "showSecret";
    }

    @RequestMapping(value = "/hello/changeColor/{color}", method = RequestMethod.POST)
    public @ResponseBody String changeColor(@PathVariable("color") Color color){


        return "Color is changed to " + color;

    }

    @RequestMapping(value = "/hello/changeCategory/{name}", method = RequestMethod.POST)
    public @ResponseBody
    String changeCategory(@PathVariable("name") Category category){

        return "Category is changed to " + category.getId();

    }

    @RequestMapping(value = "/hello/${hey}", method = RequestMethod.GET)
    public @ResponseBody String hey(){
        return "success";
    }


    @RequestMapping("/hello2/{name}")
    public @ResponseBody String echoMatrixVarible(@PathVariable("name") String name, @MatrixVariable("times") int times){

        return StringUtils.repeat(name, times, ",");
    }
}
