package utils;

import algorithm.*;
import exception.AlgorithmException;
import model.BCP;
import model.Process;
import model.queue.Queue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FunctionUtils {
    private static final String CSV_DIVISOR = ",";
    private static final String CSV_DATA_DIRECTORY = "data";

    public static String getPathProject() {
        Path currentRelativePath = Paths.get("");
        return currentRelativePath.toAbsolutePath().toString();
    }

    public static void parseCSV(String filename, Queue readyQueue) {
        String path = getPathProject() + File.separator + CSV_DATA_DIRECTORY + File.separator;
        path += filename;

        BufferedReader br = null;
        FileReader fileReader = null;
        String[] values;

        try {
            fileReader = new FileReader(path);
            br = new BufferedReader(fileReader);
            String line;

            while ((line = br.readLine()) != null) {
                values = line.split(CSV_DIVISOR);

                createProcess(values, readyQueue);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void createProcess(String[] values, Queue readyQueue) {
        int arrivalTime = Integer.parseInt(values[0]);
        int id = Integer.parseInt(values[1]);
        int burstTime = Integer.parseInt(values[2]);
        int priority = Integer.parseInt(values[3]);

        Process process = new Process(id, priority, arrivalTime, burstTime);

        BCP bcp = new BCP(process);
        readyQueue.add(bcp);
    }

    public static Algorithm defineAlgorithm(String arg) {
        switch (arg.toUpperCase()) {
            case "RR":
                return new RoundRobin();
            case "FCFS":
                return new FCFS();
            case "SJF":
                return new SJF();
            case "SJFP":
                return new SJFP();
            case "PRIORITY":
                return new Priority();
            case "PRIORITYP":
                return new PriorityP();
            default:
                throw new AlgorithmException();
        }
    }

    public static void verifyArgs(String[] args) {
        String messageException = "Arguments do not follow the standard. Check the correct way in --help";

        if ((args.length == 1) && !(args[0].toLowerCase().equals("--help")))
            throw new IllegalArgumentException(messageException);

        if (args.length == 2)
            throw new IllegalArgumentException(messageException);

    }


}
