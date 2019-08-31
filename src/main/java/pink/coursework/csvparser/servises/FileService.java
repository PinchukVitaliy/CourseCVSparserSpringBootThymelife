package pink.coursework.csvparser.servises;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pink.coursework.csvparser.models.AccessLink;
import pink.coursework.csvparser.ObjectsHelperCSV.CsvModel;
import pink.coursework.csvparser.models.Myfile;
import pink.coursework.csvparser.models.User;
import pink.coursework.csvparser.repositories.AccessLinkRepository;
import pink.coursework.csvparser.repositories.FileRepository;
import pink.coursework.csvparser.repositories.UserRepository;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * <p>Класс-сервис описывающий логику работы файла</p>
 */
@Service
public class FileService {
    //путь к папке с файлами
    @Value("${file.path}")
    String filePath;
    //количество вывода файлов на одной странице
    private static int FILEPAGE = 7;
    //количество вывода файлов с открытым доступом на одной странице
    private static int OPENFILEPAGE = 7;
    //количество вывода строк из файла на одной странице
    private static int CSVFILEPAGE = 50;
    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccessLinkRepository accessLinkRepository;

    @Autowired
    private StatisticService statisticService;

    /**
     * <p>Поиск файла</p>
     * <p>Поиск файла по id</p>
     * @param id путь к файлу
     * @return обьект класса Myfile
     */
    public Myfile getFile(Integer id){
        return fileRepository.getOne(id);
    }
    /**
     * <p>Удаление файла</p>
     * <p>Удаляет указаый файл по заданаму id</p>
     * @param myfileId id удаляемого файла
     */
    public void deleteFile(Integer myfileId) throws IOException {
        Myfile file = fileRepository.getOne(myfileId);
        Path path = Paths.get(filePath + file.getName());
        User user = userRepository.getOne(file.getCreatorOfFile().getId());
        user.getListCreatedFiles().remove(file);
        userRepository.save(user);
        fileRepository.delete(file);
        Files.deleteIfExists(path);

        statisticService.add(
                user.getEmail(),
                "Delete",
                file.getName(),
                file.getOriginName());
    }
    /**
     * <p>Пагинация файлов</p>
     * <p>Возвращает умеренное количество записей</p>
     * @param page текущая страница
     * @return список файлов
     */
    public List<Myfile> paginationFiles(int page) {
        List<Myfile> allFilesUsers = fileRepository.findAll();
        List<Myfile> files = new ArrayList<>();
        for (int i = (page - 1) * FILEPAGE; i < (page) * FILEPAGE && i < allFilesUsers.size(); i++) {
            files.add(allFilesUsers.get(i));
        }
        return files;
    }
    /**
     * <p>Количетво страниц от всех файлов</p>
     * <p>Возвращает количество страниц</p>
     * @return число страниц
     */
    public int pages(){
        return (int) Math.ceil((double) fileRepository.findAll().size() / FILEPAGE);
    }
    /**
     * <p>Поиск по названию файла или пользователю</p>
     * <p>Возвращает список файлов по фильтру</p>
     * @param search фильтр поиска
     * @return список файлов
     */
    public List<Myfile> searchList(String search){
        List<Myfile> searchList = null;
        if(search.isEmpty()){
            return searchList;
        }
            List<Myfile> fileList = fileRepository.findAll();
            searchList = new ArrayList<Myfile>();
            for (int i = 0; i < fileList.size(); i++) {
                if (fileList.get(i).getOriginName().regionMatches(true, 0, search, 0, search.length())
                        ||
                fileList.get(i).getCreatorOfFile().getLogin().regionMatches(true,0,search,0,search.length())) {
                    searchList.add(fileList.get(i));
                }
            }
        return  searchList;
    }

    /**
     * <p>Список файлов у пользователя</p>
     * <p>Возвращает список файлов текущего пользователя
     * с уже умеренным количеством записей</p>
     * @param id идентификатор текущего пользователя
     * @param page текущая страница
     * @return список файлов
     */
    public List<Myfile> listUserFiles(Integer id, int page) {
        List<Myfile> filesUser = userRepository.getOne(id).getListCreatedFiles();
        List<Myfile> files = new ArrayList<>();
        for (int i = (page - 1) * FILEPAGE; i < (page) * FILEPAGE && i < filesUser.size(); i++) {
            files.add(filesUser.get(i));
        }
        /////////////test/////////////

        //files.get(1).setListReadUsers();
        ///////////////////////
        return files;
    }
    /**
     * <p>Количество страниц по текущему пользователю</p>
     * <p>Возвращает количество страниц текущего пользователя
     * для отображения всех его файлов</p>
     * @param id идентификатор текущего пользователя
     * @return количество страниц
     */
    public int myPages(Integer id){
        return (int) Math.ceil((double)  userRepository.getOne(id).getListCreatedFiles().size() / FILEPAGE);
    }
    /**
     * <p>Поиск по своим файлам</p>
     * <p>Возвращает список файлов по названию файла</p>
     * @param id идентификатор текущего пользователя
     * @param search фильтр поиска
     * @return список файлов
     */
    public List<Myfile> searchListMyfiles(Integer id, String search) {
        List<Myfile> searchList = new ArrayList<>();
        if(search.isEmpty()){
            return null;
        }
        List<Myfile> fileList = userRepository.getOne(id).getListCreatedFiles();
        searchList = new ArrayList<Myfile>();
            for (int i = 0; i < fileList.size(); i++) {
                if (fileList.get(i).getOriginName().regionMatches(true, 0, search, 0, search.length())) {
                    searchList.add(fileList.get(i));
                }
            }
        return  searchList;
    }
    /**
     * <p>Добавления нового файла</p>
     * <p>Загружает файл на сервер</p>
     * @param file объект класса MultipartFile
     * @param idUser идентификатор текущего пользователя
     */
    public void add(MultipartFile file, Integer idUser) {
        if(!file.isEmpty()){
            Myfile newfile = new Myfile();
            User user = userRepository.getOne(idUser);
            String resultFileName = generateUniqueFileName(file.getOriginalFilename());
            singleFileUpload(file, resultFileName);

            newfile.setOriginName(file.getOriginalFilename());
            newfile.setName(resultFileName);
            newfile.setCreatorOfFile(user);
            newfile.setAccessLink(new AccessLink());
            fileRepository.save(newfile);

            user.getListCreatedFiles().add(newfile);
            userRepository.save(user);

            statisticService.add(
                    user.getEmail(),
                    "Upload",
                    resultFileName,
                    file.getOriginalFilename());
        }
    }
    /**
     * <p>Вспомогательная функция к методу add</p>
     * <p>Занимается загрузкой файла на сервер</p>
     * @param file объект класса MultipartFile
     * @param resultFileName уникальное имя файла
     */
    public void singleFileUpload(MultipartFile file, String resultFileName) {
        Path path = Paths.get(filePath + resultFileName);

        try {
            byte[] bytes = file.getBytes();
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * <p>Генерация имени файла</p>
     * <p>Создает уникальное имя файла</p>
     * @param filename текущее имя файла
     * @return уникальное имя файла
     */
    public String generateUniqueFileName(String filename) {
        //определяем типфайла
        String extension = "";
        int i = filename.lastIndexOf('.');
        if (i > 0) {
            extension = filename.substring(i+1);
        }
        //генерируем имя файла с текущей датой
        long millis = System.currentTimeMillis();
        String datetime = new Date().toString();
        datetime = datetime.replace(" ", "");
        datetime = datetime.replace(":", "");
        filename = datetime + "_" + millis;
        return filename+"."+extension;
    }
    /**
     * <p>Скачка файла</p>
     * <p>Загрузка файла из сервера себе на ПО</p>
     * @param fileId идентификатор текущее файла
     * @param response объект класса HttpServletResponse
     */
    public void dowload(Integer fileId, HttpServletResponse response){
        Myfile myfile = fileRepository.getOne(fileId);
        //Добавить заголовок ответа HTTP с именем Content-Disposition
        //и присвойте ему значение attachment; filename=fileName,
        //где fileName — имя файла по умолчанию.
        Path file = Paths.get(filePath + myfile.getName());
        if (Files.exists(file)){
            response.setHeader("Content-disposition", "attachment;filename=" +  myfile.getOriginName());
            response.setContentType("application/vnd.ms-excel");
            try {
                Files.copy(file, response.getOutputStream());
                response.getOutputStream().flush();
                statisticService.add(
                        "autorise user",
                        "Dowload",
                        myfile.getName(),
                        myfile.getOriginName());
            } catch (IOException e) {
                throw new RuntimeException("IOError writing file to output stream");
            }
        }
    }
    /**
     * <p>Создает ссылку доступа над файлом</p>
     * <p>Открывает доступ к файлу и генерирует ссылку доступа</p>
     * @param file обьект класса Myfile
     * @param read параметр доступа на чтения
     * @param edit параметр доступа на изменения
     * @param dowload параметр доступа на скачку
     * @return обьект класса Myfile
     */
    public Myfile addLink(Myfile file, Boolean read, Boolean edit, Boolean dowload) {
        AccessLink link = accessLinkRepository.getOne(file.getAccessLink().getId());
        if(read == null && edit == null && dowload == null){
            link.setRead(false);
            link.setEdit(false);
            link.setDowload(false);
            link.setLink("No link");
            file.setAccessLink(link);
            fileRepository.save(file);
            statisticService.add(
                    "autorise user",
                    "Closed access to all",
                    file.getName(),
                    file.getOriginName());
            return  file;
        }
        if(read != null){
            link.setRead(read);
            statisticService.add(
                    "autorise user",
                    "Opened access to read",
                    file.getName(),
                    file.getOriginName());
        }else{
            link.setRead(false);
            statisticService.add(
                    "autorise user",
                    "Closed access to read",
                    file.getName(),
                    file.getOriginName());
        }
           if(edit != null){
               link.setEdit(edit);
               statisticService.add(
                       "autorise user",
                       "Opened access to edit",
                       file.getName(),
                       file.getOriginName());
           }else{
               link.setEdit(false);
               statisticService.add(
                       "autorise user",
                       "Closed access to edit",
                       file.getName(),
                       file.getOriginName());
           }
               if(dowload != null){
                   link.setDowload(dowload);
                   statisticService.add(
                           "autorise user",
                           "Opened access to dowload",
                           file.getName(),
                           file.getOriginName());
               }else{
                   link.setDowload(false);
                   statisticService.add(
                           "autorise user",
                           "Closed access to dowload",
                           file.getName(),
                           file.getOriginName());
               }
                    link.setLink(UUID.randomUUID().toString());
                    file.setAccessLink(link);
                    fileRepository.save(file);

       return file;
    }

    /**
     * <p>Доступные другие файлы</p>
     * <p>Список открытых файлов с пеженацией</p>
     * @param idUser идентификатор пользователя
     * @param page текущая страница
     * @return список файлов
     */
    public List<Myfile> getOpenFiles(Integer idUser, int page) {
        User user = userRepository.getOne(idUser);
        List<Myfile> files = new ArrayList<>();
        for (int i = (page - 1) * OPENFILEPAGE; i < (page) * OPENFILEPAGE && i < user.getListOpenFiles().size(); i++) {
            files.add(user.getListOpenFiles().get(i));
        }
        return files;
    }
    /**
     * <p>Количество страниц на открытых файлах</p>
     * @param idUser идентификатор пользователя
     * @return количество страниц
     */
    public int openPages(Integer idUser) {
        return (int) Math.ceil((double)  userRepository.getOne(idUser).getListOpenFiles().size() / OPENFILEPAGE);
    }
    /**
     * <p>Добавить ссылку</p>
     * <p>Добавляет в список с открытым доступом файлы по ссылке</p>
     * @param idUser идентификатор пользователя
     * @param link ссылка-доступ
     * @return true есть такая ссылка на файл false нету ссылки на файл
     */
    public Boolean addLinkUser(Integer idUser, String link) {
        AccessLink access = accessLinkRepository.findByLink(link);
        if(access == null){
            return false;
        }
        Myfile file = fileRepository.findByAccessLink(access);
        User user = userRepository.getOne(idUser);
        if(user.getListOpenFiles().contains(file)){
            return false;
        }
        user.getListOpenFiles().add(file);
        userRepository.save(user);
        return true;
    }
    /**
     * <p>Удалить пустую ссылку</p>
     * <p>Удаляет из коллекции открытых файлов сам файл</p>
     * @param idFile идентификатор файла
     * @param idUser идентификатор пользователя
     */
    public void removeFile(Integer idFile, Integer idUser) throws IOException {
        Myfile file = fileRepository.getOne(idFile);
        User user = userRepository.getOne(idUser);
        user.getListOpenFiles().remove(file);
        userRepository.save(user);
    }
    /**
     * <p>Открыть CSV</p>
     * <p>Открывает содержимое CSV-файла с пеженецией</p>
     * @param idFile идентификатор файла
     * @param page текущая страница
     * @return обьект  CsvModel
     */
    public CsvModel openCSV(Integer idFile, int page) throws Exception {
        Myfile fileCsv = fileRepository.getOne(idFile);
        CsvModel csvModel = new CsvModel(filePath + fileCsv.getName());
        csvModel.getListRowsData( page, CSVFILEPAGE);
        return csvModel;
    }
    /**
     * <p>Количество страниц в CSV</p>
     * @param idFile идентификатор файла
     * @return количество записей
     */
    public int csvPages(Integer idFile) throws Exception {
        Myfile fileCsv = fileRepository.getOne(idFile);
        CsvModel csvModel = new CsvModel(filePath + fileCsv.getName());
        return (int) Math.ceil((double) csvModel.countRows()/ CSVFILEPAGE);
    }
    /**
     * <p>Сохранить CSV</p>
     * <p>Сохраняет изминения</p>
     * @param title список заголовков
     * @param dataList список данных
     * @param idList список id dataList
     * @param file обьект класса Myfile
     */
    public void getCsvModelSave(List<String> title, List<String> dataList, List<Integer> idList, Myfile file) throws Exception{
        Myfile fileCsv = fileRepository.getOne(file.getId());
        CsvModel csvModel = new CsvModel(filePath + fileCsv.getName());
        csvModel.writeModifiedData(title,dataList,idList);
        csvModel.saveCsv( filePath + fileCsv.getName());
        statisticService.add(
                "autorise user",
                "Saved changes",
                fileCsv.getName(),
                fileCsv.getOriginName());
    }
    /**
     * <p>Добавить колонку в CSV</p>
     * <p>Создает и добавляет новую колонку</p>
     * @param file обьект класса Myfile
     * @param newRow назнвние новой колонки
     */
    public void addNewRow(Myfile file, String newRow) throws Exception {
        Myfile fileCsv = fileRepository.getOne(file.getId());
        CsvModel csvModel = new CsvModel(filePath + fileCsv.getName());
        csvModel.addRow(filePath + fileCsv.getName(), newRow);
        statisticService.add(
                "autorise user",
                "Add Row",
                fileCsv.getName(),
                fileCsv.getOriginName());
    }
    /**
     * <p>Добавить строку в CSV</p>
     * <p>Создает и добавляет новую строку</p>
     * @param idFile дентификатор файла
     */
    public void addNewColum(Integer idFile) throws Exception {
        Myfile fileCsv = fileRepository.getOne(idFile);
        CsvModel csvModel = new CsvModel(filePath + fileCsv.getName());
        csvModel.addColum(filePath + fileCsv.getName());
        statisticService.add(
                "autorise user",
                "Add colum",
                fileCsv.getName(),
                fileCsv.getOriginName());
    }
    /**
     * <p>Удалить строки в CSV</p>
     * @param file обьект класса Myfile
     * @param colums список удаляемых строк
     */
    public void deleteColums(Myfile file, List<String> colums) throws Exception{
        Myfile fileCsv = fileRepository.getOne(file.getId());
        CsvModel csvModel = new CsvModel(filePath + fileCsv.getName());
        csvModel.deleteColumsCSV(filePath + fileCsv.getName(), colums);
        statisticService.add(
                "autorise user",
                "Delete colums",
                fileCsv.getName(),
                fileCsv.getOriginName());
    }
    /**
     * <p>Удалить колонки в CSV</p>
     * @param file обьект класса Myfile
     * @param rows список удаляемых колонок
     */
    public void deleteRows(Myfile file, List<String> rows) throws Exception{
        Myfile fileCsv = fileRepository.getOne(file.getId());
        CsvModel csvModel = new CsvModel(filePath + fileCsv.getName());
        csvModel.deleteRowsCSV(filePath + fileCsv.getName(), rows);
        statisticService.add(
                "autorise user",
                "Delete rows",
                fileCsv.getName(),
                fileCsv.getOriginName());
    }
}