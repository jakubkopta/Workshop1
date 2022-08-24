package pl.coderslab;

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
                    removeTask();
                    System.out.print(ConsoleColors.BLUE + "What's next? " + ConsoleColors.RESET);
                    break;
                case "list":
                    showList(tasks);
                    System.out.print(ConsoleColors.BLUE + "What's next? " + ConsoleColors.RESET);
                    break;
                case "exit":
                    save2File();
                    System.out.println(ConsoleColors.RED + "Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Wrong option! Try again: ");
            }
        }
    }

    public static String[][] file2Tab(String fileName){
        Path path = Paths.get(fileName);
        //check if file exists
        if (!Files.exists(path)){
            System.out.println("File doesn't exist!");
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
        System.out.println(ConsoleColors.BLUE);
        System.out.println("Hello! There are few options to choose." + ConsoleColors.RESET);
        for (String option : OPTIONS) {
            System.out.println("- " + option);
        }
        System.out.println(ConsoleColors.BLUE);
        System.out.print("Choose your option: " + ConsoleColors.RESET);
    }

    public static void addTask(){
        System.out.println("added");
    }

    public static void removeTask(){
        System.out.println("removed");
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

    public static void save2File(){
        System.out.println("saved");
    }
}
