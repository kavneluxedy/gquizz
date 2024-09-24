package com.dawan.gquizz.services;

import com.dawan.gquizz.entities.Score;
import com.dawan.gquizz.entities.User;
import com.dawan.gquizz.repositories.ScoreRepository;
import com.dawan.gquizz.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

@Service
public class ScoreService implements IScoreService {

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Score> findByUserEmail(String email) {
        Optional<User> u = userRepository.findById(email).stream().findFirst();
        if (u.isPresent()) {
            return scoreRepository.findByUserEmail(email);
        } else throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
    }

    public String updateBestScore(String email, String category, int currentScore) {
        // set 1/3 field
        Score score = new Score().setCategory(category);

        Optional<Score> scoreStream = null;
        Optional<User> u = userRepository.findById(email);
        List<Score> scores = findByUserEmail(email);

        List<Score> scoreList = scores.stream().filter(s -> Objects.equals(s.getCategory(), category)).toList();
        System.out.println(scoreList);

        if (!scoreList.isEmpty()) {
            System.out.println("SCORE DANS CETTE CATEGORIE" + scoreList.stream().findFirst());
        } else {
            score.setBestScore(0);
            System.out.println("PAS ENCORE DE SCORE DANS CETTE CATEGORIE");
        }

        if (u.isPresent()) {
            User user = u.get();

            if (currentScore > score.getBestScore()) {
                scoreRepository.saveAndFlush(score.setUser(user).setBestScore(currentScore));
                System.out.println("Vous avez battu votre propre record: " + score.getBestScore());
                return "Vous avez battu votre propre record: " + score.getBestScore();
            }
        }
        System.out.println("Vous n'avez pas battu votre propre record: " + score.getBestScore());
        return "Vous n'avez pas battu votre propre record: " + score.getBestScore();
    }
}
