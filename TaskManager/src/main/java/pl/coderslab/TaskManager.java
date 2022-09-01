package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;
import pl.coderslab.ConsoleColors;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;

public class TaskManager {
    static final String FILE_NAME = "tasks.csv";
    static final String[] OPTIONS = {"add", "remove", "list", "exit"};
    static String[][] tasks;

    public static void main(String[] args) {
        tasks = file2Tab(FILE_NAME);
        startingProcedure();
        Scanner scan = new Scanner(System.in);
        while (scan.hasNextLine()){
            String chosenOption = scan.nextLine();
            switch (chosenOption) {
                case "add":
                    addTask();
                    System.out.print(ConsoleColors.BLUE + "What's next? " + ConsoleColors.RESET);
                    break;
                case "remove":
                    try {
                        removeTask();
                    } catch (IndexOutOfBoundsException ex) {
                        System.out.println(ConsoleColors.RED + "Task doesn't exist! Try again! (" + ex.getMessage() + ")" + ConsoleColors.RESET);
                    }
                    System.out.print(ConsoleColors.BLUE + "What's next? " + ConsoleColors.RESET);
                    break;
                case "list":
                    showList(tasks);
                    System.out.print(ConsoleColors.BLUE + "What's next? " + ConsoleColors.RESET);
                    break;
                case "exit":
                    save2File(FILE_NAME, tasks);
                    System.out.println(ConsoleColors.PURPLE + "Everything saved!");
                    System.out.println("Goodbye!");
                    System.exit(0);
                default:
                    System.out.println(ConsoleColors.RED + "Wrong option! Try again: " + ConsoleColors.RESET);
            }
        }
    }

    public static String[][] file2Tab(String fileName){
        Path path = Paths.get(fileName);
        //check if file exists
        if (!Files.exists(path)){
            System.out.println(ConsoleColors.RED + "File doesn't exist!" + ConsoleColors.RESET);
            System.exit(0);
        }
        //load file to tab
        String[][] tab = null;
        try {
            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            tab = new String[lines.size()][lines.get(0).split(",").length];

            for (int i = 0; i < lines.size(); i++){
                String[] words = lines.get(i).split(",");
                for (int j = 0; j < words.length; j++) {
                    tab[i][j] = words[j].trim();
                }
            }
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
        return tab;
    }

    public static void startingProcedure(){
        System.out.println(ConsoleColors.BLUE_BOLD);
        System.out.println("Hello! There are few options to choose." + ConsoleColors.RESET);
        for (String option : OPTIONS) {
            System.out.println("- " + option);
        }
        System.out.println(ConsoleColors.BLUE_BOLD);
        System.out.print("Choose your option: " + ConsoleColors.RESET);
    }

    public static void addTask(){
        Scanner scan = new Scanner(System.in);
        System.out.println(ConsoleColors.PURPLE + "Please add task description: ");
        String description = scan.nextLine();
        System.out.println("Please add task due date: ");
        String date = scan.nextLine();
        System.out.println("Is your task important: true/false: " + ConsoleColors.RESET);
        String important = scan.nextLine();
        tasks = Arrays.copyOf(tasks, tasks.length + 1);
        tasks[tasks.length-1] = new String[3];
        tasks[tasks.length-1][0] = description;
        tasks[tasks.length-1][1] = date;
        tasks[tasks.length-1][2] = important;
    }

    public static void removeTask(){
        Scanner scan = new Scanner(System.in);
        System.out.print(ConsoleColors.PURPLE + "Please select number to remove: ");
        String num = scan.nextLine();
        while (!isNumberGreaterEqualZero(num)){
            System.out.print(ConsoleColors.RED + "Incorrect argument passed! Please select number: ");
            num = scan.nextLine();
        }
        int number = Integer.parseInt(num);
        tasks = ArrayUtils.remove(tasks, number);
    }

    public static boolean isNumberGreaterEqualZero(String input) {
        if (NumberUtils.isParsable(input)) {
            return Integer.parseInt(input) >= 0;
        }
        return false;
    }

    public static void showList(String[][] tab){
        for (int i = 0; i < tab.length; i++) {
            System.out.print(i + ": ");
            for (int j = 0; j < tab[i].length; j++) {
                System.out.print("|" + tab[i][j] + "|  ");
            }
            System.out.println();
        }
    }

    public static void save2File(String fileName, String[][] tab){
        Path path = Paths.get(fileName);
        String[] lines = new String[tab.length];
        for (int i = 0; i < tab.length; i++) {
            lines[i] = String.join(",", tab[i]);
        }

        try {
            Files.write(path, Arrays.asList(lines));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
