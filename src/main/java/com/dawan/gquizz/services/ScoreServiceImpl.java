package com.dawan.gquizz.services;

import com.dawan.gquizz.entities.Category;
import com.dawan.gquizz.entities.Score;
import com.dawan.gquizz.entities.User;
import com.dawan.gquizz.repositories.CategoryRepository;
import com.dawan.gquizz.repositories.ScoreRepository;
import com.dawan.gquizz.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScoreServiceImpl implements IScoreService {

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public String updateBestScore(User user) {
        Category cat = user.getLastQuizz().getCategory();
        int currentScore = user.getCurrentScore();

//        Optional<Score> optUserScoreByCat = scoreRepository.findOneByUser_IdAndCategory_Id(user.getId(), cat.getId());
//        Score userCatScore;

        // vérification de présence
//        if(optUserScoreByCat.isPresent()) {

        // objet a utiliser si présent
//            userCatScore = optUserScoreByCat.get();
//            if(currentScore > userCatScore.getBestScore())
//                userCatScore.setBestScore(currentScore);
//        } else {

        // objet a utiliser si absent
//            userCatScore = new Score(user,cat,currentScore);
//        }
//        scoreRepository.save(userCatScore);

        /*Optional<Score> optUserScoreByCat = scoreRepository.findOneByUser_IdAndCategory_Id(user.getId(), cat.getId());
        Score userCatScore = optUserScoreByCat.map(score -> {
            if (currentScore > score.getBestScore())
                score.setBestScore(currentScore);
            return score;
        }).orElseGet(() -> new Score(user, cat, currentScore));
        scoreRepository.save(userCatScore);*/

        scoreRepository.save(scoreRepository.findOneByUser_IdAndCategory_Id(user.getId(), cat.getId()).map(score -> {
            if (currentScore > score.getBestScore())
                score.setBestScore(currentScore);
            return score;
        }).orElseGet(() -> new Score(user, cat, currentScore)));

        /*System.out.println("=====================+++++++>" + userCatScore.getBestScore());
        Long count = categoryRepository.count();

        // S'il n'y a pas de doublons dans les catégories d'un utilisateur
        if (scoreRepository.countByUserId(userId) <= count) {
            System.out.println("COUNT: " + count);

            if (Boolean.TRUE.equals(scoreRepository.existsByUserIdAndCategoryId(userId, categoryId))) {
                // Possède déjà un score
                if (currentScore > userCatScore.getBestScore()) {
                    System.out.println("Vous avez battu votre propre record: " + userCatScore.getBestScore());
                    //TODO Update Score
                    return "Vous avez battu votre propre record: " + userCatScore.getBestScore();
                }
            } else {
                Score newScore = ScoreHelper.createNewScore(user, cat);
                scoreRepository.saveAndFlush(newScore);
            }
        }*/
        return "Vous n'avez pas battu votre meilleur score";
    }
}