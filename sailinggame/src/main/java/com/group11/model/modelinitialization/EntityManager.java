package com.group11.model.modelinitialization;

import java.util.ArrayList;
import java.util.List;

import com.group11.model.gameentites.AEntity;
import com.group11.model.gameentites.CommandableEntity;

/**
 * The EntityManager class is responsible managing the lifecycle of entities in the game. Removing them
 * when they are dead and updating the players score when an enemy is killed.
 */
public class EntityManager {

    private CommandableEntity player;
    private List<CommandableEntity> enemyList;
    private List<AEntity> entityList;
 
    /**
     * Constructs an EntityManager
     * @param world the world to spawn entities in
     * @param entityMatrix the entity matrix containg all entities
     */
    public EntityManager(List<CommandableEntity> enemyList, List<AEntity> entityList, CommandableEntity player) {
        ScoreBoard.clearScoreBoard();
        this.enemyList = enemyList;
        this.entityList = entityList;
        this.player = player;
    }

    /**
     * Removes entities that are dead (0 hp or lower). Increments the players score when an enemy is killed.
     * @param waveNumber the current wave number
     */
    protected void removeEntitiesWithZeroHp(int waveNumber) {
        List<AEntity> entitiesToRemove = new ArrayList<>();
        for (AEntity entity : entityList) {
            if (entity.getHitPoints() <= 0) {
                if (!entity.isFriendly()) {
                    ScoreBoard.incrementScore(player, waveNumber*10);
                }
                entitiesToRemove.add(entity);
            }
        }
        this.entityList.removeAll(entitiesToRemove);
        this.enemyList.removeAll(entitiesToRemove);
    }

    
}
