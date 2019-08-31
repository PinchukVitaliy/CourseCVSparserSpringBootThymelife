package pink.coursework.csvparser.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pink.coursework.csvparser.servises.StatisticService;

import java.text.ParseException;

@Controller
public class StatisticController {
    @Autowired
    private StatisticService statisticService;

    @GetMapping("/statistic/{id}")
    private String getStatFile(Model model,  @PathVariable("id") Integer idFile) {
        model.addAttribute("curpage", 1);
        model.addAttribute("idFile",idFile);
        model.addAttribute("pages", statisticService.pages(idFile));
        model.addAttribute("statistic", statisticService.getFileStat(idFile, 1));
        model.addAttribute("message", "No file statistics");
        model.addAttribute("contentPage", "/statistic/fileStat");
        return "default";
    }
    @GetMapping("/statistic/{id}/{page}")
    private String getStatFilePaginationList(Model model, @PathVariable("id") Integer idFile, @PathVariable("page")  int page) {
        model.addAttribute("curpage", page);
        model.addAttribute("idFile",idFile);
        model.addAttribute("pages", statisticService.pages(idFile));
        model.addAttribute("statistic", statisticService.getFileStat(idFile, page));
        model.addAttribute("message", "No file statistics");
        model.addAttribute("contentPage", "/statistic/fileStat");
        return "default";
    }
    @GetMapping(value = "/statistic/{id}", params = { "search" })
    private String statFileSearch(Model model, String search,  @PathVariable("id") Integer idFile)  {
        if(statisticService.searchListStatFile(search, 1) == null || statisticService.searchListStatFile(search, 1).isEmpty()){
            model.addAttribute("contentPage", "/fragments/searchResultNullStatFile");
            model.addAttribute("pages",  1);
        }else{
            model.addAttribute("tolist",true);
            model.addAttribute("curpage", 1);
            model.addAttribute("idFile",idFile);
            model.addAttribute("pages",  statisticService.statFileSearchPages(search));
            model.addAttribute("statistic", statisticService.searchListStatFile(search, 1));
            model.addAttribute("message", "No file statistics");
            model.addAttribute("contentPage", "/statistic/fileStat");
        }
        return "default";
    }
    @GetMapping(value = "/statistic/{id}/{page}", params = { "search" })
    private String statFileSearchPage(Model model, String search, @PathVariable int page,  @PathVariable("id") Integer idFile)  {
        if(statisticService.searchListStatFile(search, page) == null || statisticService.searchListStatFile(search, page).isEmpty()){
            model.addAttribute("contentPage", "/fragments/searchResultNullStatFile");
            model.addAttribute("pages",  page);
        }else{
            model.addAttribute("tolist",true);
            model.addAttribute("curpage", page);
            model.addAttribute("idFile",idFile);
            model.addAttribute("pages", statisticService.statFileSearchPages(search));
            model.addAttribute("statistic", statisticService.searchListStatFile(search, page));
            model.addAttribute("message", "No file statistics");
            model.addAttribute("contentPage", "/statistic/fileStat");
        }
        return "default";
    }
    @GetMapping("/statistic/clearFile/{id}")
    private String clearStatFile(@PathVariable("id") Integer idFile) {
        statisticService.clearStatFile(idFile);
        return "redirect:/statistic/"+idFile;
    }
    @GetMapping("/statistic/all")
    private String allStatFile(Model model) {
        model.addAttribute("curpage", 1);
        model.addAttribute("pages", statisticService.pagesAllStat());
        model.addAttribute("allStatFiles",statisticService.allStatFiles(1));
        model.addAttribute("message", "No file statistics");
        model.addAttribute("contentPage", "/statistic/allStats");
        return "default";
    }
    @GetMapping("/statistic/all/{page}")
    private String allStatFilePagination(Model model,  @PathVariable int page) {
        model.addAttribute("curpage", page);
        model.addAttribute("pages", statisticService.pagesAllStat());
        model.addAttribute("allStatFiles",statisticService.allStatFiles(page));
        model.addAttribute("message", "No file statistics");
        model.addAttribute("contentPage", "/statistic/allStats");
        return "default";
    }
    @GetMapping(value = "/statistic/all", params = { "search" })
    private String allStatsSearch(Model model, String search)  {
        if(statisticService.searchListAllStat(search, 1) == null || statisticService.searchListAllStat(search, 1).isEmpty()){
            model.addAttribute("contentPage", "/fragments/searchResultNullAllStat");
            model.addAttribute("pages",  1);
        }else{
            model.addAttribute("tolist",true);
            model.addAttribute("curpage", 1);
            model.addAttribute("pages",  statisticService.allStatSearchPages(search));
            model.addAttribute("allStatFiles", statisticService.searchListAllStat(search, 1));
            model.addAttribute("contentPage", "/statistic/allStats");
        }
        return "default";
    }
    @GetMapping(value = "/statistic/all/{page}", params = { "search" })
    private String allStatsSearchPage(Model model, String search, @PathVariable int page)  {
        if(statisticService.searchListAllStat(search, page) == null || statisticService.searchListAllStat(search, page).isEmpty()){
            model.addAttribute("contentPage", "/fragments/searchResultNullAllStat");
            model.addAttribute("pages",  page);
        }else{
            model.addAttribute("tolist",true);
            model.addAttribute("curpage", page);
            model.addAttribute("pages", statisticService.allStatSearchPages(search));
            model.addAttribute("allStatFiles", statisticService.searchListAllStat(search, page));
            model.addAttribute("contentPage", "/statistic/allStats");
        }
        return "default";
    }
    @GetMapping("/statistic/clearAllStat")
    private String clearAllStats() {
            statisticService.clearAllStats();
        return "redirect:/statistic/all";
    }
}
