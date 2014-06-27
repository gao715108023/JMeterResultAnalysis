package com.gcj.file;

import com.gcj.bean.AggregateReport;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class FileTools {

    public List<String> getAllFileDirectory(String filePath) {
        List<String> allFileDirectories = new ArrayList<String>();
        File file = new File(filePath);
        String[] directories = file.list();
        for (String directory : directories) {
            if (directory.contains(".csv")) {
                if (filePath.endsWith("\\") || filePath.endsWith("/")) {
                    allFileDirectories.add(filePath + directory);
                } else if (filePath.contains("/")) {
                    allFileDirectories.add(filePath + "/" + directory);
                } else {
                    allFileDirectories.add(filePath + "\\\\" + directory);
                }
            }
            //System.out.println(directory);
        }
        return allFileDirectories;
    }

    public List<AggregateReport> getContent(List<String> filePaths) {
        List<AggregateReport> aggregateReports = new ArrayList<AggregateReport>();
        File file;
        BufferedReader reader = null;
        String tempString;
        try {
            for (String filePath : filePaths) {
                file = new File(filePath);
                //System.out.println(file.getName());
                reader = new BufferedReader(new FileReader(file));
                int line = 0;
                while ((tempString = reader.readLine()) != null) {
                    //System.out.println(tempString);
                    line++;
                    if (tempString.startsWith("TOTAL")) {
                        //System.out.println(tempString);
                        String[] temps = tempString.split(",");
                        AggregateReport aggregateReport = new AggregateReport();
                        aggregateReport.setLabel(temps[0]);
                        aggregateReport.setSamples(Integer.parseInt(temps[1]));
                        aggregateReport.setAverage(Integer.parseInt(temps[2]));
                        aggregateReport.setMedian(Integer.parseInt(temps[3]));
                        aggregateReport.setNinetyLine(Integer.parseInt(temps[4]));
                        aggregateReport.setMin(Integer.parseInt(temps[5]));
                        aggregateReport.setMax(Integer.parseInt(temps[6]));
                        aggregateReport.setError(Float.parseFloat(temps[7]));
                        aggregateReport.setThroughput(Float.parseFloat(temps[8]) / (line - 2));

                        temps = file.getName().split("\\.");
                        if (isNumeric(temps[0])) {
                            aggregateReport.setThreads(Integer.parseInt(temps[0]));
                        } else {
                            String[] array = temps[0].split("-");
                            int threads = Integer.parseInt(array[0]) + Integer.parseInt(array[1]);
                            aggregateReport.setThreads(threads);
                        }
                        //System.out.println(temps[0]);
                        aggregateReports.add(aggregateReport);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Collections.sort(aggregateReports);
        return aggregateReports;
    }

    private boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    public void writeFile(String filePath, List<AggregateReport> aggregateReports) {
        BufferedWriter bufferedWriter = null;
        File file = new File(filePath);

        try {
            if (file.exists() == true) {
                bufferedWriter = new BufferedWriter(new FileWriter(file, true));
            } else {
                bufferedWriter = new BufferedWriter(new FileWriter(file));
            }
            String title = "并发数,平均响应时间(ms),error(%),QPS(/sec)";
            bufferedWriter.write(title + "\n");
            for (AggregateReport aggregateReport : aggregateReports) {
                //String result = aggregateReport.getThreads() + "," + aggregateReport.getLabel() + "," + aggregateReport.getSamples() + "," + aggregateReport.getAverage() + "," + aggregateReport.getMedian() + "," + aggregateReport.getNinetyLine() + "," + aggregateReport.getMin() + "," + aggregateReport.getMax() + "," + aggregateReport.getError() + "," + aggregateReport.getThroughput();
                String result = aggregateReport.getThreads() + "," + aggregateReport.getAverage() + "," + aggregateReport.getError() + "," + aggregateReport.getThroughput();
                bufferedWriter.write(result + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedWriter != null)
                    bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        FileTools fileTools = new FileTools();
        List<String> filePaths = fileTools.getAllFileDirectory("/Users/gaochuanjun/Documents/12.新闻app性能测试/01.性能测试/02.20140523");
        for (String filePath : filePaths) {
            System.out.println(filePath);
        }
        List<AggregateReport> aggregateReports = fileTools.getContent(filePaths);
        for (AggregateReport aggregateReport : aggregateReports) {
            System.out.println(aggregateReport.getThreads() + "," + aggregateReport.getLabel() + "," + aggregateReport.getSamples() + "," + aggregateReport.getAverage() + "," + aggregateReport.getMedian() + "," + aggregateReport.getNinetyLine() + "," + aggregateReport.getMin() + "," + aggregateReport.getMax() + "," + aggregateReport.getError() + "," + aggregateReport.getThroughput());
        }
        fileTools.writeFile("/Users/gaochuanjun/Documents/12.新闻app性能测试/01.性能测试/02.20140523/result.csv", aggregateReports);
    }
}
