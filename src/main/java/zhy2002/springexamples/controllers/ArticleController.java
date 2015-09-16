package zhy2002.springexamples.controllers;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import zhy2002.springexamples.domain.Article;

/**
 * Test controller.
 */
@Controller
@RequestMapping("/articles")
public class ArticleController {

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


}
