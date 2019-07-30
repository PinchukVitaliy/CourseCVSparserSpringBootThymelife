package pink.coursework.csvparser.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pink.coursework.csvparser.servises.FileService;

import javax.servlet.http.HttpServletResponse;

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
    private String deleteSubmit(@ModelAttribute("id") Integer myfileId) {
        Integer idUser = 1;
        fileService.deleteFile(myfileId);
        return "redirect:/file/myfiles/"+idUser;
    }

    @GetMapping("/file/myfiles/{id}")
    private  String myFiles(@PathVariable("id") Integer id, Model model){
            model.addAttribute("message", "You have no uploaded files!");
            model.addAttribute("curpage", 1);
            model.addAttribute("pages", fileService.myPages(id));
            model.addAttribute("myfiles", fileService.listUserFiles(id, 1));
            model.addAttribute("contentPage", "/file/myfiles");
        return "default";
    }
    @GetMapping("/file/myfiles/{id}/{page}")
    private  String myFiles(@PathVariable("id") Integer id, @PathVariable int page, Model model){
            model.addAttribute("message", "You have no uploaded files!");
            model.addAttribute("id", id);
            model.addAttribute("curpage", page);
            model.addAttribute("pages", fileService.myPages(id));
            model.addAttribute("myfiles", fileService.listUserFiles(id, page));
            model.addAttribute("contentPage", "/file/myfiles");
        return "default";
    }

    @GetMapping(value = "/file/myfiles/{id}", params = { "search" })
    private String myFilesSearch(@PathVariable("id") Integer id, Model model, String search) {
        if(fileService.searchListMyfiles(id,  search) == null || fileService.searchListMyfiles(id,  search).isEmpty()){
            model.addAttribute("contentPage", "/fragments/searchResultNullMyFiles");
        }else{
            model.addAttribute("tolist",true);
            model.addAttribute("curpage", 1);
            model.addAttribute("pages", fileService.myPages(id));
            model.addAttribute("myfiles", fileService.searchListMyfiles(id,  search));
            model.addAttribute("contentPage", "/file/myfiles");
        }
        return "default";
    }
    @GetMapping(value = "/file/myfiles/{id}/{page}", params = { "search" })
    private String myFilesSearchPage(@PathVariable("id") Integer id, Model model, String search, @PathVariable int page) {
        if(fileService.searchListMyfiles(id, search) == null || fileService.searchListMyfiles(id, search).isEmpty()){
            model.addAttribute("contentPage", "/fragments/searchResultNullMyFiles");
        }else{
            model.addAttribute("tolist",true);
            model.addAttribute("curpage", page);
            model.addAttribute("pages", fileService.myPages(id));
            model.addAttribute("myfiles", fileService.searchListMyfiles(id, search));
            model.addAttribute("contentPage", "/file/myfiles");
        }
        return "default";
    }
    @PostMapping("/file/addcsv")
    private String addcsvSubmit(@RequestParam("file") MultipartFile file) {
        Integer idUser = 1;
        fileService.add(file, idUser);
        return "redirect:/file/myfiles/"+idUser;
    }
    @GetMapping("/file/dowload")
    private String dowloadFile(@ModelAttribute("idFile") Integer id, HttpServletResponse response) {
        Integer idUser = 1;
        fileService.dowload(id, response);
        return "redirect:/file/myfiles/"+idUser;
    }
}
