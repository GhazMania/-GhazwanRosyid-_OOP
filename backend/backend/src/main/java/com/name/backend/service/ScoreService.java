// ScoreService.java
package com.name.backend.service;

import com.name.backend.model.Score;
import com.name.backend.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

// TODO: Add an annotation that will make this class known as service layer by Spring
@Service
public class ScoreService {
    // TODO: Add an annotation to do Dependency Injection from the existing instance (ScoreRepository)
    @Autowired
    // TODO: Add a private field for ScoreRepository
    private ScoreRepository scoreRepository;

    // inside ScoreService class

    // TODO: create a public method called createScore that will receive Score as the parameter and return the newly made score
    // hint: use scoreRepository to store the new score to the database
    public Score createScore(Score score){
        return scoreRepository.save(score);
    }

    // inside ScoreService class

    // TODO: create a public method called getScoreByID that can receive a UUID parameter and return the score based on the scoreId given in the parameter
    // hint: use Optional<Score> to handle the possibility that the score may or may not be found in the database
    // hint: use scoreRepository to search score based on scoreId
    public Optional<Score> getScoreByID(UUID scoreId){
        return scoreRepository.findById(scoreId);
    }

    public List<Score> getAllScores(){
        // TODO: Use scoreRepository to find all scores in the database, then return the result
        // hint: Call the same method as the code you wrote in TP number 4
        return scoreRepository.findAll();
    }

    public List<Score> getRecentScores(){
        // TODO: Use scoreRepository to find all scores in the database ordered by newest creation, then return the result
        return scoreRepository.findAllByOrderByCreatedAtDesc();
    }

    public List<Score> getScoreAboveValue(Integer minValue){
        // TODO: Use scoreRepository to find all scores in the database whose points are above a certain value
        // use minValue as the lower bound of the point value
        return scoreRepository.findByPointGreaterThan(minValue);
    }

    public List<Score> getLeaderboard(Integer limit) {
        // TODO: Use scoreRepository to find the Top Scores and provide the appropriate parameter
        return scoreRepository.findTopScores(limit);
    }

    public void deleteScore(UUID scoreId) {
        // TODO:
        // 1. Find the score you want to delete using scoreRepository, then store that score (hint: see how it's done in getScoreById())
        // 2. Check whether the score was found or not with `.orElseThrow(()-> new RuntimeException("Score with ID " + scoreId + " was not found"));`
        // 3. Call delete() from scoreRepository to delete the score stored earlier
        Optional<Score> score = scoreRepository.findById(scoreId);
        score.orElseThrow(()-> new RuntimeException("Score with ID " + scoreId + " was not found"));
        deleteScore(scoreId);
    }

}
