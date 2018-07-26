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

    private static Map<String, List<Phones>> numbers = new HashMap<>();

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("configs/applicationContext.xml");

        boolean check = true;

        while (check) {

            System.out.println("Введите номер действия:");
            System.out.println("==== 1. Добавить пользователя");
            System.out.println("==== 2. Добавить номер пользователю");
            System.out.println("==== 3. Удалить пользователя");
            System.out.println("==== 4. Найти номера пользователя");

            switch (scanner.nextLine()) {
                case "1":
                    break;
                case "2":
                    break;
                case "3":
                    break;
                case "4":
                    getNumbersForUser();
                    break;
            }
            System.out.println("Продолжить? y/n");
            check = scanner.nextLine().equals("y");
        }
    }

    private static void getNumbersForUser(){

        UserRepository userRepository = new UserRepositoryImpl();

        System.out.println("ФИО: ");

        String fullName = scanner.nextLine();

        List<Phones> phones = userRepository.getPhonesByFullName(fullName);

        if (!phones.isEmpty()){
            numbers.put(fullName, phones);
            for (Phones p : phones) {
                System.out.println(p.getNum());
            }
        } else {
            System.out.println("Нет такого пользователя");
        }
    }
}