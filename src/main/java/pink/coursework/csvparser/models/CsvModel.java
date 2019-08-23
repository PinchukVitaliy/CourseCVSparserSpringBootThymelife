package pink.coursework.csvparser.models;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvModel {
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

    public void saveCsv(List<String> title, List<String> dataList, String s) {

    }
    public void writeDates(List<String> title, List<String> dataList){
        setTitleCsv(title);
        List<DataCsv> dataCsvList = new ArrayList<>();
        int count = title.size();
        int endCount = dataList.size()/count;
        int start = 0;
        int end = start + count;
        for(int i = 0; i<endCount; i++){
            List<String> stringList = new ArrayList<>();
            for(int j = start; j < end; j++){
                stringList.add(dataList.get(j));
            }
            start+=count;
            dataCsvList.add(new DataCsv(i, stringList));
        }
        setDataModelRows(dataCsvList);
        System.out.println(getTitleCsv());
        for (DataCsv el: getDataModelRows()) {
            System.out.println(el.getDataRows());
        }

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
