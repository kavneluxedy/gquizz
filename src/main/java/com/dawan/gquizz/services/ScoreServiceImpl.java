package com.dawan.gquizz.services;

import com.dawan.gquizz.dtos.ScoreDTO;
import com.dawan.gquizz.entities.Category;
import com.dawan.gquizz.entities.Score;
import com.dawan.gquizz.entities.User;
import com.dawan.gquizz.repositories.ScoreRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreServiceImpl implements IScoreService {

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private NewQuizzService newQuizzService;

    @Override
    public Score updateBestScore(User user) {
        Category cat = user.getLastQuizz().getCategory();
        return scoreRepository.save(scoreRepository.findOneByUser_IdAndCategory_Id(user.getId(), cat.getId()).map(score -> {
            if (user.getLastQuizz().getCurrentScore() == 0) return null;
            if (user.getLastQuizz().getCurrentScore() > score.getBestScore()) {
                return score.setBestScore(user.getLastQuizz().getCurrentScore());
            }
            newQuizzService.resetCurrentScore(user);
            return score;
        }).orElseGet(() -> new Score(user, cat, user.getLastQuizz().getCurrentScore())));
    }

    @Override
    public List<ScoreDTO> getTopScoreByCategory(Category category) {
        return scoreRepository.findByCategoryOrderByBestScore(category).map(s -> {
            ModelMapper mapper = new ModelMapper();
            List<ScoreDTO> scores = s.stream().map(scoreStream -> mapper.map(scoreStream, ScoreDTO.class)).toList();
            System.out.println(scores);
            return scores;
        }).orElseGet(() -> {
            System.out.println("ERRORRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR");
            return null;
        });
    }
}