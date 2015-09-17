package zhy2002.springexamples.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import zhy2002.springexamples.conversion.CategoryConverter;
import zhy2002.springexamples.domain.Article;
import zhy2002.springexamples.domain.Category;
import zhy2002.springexamples.domain.Color;

import javax.annotation.PostConstruct;

/**
 * Test controller.
 */
@Controller
@RequestMapping("/articles")
public class ArticleController {

    @ModelAttribute("controllerName")
    public String addControllerName(){
       return "articleController";
    }

    @RequestMapping(value = "/{id:\\d+}", method = RequestMethod.GET)
    public String getArticle(@PathVariable("id") Long id, Model model){

        model.addAttribute("id", id);
        return "articles/readArticle";
    }


    @RequestMapping(value = "/edit/{id:\\d+}", method = RequestMethod.GET)
    public String editArticle(@PathVariable("id") Long id, Model model){
        model.addAttribute("id", id);
        model.addAttribute("article", new Article("Existing article"));
        return "articles/editArticle";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveArticle(Article article, BindingResult bindingResult, Model model){

        model.addAttribute("article", article);

         if(bindingResult.hasErrors()){
             model.addAttribute("error", true);
             return "articles/editArticle";
         }

         return "redirect:999";
    }


    @Autowired
    private ConfigurableConversionService conversionService;

    @PostConstruct
    public void init(){
       conversionService.addConverter(new CategoryConverter());
    }

    @InitBinder
    public void initBinder(WebDataBinder binder){
        if(binder.getConversionService() == null)
            binder.setConversionService(conversionService);
    }

    @RequestMapping(value = "/changeCategory/{name}", method = RequestMethod.POST)
    public @ResponseBody
    String changeCategory(@PathVariable("name") Category category){

        return "Category is changed to " + category.getId();

    }


}
