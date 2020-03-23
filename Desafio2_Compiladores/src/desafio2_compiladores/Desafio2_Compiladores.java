/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desafio2_compiladores;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

/**
 *
 * @author FabioRocha RA082170007
 */
public class Desafio2_Compiladores {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            List<String> newContent = new ArrayList<>();

            String path = args[0];
            List<String> content = loadTxt(path);
            if (content == null) {
                System.out.println("Arquivo vazio.");
                return;
            }

            for (String oldContentLine : content) {
                String line = oldContentLine.replaceAll("^\\{}\\[]\\(\\)", "");
                if (line.trim().isEmpty()) {
                    continue;
                } else {
                }

                char[] charArray = line.toCharArray();
                checkContent(newContent, oldContentLine, charArray);
            }

            writeResult(newContent, path);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<String> loadTxt(String path) throws IOException {
        try {
            List<String> content = Files.readAllLines(Paths.get(path));
            if (content.isEmpty()) {
                System.out.println("O arquivo selecionado está vazio.");
                return null;
            }
            return content;
        } catch (NoSuchFileException nsfe) {
            System.out.println("Arquivo inexistente.");
            return null;
        }
    }

    private static void checkContent(List<String> newContent, String oldContent, char[] charArray) {
        String content;
        Boolean isValid = checkSymbols(charArray);
        if (isValid) {
            content = oldContent + " - OK";
            newContent.add(content);
        } else {
            content = oldContent + " - Inválido";
            newContent.add(content);
        }
    }

    static boolean checkSymbols(char charArray[]) {
        Stack stack = new Stack();

        for (int i = 0; i < charArray.length; i++) {

            if (charArray[i] == '{' || charArray[i] == '(' || charArray[i] == '[') {
                stack.push(charArray[i]);
            }

            if (charArray[i] == '}' || charArray[i] == ')' || charArray[i] == ']') {
                if (stack.isEmpty()) {
                    return false;
                } else if (!isMatch((Character) stack.pop(), charArray[i])) {
                    return false;
                }
            }
        }

        if (stack.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    static boolean isMatch(char char1, char char2) {
        if (char1 == '{' && char2 == '}') {
            return true;
        } else if (char1 == '(' && char2 == ')') {
            return true;
        } else if (char1 == '[' && char2 == ']') {
            return true;
        } else {
            return false;
        }
    }

    private static void writeResult(List<String> newContent, String path) throws IOException {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(path.replace(".txt", "-check.txt")));
            for (String line : newContent) {
                pw.println(line);
            }
            pw.close();
        } catch (IOException e) {
            System.out.println("Falha ao escrever o arquivo.");
            System.out.println(e.getMessage());
        }
    }
}
