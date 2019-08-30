package pink.coursework.csvparser.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pink.coursework.csvparser.servises.StatisticService;

@Controller
public class StatisticController {
    @Autowired
    private StatisticService statisticService;

    @GetMapping("/statistic/{id}")
    private String getStatFile(Model model,  @PathVariable("id") Integer idFile) {
        model.addAttribute("curpage", 1);
        model.addAttribute("pages", statisticService.pages(idFile));
        model.addAttribute("statistic", statisticService.getFileStat(idFile, 1));
        model.addAttribute("message", "No file statistics");
        model.addAttribute("contentPage", "/statistic/fileStat");
        return "default";
    }
    @GetMapping("/statistic/{id}/{page}")
    private String getStatFilePaginationList(Model model, @PathVariable("id") Integer idFile, @PathVariable("page")  int page) {
        model.addAttribute("curpage", page);
        model.addAttribute("pages", statisticService.pages(idFile));
        model.addAttribute("statistic", statisticService.getFileStat(idFile, page));
        model.addAttribute("message", "No file statistics");
        model.addAttribute("contentPage", "/statistic/fileStat");
        return "default";
    }
}
