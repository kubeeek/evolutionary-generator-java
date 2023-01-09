package agh.gui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CSVHandler {

    String filename;
    private int epoque = 0;
    public CSVHandler(int simulationCounter){
        // Generate a unique filename for the current simulation.
        this.filename = "src/main/CSVFiles/simulation_" + simulationCounter + ".txt";
    }
    private void saveStatisticsToFile(String Animals,String Grass, String freeSpaces,
                                      String avgEnergy, String avgAge, String genotype) {
        try {
            FileWriter fileWriter = new FileWriter(this.filename, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // Write the desired statistics to the file in CSV format.
            // For example:
            bufferedWriter.write("Tura " + epoque + ",");
            bufferedWriter.write(Animals + ",");
            bufferedWriter.write(Grass + ",");
            bufferedWriter.write(freeSpaces + ",");
            bufferedWriter.write(avgEnergy + ",");
            bufferedWriter.write(avgAge + ",");
            bufferedWriter.write(genotype + "\n");

            // Close the file writer and buffered writer.
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }

        // Increment the epoque counter.
        epoque++;
    }
    private void deleteCSV(){
        String fileToDelete = this.filename;
        File file = new File(fileToDelete);
        file.delete();
    }
}
