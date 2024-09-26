package com.dawan.gquizz.services;

import com.dawan.gquizz.entities.Category;
import com.dawan.gquizz.entities.Score;
import com.dawan.gquizz.entities.User;
import com.dawan.gquizz.repositories.CategoryRepository;
import com.dawan.gquizz.repositories.ScoreRepository;
import com.dawan.gquizz.repositories.UserRepository;
import com.dawan.gquizz.utils.ScoreHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class ScoreService implements IScoreService {

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public String updateBestScore(Long userId, Long categoryId) {
        Category cat = categoryRepository.findById(categoryId).get();
        Optional<User> u = userRepository.findById(userId);
        User user = null;
        int currentScore = 0;
        if (u.isPresent()) {
            user = u.get();
            currentScore = user.getCurrentScore();
        }
        Score userCatScore = scoreRepository.findOneByUser_IdAndCategory_Id(userId, categoryId);
        System.out.println("=====================+++++++>" + userCatScore);
        Long count = categoryRepository.count();

        // S'il n'y a pas de doublons dans les catégories d'un utilisateur
        if (scoreRepository.countByUserId(userId) <= count) {
            System.out.println("COUNT: " + count);

            if (Boolean.TRUE.equals(scoreRepository.existsByUserIdAndCategoryId(userId, categoryId))) {
                // Possède déjà un score
                if (currentScore > userCatScore.getBestScore()) {
                    System.out.println("Vous avez battu votre propre record: " + userCatScore.getBestScore());
                    return "Vous avez battu votre propre record: " + userCatScore.getBestScore();
                }
            } else {
                Score newScore = ScoreHelper.createNewScore(user, cat);
                scoreRepository.saveAndFlush(newScore);
            }
        }
        return "Vous n'avez pas battu votre meilleur score";
    }
}