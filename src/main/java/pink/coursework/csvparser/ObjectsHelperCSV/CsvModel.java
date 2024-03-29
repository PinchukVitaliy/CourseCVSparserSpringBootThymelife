package pink.coursework.csvparser.ObjectsHelperCSV;

import com.opencsv.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>Класс описывает работу над CSV файлом</p>
 * <p>Множество методов для открытия файла и работой над содержимым</p>
 */
public class CsvModel implements Serializable {
    //список который хранит все названия заголовка
    private List<String> TitleCsv;
    //список DataCsv - тело с данными
    private List<DataCsv> DataModelRows;
    //поле которое хранит сепаратор файла
    private char seperator;
    //экземпляр класса CSVParser
    private CSVParser csvParser;
    //экземпляр класса CSVReader
    private  CSVReader csvReader;
    //экземпляр класса BufferedReader
    private BufferedReader br;
    //конструктор
    public CsvModel() {
    }
    /**
     * <p>Конструктор с параметром </p>
     * <p>При создании екзепляра класса создаем поля для работы над ним</p>
     *  @param PathFileName путь к файлу
     *  @throws Exception этот коструктор работает с файловой системой которая может вызвать ошибку
     */
    public CsvModel(String PathFileName) throws Exception {
        this.br = new BufferedReader(new FileReader(PathFileName));
        this.seperator = seperator();
        this.csvParser = new CSVParserBuilder().withSeparator(this.seperator).build();
        this.csvReader = new CSVReaderBuilder(new FileReader(PathFileName)).withCSVParser(csvParser).build();
    }
    //геттеры и сеттеры полей
    public List<String> getTitleCsv() {
        return TitleCsv;
    }

    public void setTitleCsv(List<String> titleCsv) {
        TitleCsv = titleCsv;
    }

    public List<DataCsv> getDataModelRows() {
        return DataModelRows;
    }

    public void setDataModelRows(List<DataCsv> dataModelRows) {
        DataModelRows = dataModelRows;
    }

    /**
     * <p>Запись данных + пеженация</p>
     * <p>Метод считывает определенный файл из уже существующего CSVReader
     * и записывает данные в поля чтобы передать объект класса в Представление</p>
     * @param page если данных много определяет на какой странице отображать их
     * @param CSVFILEPAGE константа сколько будет max записей
     * @throws Exception этот метод работает с файловой системой которая может вызвать ошибку
     */
    public void getListRowsData(int page, int CSVFILEPAGE) throws Exception{
        List<List<String>> records = new ArrayList<>();
        String[] values = null;
        while ((values = csvReader.readNext()) != null) {
            records.add(Arrays.asList(values));
        }

        setTitleCsv(records.get(0));

        int count  =  records.get(0).size();
        List<DataCsv> dataCsvs = new ArrayList<>();
        for (int i = (page - 1) * CSVFILEPAGE; i < (page) * CSVFILEPAGE && i < records.size(); i++) {
            if(i == 0){
                continue;
            }
            dataCsvs.add(new DataCsv(i, emptyCells(count, records.get(i))));
        }

        setDataModelRows(dataCsvs);
        clearCsvReader();
    }
    /**
     * <p>Число записей</p>
     * <p>Сколько всего записей в файле</p>
     * @return количество записей
     * @throws Exception этот метод работает с файловой системой которая может вызвать ошибку
     */
    public int countRows() throws Exception {
        int count = 0;
        while (csvReader.readNext() != null) {
            count++;
        }
        clearCsvReader();
        return count;
    }
    /**
     * <p>Определяет сепаратор(разделитель)</p>
     * @return возвращает сепаратор
     * @throws Exception этот метод работает с файловой системой которая может вызвать ошибку
     */
    public char seperator() throws Exception {
        String line = "";
        char csvSplitBy = 0;
        while ((line = br.readLine()) != null) {
            // use comma as separator
            if (line.contains(",")) {
                csvSplitBy = ',';
            }else if (line.contains("|")) {
                csvSplitBy = '|';
            } else if (line.contains(";")) {
                csvSplitBy = ';';
            } else if (line.contains("\t")) {
                csvSplitBy = '\t';
            } else {
                csvSplitBy = ',';
            }
            break;
        }
        clearBuffer();
        return csvSplitBy;
    }
    /**
     * <p>Заполнение пустых ячеек если они есть в CSV</p>
     * <p>Если в строках нет разделителя и ячейка не содержит данных,
     * данные парсятся и в пустоту проставляеться чистая строка для корректного отображения</p>
     * @param rowCount количество ячеек в заголовке (по которому строится весь файл)
     * @param record текущие данные которые проверяют на пустоту
     * @return возвращается лист с корректными данными
     */
    public List<String> emptyCells(int rowCount, List<String> record){
        List<String> result = new ArrayList<>();
        if(record.size() > rowCount){
            return null;
        }
        if(record.size() != rowCount){
            for (String elem : record) {
                result.add(elem);
            }
            int counter = rowCount - record.size();
            while (counter != 0){
                counter--;
                result.add("");
            }
            return result;
        }
        else{
            return record;
        }
    }
    /**
     * <p>Вспомогательный метод для saveCsv</p>
     * <p>Проверяет в каждой строке если пустота</p>
     * @param id элемент по которому идет сравнение
     * @return емли есть истина нет лож
     */
    public boolean getDataCsv(Integer id){
        for (DataCsv elem : DataModelRows){
            if(elem.getId().equals(id))
                return true;
        }
        return false;
    }
    /**
     * <p>Вспомогательный метод для saveCsv</p>
     * <p>Конвертируем список в массив</p>
     * @param id элемент по которому идет сравнение
     * @return true идет перезапись false нет
     */
    public String[] getDataCsvMass(Integer id){
        for (DataCsv elem : DataModelRows){
            if(elem.getId().equals(id))
                return elem.getDataRows().toArray(new String[0]);
        }
        return null;
    }
    /**
     * <p>Перезапись новых данных в файл</p>
     * <p>Сохраняет измененные данные из ранее записанных полей объекта
     * при помощи метода writeModifiedData</p>
     * @param PathFileName путь к файлу для открытия CSVWriter текущего файла
     * @throws Exception этот метод работает с файловой системой которая может вызвать ошибку
     */
    public void saveCsv(String PathFileName) throws Exception {
        List<String[]> csvBody = new ArrayList<>();
        String[] values = null;
        int count = 0;
        csvBody.add(removeAllSimvol(getTitleCsv().toArray(new String[0])));
        while ((values = csvReader.readNext()) != null) {
            if(count != 0){
                if(getDataCsv(count)){
                        csvBody.add(getDataCsvMass(count));
                }else{
                    csvBody.add(values);
                }
            }
            count++;
        }
        clearCsvReader();
        CSVWriter csvWriter = openCsvWriter(PathFileName);
        csvWriter.writeAll(csvBody);
        clearCsvWriter(csvWriter);
    }

    /**<p>Вспоагательный метод к saveCsv</p>
     * <p>замена символы ',' и '|' на "%" в шапке таблицы</p>
     * @param simvolArray изменяемые данные
     * @return исправленные данные изменяются
     */
    private String[] removeAllSimvol(String[] simvolArray){
        String[] array = new String[simvolArray.length];
        for(int i = 0; i < simvolArray.length; i++)
        {
            array[i] = simvolArray[i]
                    .replace(',', '%')
                    .replace('|', '%');
        }
        return array;
    }

    /**
     * <p>создание новых данных и запись их в объект</p>
     * <p>Метод принимает данные и записывает их в поля класса</p>
     * @param title данные шапки
     * @param dataList тело с данными
     * @param idList id списков да поля DataModelRows (класс помощник)
     */
    public void writeModifiedData(List<String> title, List<String> dataList, List<Integer> idList){
        title = title.stream().map(String::trim).collect(Collectors.toList());
        dataList = dataList.stream().map(String::trim).collect(Collectors.toList());

            if(dataList != null && title.size() != 0) {
                setTitleCsv(searchSameElements(title));
                List<DataCsv> dataCsvList = new ArrayList<>();
                int count = searchSameElements(title).size();
                int endCount = dataList.size() / count;
                int start = 0;
                for (int i = 0; i < endCount; i++) {
                    List<String> stringList = new ArrayList<>();
                    for (int j = start; j < start + count; j++) {
                        stringList.add(dataList.get(j));
                    }
                    start += count;
                    dataCsvList.add(new DataCsv(idList.get(i), stringList));
                }
                setDataModelRows(dataCsvList);
            }else{
                if(searchSameElements(title).size() == 0){
                    List<String> nontitle = new ArrayList<>();
                    nontitle.add("");
                    setTitleCsv(nontitle);
                }else{
                    setTitleCsv(searchSameElements(title));
                }
                List<DataCsv> list = new ArrayList<>();
                setDataModelRows(list);
            }
    }
    /**
     * <p>Поиск одинаковых элементов  writeModifiedData</p>
     * <p>Если есть одинаковый элемент меняем ему название</p>
     * @param title текущий список
     * @return уникальный список
     */
    List<String> searchSameElements(List<String> title){
        Set<String> checkDuplicates = new HashSet<String>();
        List<String> list = new ArrayList<>();
        int k = 1;
        for (int i = 0; i < title.size(); i++) {
            String items = title.get(i);
            if (!checkDuplicates.add(items)) {
                list.add(items+"(dublicat+"+k+")");
                k++;
            }
            else {
                list.add(items);
            }
        }
        return list;
    }
    /**
     * <p>Добавление столбца в файл</p>
     * <p>Метод создает и записывает в файл новый столбец</p>
     * @param PathFileName путь к файлу
     * @param colum имя столбца
     * @throws Exception этот метод работает с файловой системой которая может вызвать ошибку
     */
    public void addColum(String PathFileName, String colum) throws Exception {
        colum = colum.trim();
        List<String[]> csvBody = new ArrayList<>();
        String[] values = null;
        int count = 0;
        List<String> title = new ArrayList<>();
        while ((values = csvReader.readNext()) != null) {
            if(count == 0){
                for(int i = 0; i < values.length; i++){
                    title.add(values[i]);
                }
                title.add(searchSameElementToColum(title,  colum));
                csvBody.add(title.toArray(new String[0]));
            }else {
                csvBody.add(values);
            }
            count++;
        }
        clearCsvReader();
        CSVWriter csvWriter = openCsvWriter(PathFileName);
        csvWriter.writeAll(csvBody);
        clearCsvWriter(csvWriter);
    }
    /**
     * <p>Поиск одинакового элемента в addColum</p>
     * <p>Если есть одинаковый элемент меняем ему название</p>
     * @param title текущий список
     * @param colum название колонки
     * @return уникальный элемент
     */
    String searchSameElementToColum(List<String> title, String colum){
        boolean flag;
        colum = colum.trim();
        String str = "";
            if (title.contains(colum)) {
                str =  colum  +"(dublicat)";
                flag = true;
            } else {
                str = colum;
                flag = false;
            }
            if(flag){
                str= searchSameElementToColum(title,  str);
            }

        return str;
    }
    /**
     * <p>Добавление строки в файл</p>
     * <p>Метод создает и записывает в файл новую колонку</p>
     * @param PathFileName путь к файлу
     * @throws Exception этот метод работает с файловой системой которая может вызвать ошибку
     */
    public void addRow(String PathFileName) throws Exception {
        List<String[]> csvBody = new ArrayList<>();
        String[] values = null;
        String[] nonValue = {""};
        int count = 0;
        while ((values = csvReader.readNext()) != null) {
                csvBody.add(values);
            count++;
        }
        csvBody.add(nonValue);
        clearCsvReader();
        CSVWriter csvWriter = openCsvWriter(PathFileName);
        csvWriter.writeAll(csvBody);
        clearCsvWriter(csvWriter);
    }
    /**
     * <p>Удаление строк</p>
     * <p>Метод удаляет ранее выбранные строки по id</p>
     * @param PathFileName путь к файлу
     * @param rows список удаляемых строк
     * @throws Exception этот метод работает с файловой системой которая может вызвать ошибку
     */
    public void deleteRowsCSV(String PathFileName, List<String> rows) throws Exception{
        List<String[]> csvBody = new ArrayList<>();
        String[] values = null;
        int count = 0;
        while ((values = csvReader.readNext()) != null) {
            if(!rows.contains( Integer.toString(count))) {
                csvBody.add(values);
            }
            count++;
        }

        clearCsvReader();
        CSVWriter csvWriter = openCsvWriter(PathFileName);
        csvWriter.writeAll(csvBody);
        clearCsvWriter(csvWriter);
    }
    /**
     * <p>Удаление столбцов</p>
     * <p>Метод удаляет ранее выбранные столбцы по их названию</p>
     * @param PathFileName путь к файлу
     * @param colums список удаляемых столбцов
     * @throws Exception этот метод работает с файловой системой которая может вызвать ошибку
     */
    public void deleteColumsCSV(String PathFileName, List<String> colums) throws Exception{
        List<List<String>> csvBody = new ArrayList<>();
        String[] values = null;
        while ((values = csvReader.readNext()) != null) {
            csvBody.add(Arrays.asList(values));
        }

        List<List<String>> csvBodyList = new ArrayList<>();
        for (List<String> elem : csvBody) {
            List<String> str = new ArrayList<>();
            for (int i = 0; i < elem.size(); i++) {
                if(!columsEmpty(colums, csvBody.get(0), i)) {
                    str.add(elem.get(i));
                }
            }
            csvBodyList.add(str);
        }
        List<String[]> newBody = new ArrayList<>();
        for (List<String> massList : csvBodyList) {
            String[] rowLine = new String[massList.size()];
            newBody.add(massList.toArray(rowLine));
        }

        clearCsvReader();
        CSVWriter csvWriter = openCsvWriter(PathFileName);
        csvWriter.writeAll(newBody);
        clearCsvWriter(csvWriter);
    }
    /**
     * <p>Вспомогательный метод для deleteColumsCSV</p>
     * <p>Проверяет если такой столбец в списке</p>
     * @param colums список удаляемых столбцов
     * @param title текущий список столбцов
     * @param index индекс по которому идет сравнение
     * @return true есть совпадение false нет
     */
    //helper function to method deleteRowsCSV
    boolean columsEmpty(List<String> colums, List<String> title, int index){
        boolean flag = false;
        for (String colum: colums) {
                if(title.contains(colum) && index == title.indexOf(colum))
                    flag = true;
        }
        return flag;
    }
    //закрить файл и очистить Buffer
    private void clearBuffer() throws IOException {
        this.br.close();
    }
    //закрить файл и очистить csvReader
    private void clearCsvReader() throws IOException {
        this.csvReader.close();
    }
    //закрить файл и очистить csvWriter
    private void clearCsvWriter(CSVWriter csvWriter) throws IOException {
        csvWriter.flush();
        csvWriter.close();
    }
    //создать запись
    private CSVWriter openCsvWriter(String PathFileName) throws FileNotFoundException {
        CSVWriter csvWriter = new CSVWriter(
                new OutputStreamWriter(new FileOutputStream(PathFileName), StandardCharsets.UTF_8),
                this.seperator,
                CSVWriter.DEFAULT_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END);
        return  csvWriter;
    }

    /**<p>Метод по созданию файла с нуля + начальные данные</p>
     * @param PathFileName полный путь к папке с файлами + сам файл с разрешением
     */
    public void createNewCSV(String PathFileName) {
        try (
                Writer writer = Files.newBufferedWriter(Paths.get(PathFileName));

                CSVWriter csvWriter = new CSVWriter(writer,
                        CSVWriter.DEFAULT_SEPARATOR,
                        CSVWriter.NO_QUOTE_CHARACTER,
                        CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                        CSVWriter.DEFAULT_LINE_END);
        ){
            String[] headerRecord = {"title 1", "title 2", "title 3", "title 4"};
            csvWriter.writeNext(headerRecord);

            csvWriter.writeNext(new String[]{"data 1 1", "data 1 2", "data 1 3", "data 1 4"});
            csvWriter.writeNext(new String[]{"data 2 1", "data 2 2", "data 2 3", "data 2 4"});
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
