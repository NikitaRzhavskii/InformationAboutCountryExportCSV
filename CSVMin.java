
/**
 * Write a description of CSVMin here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import java.io.*;
import org.apache.commons.csv.*;

public class CSVMin {
  public CSVRecord coldestHourInFile(CSVParser parser) {
        CSVRecord coldestSoFar = null;
        for (CSVRecord current: parser) {
          coldestSoFar = getColdestofTwo(current, coldestSoFar);
        }
        return coldestSoFar;
    }
  public void testColdestHourInFile() {
        FileResource fr = new FileResource();
        CSVRecord coldest = coldestHourInFile(fr.getCSVParser());
        System.out.println("Coldest temperature was " + coldest.get("TemperatureF")
        + "at " + coldest.get("TimeEDT"));
  }
  public String fileWithColdestTemperature() {
        DirectoryResource dr = new DirectoryResource();
        CSVRecord coldestSoFar = null;
        File coldestFile = null;
        for (File f:dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            CSVRecord current = coldestHourInFile(fr.getCSVParser());
            coldestSoFar = getColdestofTwo(current, coldestSoFar);
            coldestFile = f; 
        } 
            System.out.println(String.format("Coldest day was in file " 
            + coldestFile.getName()));
            System.out.println(String.format("Coldest temperature on that day was " 
            + coldestSoFar.get("TemperatureF")));
            System.out.println("All the Temperature on the coldest day were:");
            FileResource fil = new FileResource(coldestFile);
            CSVParser rt = fil.getCSVParser();
            for(CSVRecord currentRow: rt){ 
                System.out.println(String.format("%s %s: %s", currentRow.get("DateUTC"), currentRow.get("TimeEST"), currentRow.get("TemperatureF")));        
            }
        
        return coldestFile.getName();
  }
  public CSVRecord getColdestofTwo(CSVRecord currentRow, CSVRecord coldestSo){
            if (coldestSo == null) {
            coldestSo = currentRow;
            }else{
            double currentTem = Double.parseDouble(currentRow.get("TemperatureF"));
            double coldestTem = Double.parseDouble(coldestSo.get("TemperatureF"));
            if (currentTem < coldestTem && currentTem != -9999) {
                coldestSo = currentRow;
            }
        }
        return coldestSo;
    }
 public void testFileWithColdestTemperature() { 
   fileWithColdestTemperature();
}
  public CSVRecord getHumidityofTwo(CSVRecord currentRow, CSVRecord humidity){
            double currentTem = 0.0;
            double humidityTem = 0.0; 
       if (humidity == null) {
        humidity = currentRow;
    }else{
        if(currentRow.get("Humidity").equals("N/A")){
                currentTem = -9999; 
            }else{
            currentTem = Double.parseDouble(currentRow.get("Humidity"));
        }
        if(humidity.get("Humidity").equals("N/A")){ 
            humidityTem = - 9999;
        }else{
        humidityTem = Double.parseDouble(humidity.get("Humidity"));
        }
        if (currentTem < humidityTem && currentTem != -9999) {
                humidity = currentRow;
        }
        
    }
    return humidity;
}
  public CSVRecord getMaxHumidityofTwo(CSVRecord currentRow, CSVRecord humidity){
            double currentTem = 0.0;
            double humidityTem = 0.0; 
       if (humidity == null) {
        humidity = currentRow;
    }else{
        if(currentRow.get("Humidity").equals("N/A")){
                currentTem = -9999; 
            }else{
            currentTem = Double.parseDouble(currentRow.get("Humidity"));
        }
        if(humidity.get("Humidity").equals("N/A")){ 
            humidityTem = - 9999;
        }else{
        humidityTem = Double.parseDouble(humidity.get("Humidity"));
        }
        if (currentTem > humidityTem && currentTem != -9999) {
                humidity = currentRow;
        }
        
    }
    return humidity;
}
public CSVRecord lowestHumidityInFile(CSVParser parser){
    CSVRecord humidity = null; 
    for(CSVRecord current: parser){
        humidity = getHumidityofTwo(current, humidity);
    }
    return humidity; 
  }
 public void testLowestHumidityInFile(){
     FileResource fr = new FileResource(); 
     CSVRecord humidity = lowestHumidityInFile(fr.getCSVParser());
     System.out.println("Lowest Humidity was " 
     + humidity.get("Humidity") + " at " + humidity.get("DateUTC"));
}
public String lowestHumidityInManyFiles(){
    DirectoryResource dr = new DirectoryResource(); 
    CSVRecord humidity = null; 
    String fileName = null; 
    for(File f: dr.selectedFiles()){
        FileResource fr = new FileResource(f); 
        CSVRecord current = lowestHumidityInFile(fr.getCSVParser()); 
        humidity = getHumidityofTwo(current, humidity); 
}
System.out.println(String.format("Lowest Humidity was " + humidity.get("Humidity") + " at " + humidity.get("DateUTC")));;
return fileName; 
}
    public void testLowestHumidityInManyFile() {
    lowestHumidityInManyFiles(); 
    }
    public double averageTemperatureInFile(CSVParser parser){ 
        double sum = 0;
        double number = 0; 
        for(CSVRecord current: parser){
        double currentTemp  = Double.parseDouble(current.get("TemperatureF"));
        sum = sum + currentTemp; 
        number += 1; 
        }
        sum = sum/number; 
        return sum; 
    }
    public void testAverageTemperatureInFile(){ 
        FileResource fr = new FileResource(); 
        double averageTemper = averageTemperatureInFile(fr.getCSVParser()); 
        System.out.println("Average temperature in file is "+ averageTemper);
}
public double averageTemperatureWithHighHumidityInFile(CSVParser parser, int value){
    double sum = 0;
    double number = 0; 
    CSVRecord maxHumidity = null; 
    double max = 0; 
    for(CSVRecord current: parser){ 
        double currentTemp = Double.parseDouble(current.get("TemperatureF"));
        double currentHum = Double.parseDouble(current.get("Humidity")); 
        maxHumidity = getMaxHumidityofTwo(current, maxHumidity); 
        max = Double.parseDouble(maxHumidity.get("Humidity"));
        sum = sum + currentTemp; 
        number += 1;
    }
    sum = sum/number; 
    if( max >= value){
    return sum; 
}else{
    return sum = 0;
}
}
public void testAverageTemperatureWithHighHumidityInFile(){
    FileResource fr = new FileResource(); 
    double average = averageTemperatureWithHighHumidityInFile(fr.getCSVParser(), 80); 
    if(average == 0){ 
        System.out.println("No temperatures with that humidity"); 
    }else{
        System.out.println("Average temperature with high Humidity is " + average); 
     }
}
}

