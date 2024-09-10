package com.dawan.gquizz;

import com.dawan.gquizz.entities.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Map;


@SpringBootApplication
public class GquizzApplication {

    public enum CATEGORY {tv_cinema, art_litterature, musique, sport, politique}

    public static void main(String[] args) {
        SpringApplication.run(GquizzApplication.class, args);
        test();
    }

    public static void test() {
        User Luc = new User();
        System.out.println(CATEGORY.tv_cinema.name());
        Map<String, Integer> scores = new HashMap<String, Integer>();
        scores.put("cc", 100);
        scores.put("cb", 50);
        System.out.println(Luc.scores);
        Luc.setScores(scores);
    }
}
