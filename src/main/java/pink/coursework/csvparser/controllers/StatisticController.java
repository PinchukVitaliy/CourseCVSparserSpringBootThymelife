package pink.coursework.csvparser.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pink.coursework.csvparser.servises.StatisticService;

import java.text.ParseException;
/**
 * Контроллер класса статистики
 * @Controller определяет класс как Контроллер Spring MVC
 * @Autowired обеспечивает контроль над тем, где и как автосвязывание должны быть осуществленно.
 * @GetMapping аннотация для отображения HTTP- GET запросов на определенные методы-обработчики.
 * В частности, @GetMapping это составная аннотация, которая действует как ярлык для @RequestMapping(method = RequestMethod.GET).
 * @PathVariable определяет шаблон, который используется в URI для входящего запроса.
 * PathVariable имеет только одно значение атрибута для привязки шаблона URI запроса
 */
@Controller
public class StatisticController {
    //сервис класса статистики
    @Autowired
    private StatisticService statisticService;

    /**<p>Get маппинг на статистику одного файла</p>
     * @param model объект который передает данные в представление
     * @param idFile идентификатор файла
     * @return переход на страницу fileStat
     */
    @GetMapping("/statistic/{id}")
    private String getStatFile(Model model,  @PathVariable("id") Integer idFile) {
        model.addAttribute("curpage", 1);
        model.addAttribute("idFile",idFile);
        model.addAttribute("pages", statisticService.pages(idFile));
        model.addAttribute("statistic", statisticService.getFileStat(idFile, 1));
        model.addAttribute("message", "No records");
        model.addAttribute("contentPage", "/statistic/fileStat");
        return "default";
    }

    /**<p>Get маппинг на статистику одного файла + пеженация</p>
     * @param model объект который передает данные в представление
     * @param idFile идентификатор файла
     * @param page текущая страница
     * @return переход на страницу fileStat
     */
    @GetMapping("/statistic/{id}/{page}")
    private String getStatFilePaginationList(Model model, @PathVariable("id") Integer idFile, @PathVariable("page")  int page) {
        model.addAttribute("curpage", page);
        model.addAttribute("idFile",idFile);
        model.addAttribute("pages", statisticService.pages(idFile));
        model.addAttribute("statistic", statisticService.getFileStat(idFile, page));
        model.addAttribute("message", "No records");
        model.addAttribute("contentPage", "/statistic/fileStat");
        return "default";
    }

    /**<p>Get маппинг на поиск статистики одного файла</p>
     * @param model объект который передает данные в представление
     * @param search фильтр поиска
     * @param idFile идентификатор файла
     * @return если есть результат переход на страницу fileStatSearch или на searchResultNullStatFile
     */
    @GetMapping(value = "/statistic/{id}", params = { "search" })
    private String statFileSearch(Model model, String search,  @PathVariable("id") Integer idFile)  {
        if(statisticService.searchListStatFile(search, idFile) == null || statisticService.searchListStatFile(search, idFile).isEmpty()){
            model.addAttribute("contentPage", "/fragments/searchResultNullStatFile");
            model.addAttribute("idFile",idFile);
            model.addAttribute("pages",  1);
        }else{
            model.addAttribute("pages",  1);
            model.addAttribute("idFile",idFile);
            model.addAttribute("statistic", statisticService.searchListStatFile(search, idFile));
            model.addAttribute("message", "No records");
            model.addAttribute("contentPage", "/statistic/fileStatSearch");
        }
        return "default";
    }

    /**<p>Get маппинг на поиск статистики одного файла + пеженация</p>
     * @param model объект который передает данные в представление
     * @param search фильтр поиска
     * @param page текущая страница
     * @param idFile идентификатор файла
     * @return если есть результат переход на страницу fileStatSearch или на searchResultNullStatFile
     */
    @GetMapping(value = "/statistic/{id}/{page}", params = { "search" })
    private String statFileSearchPage(Model model, String search, @PathVariable int page,  @PathVariable("id") Integer idFile)  {
        if(statisticService.searchListStatFile(search, idFile) == null || statisticService.searchListStatFile(search, idFile).isEmpty()){
            model.addAttribute("contentPage", "/fragments/searchResultNullStatFile");
            model.addAttribute("idFile",idFile);
            model.addAttribute("pages",  page);
        }else{
            model.addAttribute("pages",  page);
            model.addAttribute("idFile", idFile);
            model.addAttribute("statistic", statisticService.searchListStatFile(search, idFile));
            model.addAttribute("message", "No records");
            model.addAttribute("contentPage", "/statistic/fileStatSearch");
        }
        return "default";
    }

    /**<p>Get маппинг на очистку статистики одного файла</p>
     * @param idFile идентификатор файла
     * @return редирект на страницу statistic
     */
    @GetMapping("/statistic/clearFile/{id}")
    private String clearStatFile(@PathVariable("id") Integer idFile) {
        statisticService.clearStatFile(idFile);
        return "redirect:/statistic/"+idFile;
    }

    /**<p>Get маппинг на статистики всех файлов</p>
     * @param model объект который передает данные в представление
     * @return переход на страницу allStats
     */
    @GetMapping("/statistic/all")
    private String allStatFile(Model model) {
        model.addAttribute("curpage", 1);
        model.addAttribute("pages", statisticService.pagesAllStat());
        model.addAttribute("allStatFiles",statisticService.allStatFiles(1));
        model.addAttribute("message", "No records");
        model.addAttribute("contentPage", "/statistic/allStats");
        return "default";
    }

    /**<p>Get маппинг на статистику всех файлов + пеженация</p>
     * @param model объект который передает данные в представление
     * @param page текущая страница
     * @return переход на страницу allStats
     */
    @GetMapping("/statistic/all/{page}")
    private String allStatFilePagination(Model model,  @PathVariable int page) {
        model.addAttribute("curpage", page);
        model.addAttribute("pages", statisticService.pagesAllStat());
        model.addAttribute("allStatFiles",statisticService.allStatFiles(page));
        model.addAttribute("message", "No records");
        model.addAttribute("contentPage", "/statistic/allStats");
        return "default";
    }

    /**<p>Get маппинг на поиск статистики всех файлов</p>
     * @param model объект который передает данные в представление
     * @param search фильтр поиска
     * @return если есть результат переход на страницу allStatsSearch или на searchResultNullAllStat
     */
    @GetMapping(value = "/statistic/all", params = { "search" })
    private String allStatsSearch(Model model, String search)  {
        if(statisticService.searchListAllStat(search) == null || statisticService.searchListAllStat(search).isEmpty()){
            model.addAttribute("contentPage", "/fragments/searchResultNullAllStat");
            model.addAttribute("pages",  1);
        }else{
            model.addAttribute("tolist",1);
            model.addAttribute("allStatFiles", statisticService.searchListAllStat(search));
            model.addAttribute("contentPage", "/statistic/allStatsSearch");
        }
        return "default";
    }

    /**<p>Get маппинг на поиск статистики всех файлов + пеженация</p>
     * @param model объект который передает данные в представление
     * @param search фильтр поиска
     * @param page текущая страница
     * @return если есть результат переход на страницу allStatsSearch или на searchResultNullAllStat
     */
    @GetMapping(value = "/statistic/all/{page}", params = { "search" })
    private String allStatsSearchPage(Model model, String search, @PathVariable int page)  {
        if(statisticService.searchListAllStat(search) == null || statisticService.searchListAllStat(search).isEmpty()){
            model.addAttribute("contentPage", "/fragments/searchResultNullAllStat");
            model.addAttribute("pages",  page);
        }else{
            model.addAttribute("tolist", page);
            model.addAttribute("allStatFiles", statisticService.searchListAllStat(search));
            model.addAttribute("contentPage", "/statistic/allStatsSearch");
        }
        return "default";
    }

    /**<p>Get маппинг на очистку всей статистики</p>
     * @return редирект на страницу all
     */
    @GetMapping("/statistic/clearAllStat")
    private String clearAllStats() {
            statisticService.clearAllStats();
        return "redirect:/statistic/all";
    }

    /**<p>Get маппинг на статистику всех файлов одного пользователя</p>
     * @param model объект который передает данные в представление
     * @param idUser идентификатор пользователя
     * @return переход на страницу myStats
     */
    @GetMapping("/statistic/myStat/{id}")
    private String myStatFile(Model model, @PathVariable("id") Integer idUser) {
        model.addAttribute("curpage", 1);
        model.addAttribute("pages", statisticService.pagesMyStat(idUser));
        model.addAttribute("myStatFiles",statisticService.myStatFiles(1, idUser));
        model.addAttribute("message", "No records");
        model.addAttribute("contentPage", "/statistic/myStats");
        return "default";
    }

    /**<p>Get маппинг на статистику всех файлов одного пользователя + пеженация</p>
     * @param model объект который передает данные в представление
     * @param idUser идентификатор пользователя
     * @param page текущая страница
     * @return переход на страницу myStats
     */
    @GetMapping("/statistic/myStat/{id}/{page}")
    private String myStatFilePagination(Model model, @PathVariable("id") Integer idUser, @PathVariable int page) {
        model.addAttribute("curpage", page);
        model.addAttribute("pages", statisticService.pagesMyStat(idUser));
        model.addAttribute("myStatFiles",statisticService.myStatFiles(page, idUser));
        model.addAttribute("message", "No records");
        model.addAttribute("contentPage", "/statistic/myStats");
        return "default";
    }

    /**<p>Get маппинг на очистку всей статистики одного пользователя</p>
     * @return редирект на страницу all
     */
    @GetMapping("/statistic/clearMyStat/{id}")
    private String clearMyStats(@PathVariable("id") Integer idUser) {
        statisticService.clearMyStats(idUser);
        return "redirect:/statistic/myStat/"+idUser;
    }
}
