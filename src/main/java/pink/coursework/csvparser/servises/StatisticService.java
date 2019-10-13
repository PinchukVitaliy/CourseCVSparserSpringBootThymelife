package pink.coursework.csvparser.servises;

import org.hibernate.dialect.MyISAMStorageEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pink.coursework.csvparser.models.Myfile;
import pink.coursework.csvparser.models.Statistic;
import pink.coursework.csvparser.models.User;
import pink.coursework.csvparser.repositories.FileRepository;
import pink.coursework.csvparser.repositories.StatisticRepository;
import pink.coursework.csvparser.repositories.UserRepository;

import java.util.*;

/**
 * <p>Класс-сервис описывающий логику работы статистики</p>
 * @Service содержат бизнес-логику и вызывают методы на уровне хранилища.
 * @Autowired обеспечивает контроль над тем, где и как автосвязывание должны быть осуществленно.
 */
@Service
public class StatisticService {
    //количество вывода конкретной записи
    private static int STATICPAGE = 7;
    //количество вывода всех записей
    private static int ALLSTATICPAGE = 7;
    //количество вывода всех записей конкретного пользователя
    private static int MYSTATICPAGE = 7;

    @Autowired
    private UserRepository userRepository;

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
            if(st.getFileName().equals(file.getName())){
                concretStats.add(st);
            }
        }
        return concretStats;
    }
    /**
     * <p>Поиск по определенном файлу</p>
     * @param search фильтр поиска
     * @param idFile идентификатор файла
     * @return количество найдених совпадений
     */
    public List<Statistic> searchListStatFile(String search, Integer idFile) {
        search = search.trim();
        if(search.isEmpty()){
            return null;
        }
        List<Statistic> searchList = new ArrayList<>();
        List<Statistic> statisticsList = listStatsConcreteFile(idFile);
        for (int i = 0; i < statisticsList.size(); i++) {
            if (statisticsList.get(i).getFileAction().regionMatches(true, 0, search, 0, search.length())
                    || statisticsList.get(i).getFileNameOriginal().regionMatches(true, 0, search, 0, search.length())
                    || statisticsList.get(i).getUserName().regionMatches(true, 0, search, 0, search.length())
                    || statisticsList.get(i).getDate().toString().regionMatches(true, 0, search, 0, search.length())) {
                searchList.add(statisticsList.get(i));
            }
        }
        return searchList;
    }
    /**
     * <p>Очистить историю файла</p>
     * <p>Обнуляет всю историю, конкретного файла</p>
     * @param idFile идентификатор файла
     */
    public void clearStatFile(Integer idFile) {
        Myfile file = fileRepository.getOne(idFile);
        List<Statistic> allStat = statisticRepository.findAll();
        for (Statistic st : allStat){
            if(st.getFileName() == file.getName() && !st.getUserCreate()){
                statisticRepository.delete(st);
            }
        }
    }
    /**
     * <p>Количество страниц для пеженации</p>
     * <p>Количество страниц всех записей</p>
     * @return количество страниц
     */
    public int pagesAllStat() {
        return (int) Math.ceil((double) statisticRepository.findAll().size() / ALLSTATICPAGE);
    }
    /**
     * <p>Информация о всех файлах</p>
     * <p>Информация о всех файлах с пеженацией</p>
     * @return количество строк
     */
    public List<Statistic> allStatFiles(int page) {
        List<Statistic> listAllStats = statisticRepository.findAll();
        List<Statistic> pageStats = new ArrayList<>();
        for (int i = (page - 1) * ALLSTATICPAGE; i < (page) * ALLSTATICPAGE && i < listAllStats.size(); i++) {
            pageStats.add(listAllStats.get(i));
        }
        return pageStats;
    }
    /**
     * <p>Очистить всю историю</p>
     * <p>Обнуляет всю историю, всех файлов</p>
     */
    public void clearAllStats() {
            statisticRepository.deleteAll();
    }
    /**
     * <p>Поиск по всем файлам </p>
     * @param search фильтр поиска
     * @return количество найдених совпадений
     */
    public List<Statistic> searchListAllStat(String search) {
        if(search.isEmpty()){
            return null;
        }
        return  resultSearch(search);
    }
    /**
     * <p>Поиск по всем файлам</p>
     * <p>Возвращает результат поиска по событию, пользователю, файлу и дате
     * отдельно друг от друга</p>
     * @param search фильтр поиска
     * @return количество найдених совпадений
     */
    List<Statistic> resultSearch(String search){
        search = search.trim();
        List<Statistic> searchList = new ArrayList<>();
        List<Statistic> statisticsList = statisticRepository.findAll();
        for (int i = 0; i < statisticsList.size(); i++) {
            if (statisticsList.get(i).getFileAction().regionMatches(true, 0, search, 0, search.length())
                    || statisticsList.get(i).getFileNameOriginal().regionMatches(true, 0, search, 0, search.length())
                    || statisticsList.get(i).getUserName().regionMatches(true, 0, search, 0, search.length())
                    || statisticsList.get(i).getDate().toString().regionMatches(true, 0, search, 0, search.length())) {
                searchList.add(statisticsList.get(i));
            }
        }
        return searchList;
    }

    /**<p>Количество страниц для пеженации конкретного пользователя</p>
     * @param idUser идентификатор пользователя
     * @return  количество страниц
     */
    public int pagesMyStat(Integer idUser) {
        int count = 0;
        if(myStatsAll(idUser) != null){
            count = myStatsAll(idUser).size();
        }
        return (int) Math.ceil((double)  count / MYSTATICPAGE);
    }

    /**<p>Информация о всех файлах с пеженацией конкретного пользователя</p>
     * @param page текущая страница
     * @param idUser  идентификатор пользователя
     * @return список статистики
     */
    public List<Statistic> myStatFiles(int page, Integer idUser) {
        int count = 0;
        if(myStatsAll(idUser) != null){
            count = myStatsAll(idUser).size();
        }
        List<Statistic> paginMyStatsList = new ArrayList<>();
        for (int i = (page - 1) * MYSTATICPAGE; i < (page) * MYSTATICPAGE && i < count; i++) {
            paginMyStatsList.add(myStatsAll(idUser).get(i));
        }
        return paginMyStatsList;
    }

    /**<p>запись в коллекцию информации по всем файлам пользователя</p>
     * @param idUser идентификатор пользователя
     * @return список статистики
     */
    private List<Statistic> myStatsAll(Integer idUser){
        User user = userRepository.getOne(idUser);
        if(user.getListCreatedFiles().isEmpty() || user.getListCreatedFiles() == null){
            return null;
        }
        List<Statistic> listAllStats = statisticRepository.findAll();
        List<Statistic> listMyStats = new ArrayList<>();
        for (Myfile file : user.getListCreatedFiles()){
            for (Statistic stat : listAllStats) {
                if(stat.getFileName().equals(file.getName())){
                    listMyStats.add(stat);
                }
            }
        }
        return listMyStats;
    }
    /**
     * <p>Очистить всю историю одного пользователя</p>
     * <p>Обнуляет всю историю, всех файлов одного пользователя</p>
     */
    public void clearMyStats(Integer idUser) {
        statisticRepository.deleteAll(myStatsAll(idUser));
    }
}
