package pink.coursework.csvparser.models;

import com.opencsv.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


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

    public void saveCsv(String PathFileName) throws Exception {
        char seperator =seperator(PathFileName);
        CSVReader reader = new CSVReader(new FileReader(PathFileName), seperator);
        List<String[]> csvBody = new ArrayList<>();
        String[] values = null;
        int count = 0;
        boolean flag = true;
        csvBody.add(getTitleCsv().toArray(new String[0]));
        while ((values = reader.readNext()) != null) {
            if(count != 0){
                if(flag){
                for(DataCsv elem : getDataModelRows()) {
                    if(elem.getId().equals(count)) {
                        csvBody.add(elem.getDataRows().toArray(new String[0]));
                        flag = true;
                    }
                }}
                if(!flag){
                    csvBody.add(values);
                    flag =false;
                }
            }
            count++;
        }


        //csvBody.get(2)[2] = "test";
        reader.close();
        CSVWriter csvWriter = new CSVWriter(new FileWriter(PathFileName), seperator);
        //List<String[]> csvBody = new ArrayList<>();
        //String[] str = {"1","2","3","4","5"};
        //csvBody.get(2)[2] = "test";
        //csvBody.add(str);
        csvWriter.writeAll(csvBody);
        csvWriter.flush();
        csvWriter.close();
    }
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
