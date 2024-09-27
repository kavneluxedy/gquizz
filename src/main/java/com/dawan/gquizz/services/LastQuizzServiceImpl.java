package com.dawan.gquizz.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dawan.gquizz.entities.LastQuizz;
import com.dawan.gquizz.entities.User;
import com.dawan.gquizz.repositories.LastQuizzRepository;

@Service
public class LastQuizzServiceImpl implements ILastQuizzService {
	@Autowired
	private LastQuizzRepository lastQuizzRepository;
	
	@Override
    public LastQuizz update(Long lastQuizzId, LastQuizz lastQuizz) throws Exception {
        if (lastQuizzRepository.findById(lastQuizzId) != null) {
        	
        	return lastQuizzRepository.saveAndFlush(lastQuizz);
        	
        } else {
            
            return lastQuizzRepository.save(lastQuizz);
        }
        
    }
}
