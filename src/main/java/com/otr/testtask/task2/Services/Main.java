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

        UserRepository userRepository = new UserRepositoryImpl();

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