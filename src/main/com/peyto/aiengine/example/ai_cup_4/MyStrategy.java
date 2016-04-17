package com.peyto.aiengine.example.ai_cup_4;

import com.peyto.aiengine.core.AIEngine;
import com.peyto.aiengine.example.ai_cup_4.model.Car;
import com.peyto.aiengine.example.ai_cup_4.model.Game;
import com.peyto.aiengine.example.ai_cup_4.model.Move;
import com.peyto.aiengine.example.ai_cup_4.model.World;

public class MyStrategy implements Strategy{

    @Override
    public void move(Car self, World world, Game game, Move move) {
        AIEngine engine = AIEngine.getInstance();

        /* Idea:
         * 1. Update Local State
         *  - update actual state
         *  - count & update existing risks
         *  - find & count new risks
         * 2. Work with Patterns and Templates
         *  not sure for now what it should contain
         *  - update Patterns for e-learning
         *  - find new Patterns for e-learning
         * 3. Update Local Tactics in line with global strategy
         *  global strategy influence on local tactics by estimate function (some weights)
         *  - generic algorithms
         *    a. selection
         *    b. mutation
         *    c. selection
         *  - aggressive protocol
         * 4. Count Actions
         * 5. Count Global GoStrategy
         */
    }

}
