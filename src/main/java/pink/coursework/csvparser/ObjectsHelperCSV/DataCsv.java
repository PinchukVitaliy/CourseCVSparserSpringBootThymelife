package pink.coursework.csvparser.ObjectsHelperCSV;

import java.util.List;

/**
 * <p>Класс который хранит данные</p>
 */
public class DataCsv {
    Integer Id;
    List<String> DataRows;

    public DataCsv() {
    }

    public DataCsv(Integer id, List<String> dataRows) {
        Id = id;
        DataRows = dataRows;
    }

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
