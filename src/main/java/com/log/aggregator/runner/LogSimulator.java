package com.log.aggregator.runner;

import com.log.aggregator.utils.LogProperty;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by yashkhandelwal
 */
public class LogSimulator {

    public static void main(String[] args) throws InterruptedException {
        for (int i = 1; i < 10000; i++) {
            try (FileWriter fw = new FileWriter(LogProperty.getLogProperties().getString("producer1.log.path"), true);
                 BufferedWriter bw = new BufferedWriter(fw);
                 PrintWriter out = new PrintWriter(bw)) {
                out.println("This is logFile1 " + i);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Thread.sleep(200);
            try (FileWriter fw = new FileWriter(LogProperty.getLogProperties().getString("producer2.log.path"), true);
                 BufferedWriter bw = new BufferedWriter(fw);
                 PrintWriter out = new PrintWriter(bw)) {
                out.println("This is logFile2 " + i);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Thread.sleep(200);
            try (FileWriter fw = new FileWriter(LogProperty.getLogProperties().getString("producer3.log.path"), true);
                 BufferedWriter bw = new BufferedWriter(fw);
                 PrintWriter out = new PrintWriter(bw)) {
                out.println("This is logFile3 " + i);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Thread.sleep(200);
            try (FileWriter fw = new FileWriter(LogProperty.getLogProperties().getString("producer4.log.path"), true);
                 BufferedWriter bw = new BufferedWriter(fw);
                 PrintWriter out = new PrintWriter(bw)) {
                out.println("This is logFile4 " + i);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Thread.sleep(200);
            try (FileWriter fw = new FileWriter(LogProperty.getLogProperties().getString("producer5.log.path"), true);
                 BufferedWriter bw = new BufferedWriter(fw);
                 PrintWriter out = new PrintWriter(bw)) {
                out.println("This is logFile5 " + i);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Thread.sleep(200);
        }
        System.out.println("Logging Done");
    }
}
