package pink.coursework.csvparser.models;

import com.opencsv.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;


public class CsvModel implements Serializable {

    List<String> TitleCsv;
    List<DataCsv> DataModelRows;

    public CsvModel() {
    }


    //запись данных + пеженация
    public void getListRowsData(String PathFileName, int page, int CSVFILEPAGE) throws Exception{
        CSVParser csvParser = new CSVParserBuilder().withSeparator(seperator(PathFileName)).build();
        CSVReader csvReader = new CSVReaderBuilder(new FileReader(PathFileName)).withCSVParser(csvParser).build();

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
        csvReader.close();
    }
    //количество записей
    public int countRows(String PathFileName) throws Exception {
        int count = 0;
        CSVReader csvReader = new CSVReader(new FileReader(PathFileName));
        while (csvReader.readNext() != null) {
            count++;
        }
        csvReader.close();
        return count;
    }
    //определяет сеператор
    public char seperator(String PathFileName) throws Exception {
        String line = "";
        char csvSplitBy = 0;
        BufferedReader br = new BufferedReader(new FileReader(PathFileName));
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
                csvSplitBy = 0;
            }
            break;
        }
        br.close();
        return csvSplitBy;
    }
    //заполнение пустых ячеек если они есть в CSV
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
    //helper to method saveCsv
    public boolean getDataCsv(Integer id){
        for (DataCsv elem : DataModelRows){
            if(elem.getId().equals(id))
                return true;
        }
        return false;
    }
    //helper to method saveCsv
    public String[] getDataCsvMass(Integer id){
        for (DataCsv elem : DataModelRows){
            if(elem.getId().equals(id))
                return elem.getDataRows().toArray(new String[0]);
        }
        return null;
    }
    //перезапись новых данных в файл
    public void saveCsv(String PathFileName) throws Exception {
        char seperator = seperator(PathFileName);
        CSVParser csvParser = new CSVParserBuilder().withSeparator(seperator).build();
        CSVReader reader = new CSVReaderBuilder(new FileReader(PathFileName)).withCSVParser(csvParser).build();
        List<String[]> csvBody = new ArrayList<>();
        String[] values = null;
        int count = 0;
        csvBody.add(getTitleCsv().toArray(new String[0]));
        while ((values = reader.readNext()) != null) {
            if(count != 0){
                if(getDataCsv(count)){
                        csvBody.add(getDataCsvMass(count));
                }else{
                    csvBody.add(values);
                }
            }
            count++;
        }

        reader.close();
        CSVWriter csvWriter = new CSVWriter(
                new OutputStreamWriter(new FileOutputStream(PathFileName), StandardCharsets.UTF_8),
                seperator,
                CSVWriter.DEFAULT_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END);

        csvWriter.writeAll(csvBody);
        csvWriter.flush();
        csvWriter.close();
    }
    //создание новых данных и запись их в обьект
    public void writeModifiedData(List<String> title, List<String> dataList, List<Integer> idList){
        setTitleCsv(title);
        List<DataCsv> dataCsvList = new ArrayList<>();
        int count = title.size();
        int endCount = dataList.size()/count;
        int start = 0;
        for(int i = 0; i<endCount; i++){
            List<String> stringList = new ArrayList<>();
            for(int j = start; j < start+count; j++){
                stringList.add(dataList.get(j));
            }
            start+=count;
            dataCsvList.add(new DataCsv(idList.get(i), stringList));
        }
        setDataModelRows(dataCsvList);
    }
    //добавление столбца в файл
    public void addRow(String PathFileName, String Row) throws Exception {
        char seperator = seperator(PathFileName);
        CSVParser csvParser = new CSVParserBuilder().withSeparator(seperator).build();
        CSVReader reader = new CSVReaderBuilder(new FileReader(PathFileName)).withCSVParser(csvParser).build();
        List<String[]> csvBody = new ArrayList<>();
        String[] values = null;
        int count = 0;
        List<String> title = new ArrayList<>();
        while ((values = reader.readNext()) != null) {
            if(count == 0){
                for(int i = 0; i < values.length; i++){
                    title.add(values[i]);
                }
                title.add(Row);
                csvBody.add(title.toArray(new String[0]));
            }else {
                csvBody.add(values);
            }
            count++;
        }
        reader.close();
        CSVWriter csvWriter = new CSVWriter(
                new OutputStreamWriter(new FileOutputStream(PathFileName), StandardCharsets.UTF_8),
                seperator,
                CSVWriter.DEFAULT_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END);

        csvWriter.writeAll(csvBody);
        csvWriter.flush();
        csvWriter.close();
    }

    //добавление колонки в файл
    public void addColum(String PathFileName) throws Exception {
        char seperator = seperator(PathFileName);
        CSVParser csvParser = new CSVParserBuilder().withSeparator(seperator).build();
        CSVReader reader = new CSVReaderBuilder(new FileReader(PathFileName)).withCSVParser(csvParser).build();
        List<String[]> csvBody = new ArrayList<>();
        String[] values = null;
        String[] nonValue = {""};
        int count = 0;
        List<String> title = new ArrayList<>();
        while ((values = reader.readNext()) != null) {
                csvBody.add(values);
            count++;
        }
        csvBody.add(nonValue);
        reader.close();
        CSVWriter csvWriter = new CSVWriter(
                new OutputStreamWriter(new FileOutputStream(PathFileName), StandardCharsets.UTF_8),
                seperator,
                CSVWriter.DEFAULT_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END);

        csvWriter.writeAll(csvBody);
        csvWriter.flush();
        csvWriter.close();
    }
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

}
