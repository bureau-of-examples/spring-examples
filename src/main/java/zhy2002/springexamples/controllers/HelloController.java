package zhy2002.springexamples.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
}
