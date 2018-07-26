package com.otr.testtask.task2.Services;

import com.otr.testtask.task2.Model.Phones;
import com.otr.testtask.task2.Repository.UserRepository;
import com.otr.testtask.task2.Repository.UserRepositoryImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class Main {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("configs/applicationContext.xml");

        while (true) {

            System.out.println("Введите номер действия:");
            System.out.println("==== 1. Добавить пользователя");
            System.out.println("==== 2. Добавить номер пользователю");
            System.out.println("==== 3. Удалить пользователя");
            System.out.println("==== 4. Найти номера пользователя");

            switch (scanner.nextLine()) {
                case "1":
                    addUser();
                    break;
                case "2":
                    addNumberForUser();
                    break;
                case "3":
                    deleteUser();
                    break;
                case "4":
                    printNumbersForUser();
                    break;
            }
            System.out.println("Продолжить? y/n");
            if (!scanner.nextLine().equals("y")) break;
        }
    }

    private static void addUser() {

        System.out.print("Введите ФИО: ");
        String name = scanner.nextLine();
        System.out.print("\nВведите номер: ");
        String number = scanner.nextLine();
        System.out.println();

        UserRepositoryImpl userRepository = new UserRepositoryImpl();

        userRepository.addUser(name, number);

        System.out.println("Пользователь добавлен.");
        while (true) {
            System.out.println("Добавить номер? y/n");
            if (scanner.nextLine().equals("y")) {
                addNumber(name);
            } else break;
        }
    }

    private static void deleteUser() {

        UserRepositoryImpl userRepository = new UserRepositoryImpl();

        System.out.println("ФИО для удаления: ");

        userRepository.deleteUserForName(scanner.nextLine());

        System.out.println("Пользователь удалён.");
    }

    private static void addNumberForUser() {

        System.out.print("Добавить номер для: ");
        String name = scanner.nextLine();
        System.out.println();

        addNumber(name);
    }

    private static void addNumber(String name) {
        UserRepositoryImpl userRepository = new UserRepositoryImpl();

        System.out.print("Введите номер: ");
        String number = scanner.nextLine();
        System.out.println();

        boolean check = userRepository.setNumberForUser(name, number);

        if (check) {
            System.out.println("Номер добавлен. Вывести все номера пользователя? y/n");
            if (scanner.nextLine().equals("y"))
                resultPrint(name);
        } else {
            System.out.println("Ошибка добавления!");
        }

    }

    private static void printNumbersForUser() {

        System.out.println("ФИО: ");

        String fullName = scanner.nextLine();

        resultPrint(fullName);
    }

    private static void resultPrint(String fullName) {

        Map<String, List<Phones>> numbers = new HashMap<>();

        UserRepository userRepository = new UserRepositoryImpl();

        List<Phones> phones = userRepository.getPhonesByFullName(fullName);

        if (phones.isEmpty()) {
            System.out.println("Нет такого пользователя");
        } else {

            numbers.put(fullName, phones);

            String key = (String) numbers.keySet().toArray()[0];

            System.out.println(key);

            for (Phones phone : numbers.get(key)) {
                System.out.println(phone.getNum());
            }
        }

    }
}