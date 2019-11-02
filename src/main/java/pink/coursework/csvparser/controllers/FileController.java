package pink.coursework.csvparser.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pink.coursework.csvparser.models.Myfile;
import pink.coursework.csvparser.models.User;
import pink.coursework.csvparser.servises.FileService;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Контроллер класса файл
 * @Controller определяет класс как Контроллер Spring MVC
 * @Autowired обеспечивает контроль над тем, где и как автосвязывание должны быть осуществленно.
 * @GetMapping аннотация для отображения HTTP- GET запросов на определенные методы-обработчики.
 * В частности, @GetMapping это составная аннотация, которая действует как ярлык для @RequestMapping(method = RequestMethod.GET).
 * @PostMapping аннотация для отображения HTTP- POSTзапросов на определенные методы-обработчики.
 * В частности, @PostMapping это составная аннотация, которая действует как ярлык для @RequestMapping(method = RequestMethod.POST).
 * @RequestParam аннотация используется для привязки параметров запроса к методу в вашем контроллере.
 * @PathVariable определяет шаблон, который используется в URI для входящего запроса.
 * PathVariable имеет только одно значение атрибута для привязки шаблона URI запроса
 * @ModelAttribute это аннотация, которая связывает параметр метода или возвращаемое значение метода с
 * именованным атрибутом модели, а затем предоставляет его веб-представлению.
 */
@Controller
public class FileController {
    //сервис объекта файл
    @Autowired
    private FileService fileService;
    //объекта сессии в сервлете объекта HttpServletRequest
    @Autowired
    private HttpSession httpSession;

    /**<p>Get маппинг на все файлы</p>
     * @param model объект который передает данные в представление
     * @return переход страницу allfiles
     */
    @GetMapping("/file/files")
    private String getAllFiles(Model model) {
        model.addAttribute("curpage", 1);
        model.addAttribute("pages", fileService.pages());
        model.addAttribute("files", fileService.paginationFiles(1));
        model.addAttribute("find", true);
        model.addAttribute("contentPage", "/file/allfiles");
        return "default";
    }

    /**<p>Get маппинг на все файлы с пеженацией</p>
     * @param model объект который передает данные в представление
     * @param page текущая страница
     * @return переход страницу allfiles
     */
    @GetMapping("/file/files/{page}")
    private String getFilesPaginationList(Model model, @PathVariable int page) {
        model.addAttribute("curpage", page);
        model.addAttribute("pages", fileService.pages());
        model.addAttribute("files", fileService.paginationFiles(page));
        model.addAttribute("find", true);
        model.addAttribute("contentPage", "/file/allfiles");
        return "default";
    }

    /**<p>Get маппинг поиска по файлам</p>
     * @param model объект который передает данные в представление
     * @param search фильтр поиска
     * @return переход страницу search
     */
    @GetMapping(value = "/file/files", params = { "search" })
    private String getSearchFileAndUser(Model model, String search) {
        if(fileService.searchList(search) == null || fileService.searchList(search).isEmpty()){
            model.addAttribute("pages",  1);
            model.addAttribute("contentPage", "/fragments/searchResultNullFiles");
        }else{
            model.addAttribute("pages",  1);
            model.addAttribute("files", fileService.searchList(search));
            model.addAttribute("find", true);
            model.addAttribute("contentPage", "/file/search");
        }
        return "default";
    }
    /**<p>Get маппинг поиска по файлам + пеженация</p>
     * @param model объект который передает данные в представление
     * @param search фильтр поиска
     * @return переход страницу search
     */
    @GetMapping(value = "/file/files/{page}", params = { "search" })
    private String getSearchFileAndUserPage(Model model, String search,  @PathVariable int page) {
        if(fileService.searchList(search) == null || fileService.searchList(search).isEmpty()){
            model.addAttribute("pages",  page);
            model.addAttribute("contentPage", "/fragments/searchResultNullFiles");
        }else{
            model.addAttribute("pages",  page);
            model.addAttribute("files", fileService.searchList(search));
            model.addAttribute("find", true);
            model.addAttribute("contentPage", "/file/search");
        }
        return "default";
    }

    /**<p>Get маппинг удаления файла</p>
     * @param idFile идентификатор файла
     * @param model объект который передает данные в представление
     * @return переход страницу delete
     */
    @GetMapping("/file/delete/{id}")
    private String delete(@PathVariable("id") Integer idFile, Model model) {
        model.addAttribute("file", fileService.getFile(idFile));
        model.addAttribute("contentPage", "/file/delete");
        return "default";
    }

    /**<p>Post маппинг удаления файла</p>
     * @param myfileId идентификатор файла
     * @return редирект на страницу myfiles
     * @throws IOException при обработке метода в маппинге может возникнуть исключение
     */
    @PostMapping("/file/delete")
    private String deleteSubmit(@ModelAttribute("id") Integer myfileId) throws IOException {
        User userSession = (User)httpSession.getAttribute("user");
        fileService.deleteFile(myfileId);
        return "redirect:/file/myfiles/"+userSession.getId();
    }

    /**<p>Get маппинг на список файлов пользователя</p>
     * @param idUser идентификатор пользователя
     * @param model  объект который передает данные в представление
     * @return переход страницу myfiles
     */
    @GetMapping("/file/myfiles/{id}")
    private  String myFiles(@PathVariable("id") Integer idUser, Model model){
            model.addAttribute("message", "You have no uploaded files!");
            model.addAttribute("curpage", 1);
            model.addAttribute("pages", fileService.myPages(idUser));
            model.addAttribute("myfiles", fileService.listUserFiles(idUser, 1));
            model.addAttribute("find", true);
            model.addAttribute("contentPage", "/file/myfiles");
        return "default";
    }

    /**<p>Get маппинг на список файлов пользователя + пеженация</p>
     * @param idUser идентификатор пользователя
     * @param page текущая страница
     * @param model  объект который передает данные в представление
     * @return переход страницу myfiles
     */
    @GetMapping("/file/myfiles/{id}/{page}")
    private  String myFiles(@PathVariable("id") Integer idUser, @PathVariable int page, Model model){
            model.addAttribute("message", "You have no uploaded files!");
            model.addAttribute("id", idUser);
            model.addAttribute("curpage", page);
            model.addAttribute("pages", fileService.myPages(idUser));
            model.addAttribute("myfiles", fileService.listUserFiles(idUser, page));
            model.addAttribute("find", true);
            model.addAttribute("contentPage", "/file/myfiles");
        return "default";
    }

    /**<p>Get маппинг поиска по файлам пользователя</p>
     * @param idUser идентификатор пользователя
     * @param model  объект который передает данные в представление
     * @param search фильтр поиска
     * @return переход страницу myfiles
     */
    @GetMapping(value = "/file/myfiles/{id}", params = { "search" })
    private String myFilesSearch(@PathVariable("id") Integer idUser, Model model, String search) {
        if(fileService.searchListMyfiles(idUser,  search) == null || fileService.searchListMyfiles(idUser,  search).isEmpty()){
            model.addAttribute("page", 1);
            model.addAttribute("contentPage", "/fragments/searchResultNullMyFiles");
        }else{
            model.addAttribute("page", 1);
            model.addAttribute("myfiles", fileService.searchListMyfiles(idUser,  search));
            model.addAttribute("find", true);
            model.addAttribute("contentPage", "/file/searchMyfiles");
        }
        return "default";
    }
    /**<p>Get маппинг поиска по файлам пользователя + пеженация</p>
     * @param idUser идентификатор пользователя
     * @param model  объект который передает данные в представление
     * @param search фильтр поиска
     * @param page текущая страница
     * @return переход страницу myfiles
     */
    @GetMapping(value = "/file/myfiles/{id}/{page}", params = { "search" })
    private String myFilesSearchPage(@PathVariable("id") Integer idUser, Model model, String search, @PathVariable int page) {
        if(fileService.searchListMyfiles(idUser, search) == null || fileService.searchListMyfiles(idUser, search).isEmpty()){
            model.addAttribute("page", page);
            model.addAttribute("contentPage", "/fragments/searchResultNullMyFiles");
        }else{
            model.addAttribute("page", page);
            model.addAttribute("myfiles", fileService.searchListMyfiles(idUser, search));
            model.addAttribute("find", true);
            model.addAttribute("contentPage", "/file/searchMyfiles");
        }
        return "default";
    }

    /**<p>Post маппинг добавления файла</p>
     * @param file обьект класса MultipartFile
     * @return редирект на страницу myfiles
     */
    @PostMapping("/file/addcsv")
    private String addcsvSubmit(@RequestParam("file") MultipartFile file) {
        User userSession = (User)httpSession.getAttribute("user");
        fileService.add(file, userSession.getId());
        return "redirect:/file/myfiles/"+userSession.getId();
    }

    /**<p>Get маппинг скачки файла</p>
     * @param idFile идентификатор файла
     * @param response обьект класса HttpServletResponse
     * @return редирект на страницу myfiles
     */
    @GetMapping("/file/dowload")
    private String dowloadFile(@ModelAttribute("idFile") Integer idFile, HttpServletResponse response) {
        User userSession = (User)httpSession.getAttribute("user");
        fileService.dowload(idFile, response);
        return "redirect:/file/myfiles/"+userSession.getId();
    }

    /**<p>Post маппинг на откритие доступа к файлу</p>
     * @param file обьект файла
     * @param read поле для чтения
     * @param edit поле для редактирования
     * @param dowload поле для скачки
     * @return редирект на страницу myfiles
     */
    @PostMapping("/file/share")
    private String share(@ModelAttribute("file") Myfile file,
                         @RequestParam(value = "read", required = false) Boolean read,
                         @RequestParam(value = "edit", required = false) Boolean edit,
                         @RequestParam(value = "dowload", required = false) Boolean dowload){
        User userSession = (User)httpSession.getAttribute("user");
        fileService.addLink(file, read, edit, dowload);
        return "redirect:/file/myfiles/"+userSession.getId();
    }

    /**<p>Get маппинг на откритие файлы</p>
     * @param model объект который передает данные в представление
     * @param idUser идентификатор пользователя
     * @return переход на страницу links
     */
    @GetMapping(value = "/file/links/{id}")
    private String openFiles(Model model, @PathVariable("id") Integer idUser) {
        model.addAttribute("message", "No open files");
        model.addAttribute("curpage", 1);
        model.addAttribute("pages", fileService.openPages(idUser));
        model.addAttribute("openfiles", fileService.getOpenFiles(idUser, 1));
        model.addAttribute("find", true);
        model.addAttribute("contentPage", "/file/links");
        return "default";
    }

    /**<p>Get маппинг на откритие файлы + пеженация</p>
     * @param model объект который передает данные в представление
     * @param idUser идентификатор пользователя
     * @param page текущая страница
     * @return переход на страницу links
     */
    @GetMapping(value = "/file/links/{id}/{page}")
    private String openFilesPagination(Model model, @PathVariable("id") Integer idUser, @PathVariable int page) {
        model.addAttribute("message", "No open files");
        model.addAttribute("curpage", page);
        model.addAttribute("pages", fileService.openPages(idUser));
        model.addAttribute("openfiles", fileService.getOpenFiles(idUser, page));
        model.addAttribute("find", true);
        model.addAttribute("contentPage", "/file/links");
        return "default";
    }

    /**<p>Get маппинг поиск по открытым файлам</p>
     * @param idUser идентификатор текущего пользователя
     * @param model объект который передает данные в представление
     * @param search фильтр поиска
     * @return список файлов найдених по фильтру
     */
    @GetMapping(value = "/file/links/{id}", params = { "search" })
    private String openFilesSearch(@PathVariable("id") Integer idUser, Model model, String search) {
        if(fileService.searchListOpenfiles(search, idUser) == null || fileService.searchListOpenfiles(search, idUser).isEmpty()){
            model.addAttribute("page", 1);
            model.addAttribute("contentPage", "/fragments/searchResultNullOpenFiles");
        }else{
            model.addAttribute("tolist",true);
            model.addAttribute("page", 1);
            model.addAttribute("openfiles", fileService.searchListOpenfiles(search, idUser));
            model.addAttribute("find", true);
            model.addAttribute("contentPage", "/file/searchLinks");
        }
        return "default";
    }

    /**<p>Get маппинг поиск по открытым файлам + пеженация</p>
     * @param idUser идентификатор текущего пользователя
     * @param model объект который передает данные в представление
     * @param search фильтр поиска
     * @param page текущая страница
     * @return список файлов найдених по фильтру
     */
    @GetMapping(value = "/file/links/{id}/{page}", params = { "search" })
    private String openFilesSearchPagination(@PathVariable("id") Integer idUser, Model model, String search, @PathVariable int page) {
        if(fileService.searchListOpenfiles(search, idUser) == null || fileService.searchListOpenfiles(search, idUser).isEmpty()){
            model.addAttribute("page", page);
            model.addAttribute("contentPage", "/fragments/searchResultNullOpenFiles");
        }else{
            model.addAttribute("tolist",true);
            model.addAttribute("page", page);
            model.addAttribute("openfiles", fileService.searchListOpenfiles(search, idUser));
            model.addAttribute("find", true);
            model.addAttribute("contentPage", "/file/searchLinks");
        }
        return "default";
    }
    /**<p>Post маппинг добавления файла по ссылке</p>
     * @param model объект который передает данные в представление
     * @param link ссылка доступа
     * @return редирект на страницу links
     */
    @PostMapping("/file/addlink")
    private String addLinkUser(Model model, @ModelAttribute("link") String link){
        User userSession = (User)httpSession.getAttribute("user");
        fileService.addLinkUser(userSession.getId(), link);
        model.addAttribute("openfiles", fileService.getOpenFiles(userSession.getId(),1));
        return "redirect:/file/links/"+userSession.getId();
    }

    /**<p>Get маппинг удаления файла по ссылке</p>
     * @param idFile идентификатор файла
     * @return редирект на страницу links
     */
    @GetMapping("/file/remove/{id}")
    private String removeFile(@PathVariable("id") Integer idFile) {
        User userSession = (User)httpSession.getAttribute("user");
        fileService.removeFile(idFile, userSession.getId());
        return "redirect:/file/links/"+userSession.getId();
    }

    /**<p>Get маппинг просмотра содержимого файла</p>
     * @param model объект который передает данные в представление
     * @param idFile идентификатор файла
     * @return переход на страницу opencsv
     * @throws Exception при обработке метода в маппинге может возникнуть исключение
     */
    @GetMapping("/file/open/{id}")
    private String openCSV(Model model, @PathVariable("id") Integer idFile) throws Exception {
        model.addAttribute("file", fileService.getFile(idFile));
        model.addAttribute("curpage", 1);
        model.addAttribute("pages", fileService.csvPages(idFile));
        model.addAttribute("csvfile", fileService.openCSV(idFile, 1));
        model.addAttribute("contentPage", "/file/opencsv");
        return "default";
    }

    /**<p>Get маппинг просмотра содержимого файла + пеженация</p>
     * @param model объект который передает данные в представление
     * @param idFile идентификатор файла
     * @param page текущая страница
     * @return переход на страницу opencsv
     * @throws Exception при обработке метода в маппинге может возникнуть исключение
     */
    @GetMapping("/file/open/{id}/{page}")
    private String paginationOpenCSV(Model model, @PathVariable("id") Integer idFile, @PathVariable int page) throws Exception {
        model.addAttribute("file", fileService.getFile(idFile));
        model.addAttribute("curpage", page);
        model.addAttribute("pages", fileService.csvPages(idFile));
        model.addAttribute("csvfile", fileService.openCSV(idFile, page));
        model.addAttribute("contentPage", "/file/opencsv");
        return "default";
    }

    /**<p>Post маппинг сохранения изменений в файле</p>
     * @param file обьект файла
     * @param page текущая страница
     * @param titleList лист заголовков файла
     * @param dataList лист данных файла
     * @param idList лис идентификаторв данных файла
     * @return редирект на страницу open
     * @throws Exception при обработке метода в маппинге может возникнуть исключение
     */
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

    /**<p>Post маппинг на добавления столбца в файл</p>
     * @param file обьект файла
     * @param page текущая страница
     * @param newColum название нового стобца
     * @return редирект на страницу open
     * @throws Exception при обработке метода в маппинге может возникнуть исключение
     */
    @PostMapping("/file/colum")
    private String addRowOpenCSV( @RequestParam("file") Myfile file,
                                  @RequestParam("curpage") int page,
                                @RequestParam("newColum") String newColum) throws Exception{
        fileService.addNewColum(file, newColum);
        return "redirect:/file/open/"+file.getId()+"/"+page;
    }

    /**<p>Get маппинг на добавления строки в файл</p>
     * @param idFile идентификатор файла
     * @param page текущая страница
     * @return редирект на страницу open
     * @throws Exception при обработке метода в маппинге может возникнуть исключение
     */
    @GetMapping("/file/row/{id}/{page}")
    private String addColumOpenCSV(@PathVariable("id") Integer idFile, @PathVariable("page") int page) throws Exception {
        fileService.addNewRow(idFile);
        return "redirect:/file/open/"+idFile+"/"+page;
    }

    /**<p>Post маппинг на удаления строк из файла</p>
     * @param file обьект файла
     * @param page текущая страница
     * @param rows список удаляемых строк
     * @return редирект на страницу open
     * @throws Exception при обработке метода в маппинге может возникнуть исключение
     */
    @PostMapping("/file/deleterows")
    public String deleteRows(@RequestParam("file") Myfile file,
                             @RequestParam("curpage") int page,
                             @RequestParam("deleterows") List<String> rows) throws Exception{
        fileService.deleteRows(file, rows);
        return "redirect:/file/open/"+file.getId()+"/"+page;
    }

    /**<p>Post маппинг на удаления столбцев из файла</p>
     * @param file обьект файла
     * @param page текущая страница
     * @param colums список удаляемых колонок
     * @return редирект на страницу open
     * @throws Exception при обработке метода в маппинге может возникнуть исключение
     */
    @PostMapping("/file/deletecolums")
    public String deleteColums(@RequestParam("file") Myfile file,
                             @RequestParam("curpage") int page,
                             @RequestParam("deletecolums") List<String> colums) throws Exception{
        fileService.deleteColums(file, colums);
        return "redirect:/file/open/"+file.getId()+"/"+page;
    }

    /**<p>Post маппинг по созданию чистого CSV файла</p>
     * @param filename название самого файла
     * @return редирект на список своих файлов
     */
    @PostMapping("/file/newFile")
    private String createNewFile(@RequestParam("filename") String filename) {
        int idUser = fileService.createNewFile(filename);
        return "redirect:/file/myfiles/"+idUser;
    }
}
