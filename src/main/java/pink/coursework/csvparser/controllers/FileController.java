package pink.coursework.csvparser.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pink.coursework.csvparser.models.Myfile;
import pink.coursework.csvparser.servises.FileService;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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

    @PostMapping("/file/share")
    private String share(@ModelAttribute("file") Myfile file,
                         @RequestParam(value = "read", required = false) Boolean read,
                         @RequestParam(value = "edit", required = false) Boolean edit,
                         @RequestParam(value = "dowload", required = false) Boolean dowload){
        Integer idUser = 1;
        fileService.addLink(file, read, edit, dowload);
        return "redirect:/file/myfiles/"+idUser;
    }

    @GetMapping(value = "/file/links/{id}")
    private String openFiles(Model model, @PathVariable("id") Integer idUser) {
        idUser = 1;
        model.addAttribute("message", "No open files");
        model.addAttribute("curpage", 1);
        model.addAttribute("pages", fileService.openPages(idUser));
        model.addAttribute("openfiles", fileService.getOpenFiles(idUser, 1));
        model.addAttribute("contentPage", "/file/links");
        return "default";
    }
    @GetMapping(value = "/file/links/{id}/{page}")
    private String openFilesPagination(Model model, @PathVariable("id") Integer idUser, @PathVariable int page) {
        idUser = 1;
        model.addAttribute("message", "No open files");
        model.addAttribute("curpage", page);
        model.addAttribute("pages", fileService.openPages(idUser));
        model.addAttribute("openfiles", fileService.getOpenFiles(idUser, page));
        model.addAttribute("contentPage", "/file/links");
        return "default";
    }
    @PostMapping("/file/addlink")
    private String addLinkUser(Model model, @ModelAttribute("link") String link){
        Integer idUser = 1;
        fileService.addLinkUser(idUser, link);
        model.addAttribute("openfiles", fileService.getOpenFiles(idUser,1));
        return "redirect:/file/links/"+idUser;
    }
    @GetMapping("/file/remove/{id}")
    private String removeFile(@PathVariable("id") Integer idFile){
        Integer idUser = 1;
        fileService.removeFile(idFile, idUser);
        return "redirect:/file/links/"+idUser;
    }
    @GetMapping("/file/open/{id}")
    private String openCSV(Model model, @PathVariable("id") Integer idFile) throws Exception {
        model.addAttribute("file", fileService.getFile(idFile));
        model.addAttribute("curpage", 1);
        model.addAttribute("pages", fileService.csvPages(idFile));
        model.addAttribute("csvfile", fileService.openCSV(idFile, 1));
        model.addAttribute("contentPage", "/file/opencsv");
        return "default";
    }
    @GetMapping("/file/open/{id}/{page}")
    private String paginationOpenCSV(Model model, @PathVariable("id") Integer idFile, @PathVariable int page) throws Exception {
        model.addAttribute("file", fileService.getFile(idFile));
        model.addAttribute("curpage", page);
        model.addAttribute("pages", fileService.csvPages(idFile));
        model.addAttribute("csvfile", fileService.openCSV(idFile, page));
        model.addAttribute("contentPage", "/file/opencsv");
        return "default";
    }
    @PostMapping("/file/saveall")
    private String saveOpenCSV( @RequestParam("file") Myfile file,
                               @RequestParam("curpage") int page,
                               @RequestParam(value = "title", required = false) List<String> titleList,
                                @RequestParam(value = "dataList", required = false) List<String> dataList,
                                @RequestParam(value = "idList", required = false) List<Integer> idList) throws Exception {
        // required = false ели такогопараметра нет в HTML то игнорировать
        fileService.getCsvModelSave(titleList, dataList, idList, file);
        return "redirect:/file/open/"+file.getId()+"/"+page;
    }
    @PostMapping("/file/row")
    private String addRowOpenCSV( @RequestParam("file") Myfile file,
                                  @RequestParam("curpage") int page,
                                @RequestParam("newRow") String newRow) throws Exception{
        fileService.addNewRow(file, newRow);
        return "redirect:/file/open/"+file.getId()+"/"+page;
    }
    @GetMapping("/file/colum/{id}/{page}")
    private String addColumOpenCSV(@PathVariable("id") Integer idFile, @PathVariable("page") int page) throws Exception {
        fileService.addNewColum(idFile);
        return "redirect:/file/open/"+idFile+"/"+page;
    }
    @PostMapping("/file/deleterows")
    public String deleteRows(@RequestParam("file") Myfile file,
                             @RequestParam("curpage") int page,
                             @RequestParam("deleterows") List<String> rows) throws Exception{
        fileService.deleteRows(file, rows);
        return "redirect:/file/open/"+file.getId()+"/"+page;
    }
    @PostMapping("/file/deletecolums")
    public String deleteColums(@RequestParam("file") Myfile file,
                             @RequestParam("curpage") int page,
                             @RequestParam("deletecolums") List<String> colums) throws Exception{
        fileService.deleteColums(file, colums);
        return "redirect:/file/open/"+file.getId()+"/"+page;
    }
}
