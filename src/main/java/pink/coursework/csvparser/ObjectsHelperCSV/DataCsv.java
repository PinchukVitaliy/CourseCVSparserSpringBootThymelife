package pink.coursework.csvparser.ObjectsHelperCSV;

import java.util.List;

/**
 * <p>Класс который хранит данные</p>
 */
public class DataCsv {
    //идентификатор
    Integer Id;
    //список с данными файла
    List<String> DataRows;
    //конструктор
    public DataCsv() {
    }

    /**конструктор с параметрами
     * @param id идентификатор поля id
     * @param dataRows список строк поля DataRows
     */
    public DataCsv(Integer id, List<String> dataRows) {
        Id = id;
        DataRows = dataRows;
    }
    //геттеры и сеттеры
    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public List<String> getDataRows() {
        return DataRows;
    }

    public void setDataRows(List<String> dataRows) {
        DataRows = dataRows;
    }
}
