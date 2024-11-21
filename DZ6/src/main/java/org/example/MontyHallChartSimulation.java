/**
 * В качестве задачи предлагаю вам реализовать код для демонстрации парадокса Монти Холла (Парадокс Монти Холла — Википедия ) и наглядно убедиться в верности парадокса
 * (запустить игру в цикле на 1000 и вывести итоговый счет).
 * Необходимо:
 * Создать свой Java Maven или Gradle проект;
 * Подключить зависимость lombok.
 * Можно подумать о подключении какой-нибудь математической библиотеки для работы со статистикой
 * Самостоятельно реализовать прикладную задачу;
 * Сохранить результат в HashMap<шаг теста, результат>
 * Вывести на экран статистику по победам и поражениям
 */

package org.example;


import lombok.Data;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MontyHallChartSimulation {


    @Data
    static class Result {
        private int stayWins;
        private int switchWins;
        private int totalGames;

        public int getStayLosses() {
            return totalGames - stayWins;
        }

        public int getSwitchLosses() {
            return totalGames - switchWins;
        }
    }

    public enum StepResult{
        STAY,
        SWITCH
    };

    public static void main(String[] args) {

        int totalGames = 1000;
        Result result = simulateGames(totalGames);
        DecimalFormat decimalFormat = new DecimalFormat("#.###");
        System.out.println("Результаты симуляции:");
        System.out.println("Победы, оставаясь при своем выборе: " + result.getStayWins());
        System.out.println("Победы, сменив выбор: " + result.getSwitchWins());
        System.out.println("Процент побед при остающемся выборе: " + decimalFormat.format(((double) result.getStayWins() / totalGames * 100)) + "%");
        System.out.println("Процент побед при смене выбора: " + decimalFormat.format(((double) result.getSwitchWins() / totalGames * 100)) + "%");


        createWinPieChart(result);
    }

    public static Result simulateGames(int totalGames) {
        Map<Integer, Enum> results = new HashMap<>();
        Random random = new Random();
        int stayWins = 0;
        int switchWins = 0;

        for (int i = 0; i < totalGames; i++) {
            int prizeDoor = random.nextInt(3);
            int playerChoice = random.nextInt(3);


            int openDoor;
            do {
                openDoor = random.nextInt(3);
            } while (openDoor == prizeDoor || openDoor == playerChoice);


            int switchChoice = 3 - playerChoice - openDoor;


            if (playerChoice == prizeDoor) {
                results.put(i,StepResult.STAY);
                stayWins++;
            }
            if (switchChoice == prizeDoor) {
                results.put(i,StepResult.SWITCH);
                switchWins++;
            }

        }

        Result result = new Result();
        result.setStayWins(stayWins);
        result.setSwitchWins(switchWins);
        result.setTotalGames(totalGames);
        return result;
    }

    public static void createWinPieChart(Result result) {

        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Stay Wins", result.getStayWins());
        dataset.setValue("Stay Losses", result.getStayLosses());
        dataset.setValue("Switch Wins", result.getSwitchWins());
        dataset.setValue("Switch Losses", result.getSwitchLosses());

        // Создание диаграммы
        JFreeChart chart = ChartFactory.createPieChart("Monty Hall Simulation Results", dataset, true, true, false);


        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setSectionPaint("Stay Wins", new Color(76, 175, 80));
        plot.setSectionPaint("Stay Losses", new Color(244, 67, 54));
        plot.setSectionPaint("Switch Wins", new Color(33, 150, 243));
        plot.setSectionPaint("Switch Losses", new Color(255, 152, 0));


        JFrame frame = new JFrame("Monty Hall Results");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(new ChartPanel(chart), BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }
}