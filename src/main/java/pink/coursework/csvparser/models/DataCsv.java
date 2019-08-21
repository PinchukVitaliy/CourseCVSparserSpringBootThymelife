package pink.coursework.csvparser.models;

import java.util.List;

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
