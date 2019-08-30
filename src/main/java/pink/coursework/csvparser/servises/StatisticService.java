package pink.coursework.csvparser.servises;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pink.coursework.csvparser.models.Myfile;
import pink.coursework.csvparser.models.Statistic;
import pink.coursework.csvparser.repositories.FileRepository;
import pink.coursework.csvparser.repositories.StatisticRepository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>Класс-сервис описывающий логику работы статистики</p>
 */
@Service
public class StatisticService {
    //количество вывода записей
    private static int STATICPAGE = 9;

    @Autowired
    private StatisticRepository statisticRepository;
    @Autowired
    private FileRepository fileRepository;
    /**
     * <p>Добавить запись</p>
     * @param userMail email пользователя
     * @param fileAction действие
     * @param fileName имя файла(уникальное)
     * @param fileOriginalName имя файла
     */
    public void add(String userMail, String fileAction, String fileName, String fileOriginalName){
        Statistic statistic = new Statistic();
        statistic.setUserName(userMail);
        if (fileAction.equals("Upload"))  {
            statistic.setUserCreate(true);
        }
        statistic.setFileAction(fileAction);
        statistic.setFileName(fileName);
        statistic.setFileNameOriginal(fileOriginalName);
        statistic.setDate(new Date());
        statisticRepository.save(statistic);
    }
    /**
     * <p>Количество страниц для пеженации</p>
     * @param idFile идентификатор файла
     * @return количество страниц
     */
    public int pages(Integer idFile) {
        return (int) Math.ceil((double) listStatsConcreteFile(idFile).size() / STATICPAGE);
    }
    /**
     * <p>Информация о файле</p>
     * <p>Список объектов что было сделано с конкретным файлом</p>
     * @param idFile идентификатор файла
     * @param page текущая страница
     * @return информация о файле
     */
    public List<Statistic> getFileStat(Integer idFile, int page) {
        List<Statistic> listStats = listStatsConcreteFile(idFile);
        List<Statistic> stats = new ArrayList<>();
        for (int i = (page - 1) * STATICPAGE; i < (page) * STATICPAGE && i < listStats.size(); i++) {
            stats.add(listStats.get(i));
        }
        return stats;
    }
    /**
     * <p>Вспомогательный метод для пеженации</p>
     * <p>Из всего списка возвращает информацию о конкретном файле</p>
     * @param idFile идентификатор файла
     * @return информация о конкретном файле
     */
    List<Statistic> listStatsConcreteFile(Integer idFile){
        Myfile file = fileRepository.getOne(idFile);
        List<Statistic> allStat = statisticRepository.findAll();
        List<Statistic> concretStats = new ArrayList<>();
        for (Statistic st : allStat){
            if(st.getFileName() == file.getName()){
                concretStats.add(st);
            }
        }
        return concretStats;
    }
}
