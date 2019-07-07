package pink.coursework.csvparser.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pink.coursework.csvparser.models.Myfile;
import pink.coursework.csvparser.servises.FileService;

@Controller
public class FileController {
    @Autowired
    private FileService fileService;

    @GetMapping("/file/files")
    private String getAllFiles(Model model) {
        model.addAttribute("curpage", 1);
        model.addAttribute("pages", fileService.pages());
        model.addAttribute("files", fileService.paginationFiles(1));
        model.addAttribute("contentPage", "/file/allfiles");
        return "default";
    }
    @GetMapping("/file/files/{page}")
    private String getFilesPaginationList(Model model, @PathVariable int page) {
        model.addAttribute("curpage", page);
        model.addAttribute("pages", fileService.pages());
        model.addAttribute("files", fileService.paginationFiles(page));
        model.addAttribute("contentPage", "/file/allfiles");
        return "default";
    }
    @GetMapping(value = "/file/files", params = { "search" })
    private String getSearchFileAndUser(Model model, String search) {
        if(fileService.searchList(search) == null || fileService.searchList(search).isEmpty()){
            model.addAttribute("contentPage", "/fragments/searchResultNullFiles");
        }else{
            model.addAttribute("files", fileService.searchList(search));
            model.addAttribute("contentPage", "/file/search");
        }
        return "default";
    }
    @GetMapping(value = "/file/files/{page}", params = { "search" })
    private String getSearchFileAndUserPage(Model model, String search) {
        if(fileService.searchList(search) == null || fileService.searchList(search).isEmpty()){
            model.addAttribute("contentPage", "/fragments/searchResultNullFiles");
        }else{
            model.addAttribute("files", fileService.searchList(search));
            model.addAttribute("contentPage", "/file/search");
        }
        return "default";
    }
    @GetMapping("/file/delete/{id}")
    private String delete(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("file", fileService.getFile(id));
        model.addAttribute("contentPage", "/file/delete");
        return "default";
    }

    @PostMapping("/file/delete")
    private String deleteSubmit(@ModelAttribute("file") Myfile myfile) {
        fileService.deleteFile(myfile);
        return "redirect:/file/files";
    }
}
