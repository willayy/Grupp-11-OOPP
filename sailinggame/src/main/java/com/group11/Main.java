package com.group11;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.group11.controller.GlobalKeyListener;
import com.group11.controller.KeyboardInterpretor;
import com.group11.model.builders.EntityDirector;
import com.group11.model.builders.ShipBuilder;
import com.group11.model.gameentites.AEntity;
import com.group11.model.gameentites.AMovableBody;
import com.group11.model.gameentites.CommandableEntity;
import com.group11.model.gameentites.Ship;
import com.group11.model.gameworld.AdvancedMapGenerator;
import com.group11.model.gameworld.BasicWorldGenerator;
import com.group11.model.gameworld.IMapGenerator;
import com.group11.model.gameworld.IWorldGenerator;
import com.group11.model.gameworld.World;
import com.group11.model.utility.*;
import com.group11.view.uicomponents.AppWindow;

class Main {

    private AppWindow appWindow;
    private static final GlobalKeyListener keyboardController = new GlobalKeyListener();
    private static final KeyboardInterpretor keyboardInterpreter = new KeyboardInterpretor();
    private AICommander aiCommander;
    private static int windowHeight;
    private static int windowWidth;
    private int waveNumber = 1;
    private World world;
    private CommandableEntity player;
    private ArrayList<ArrayList<Integer>> playerMatrix;
    private List<List<AEntity>> entityMatrix;
    private List<CommandableEntity> enemyList;
    private List<AEntity> entityList = new ArrayList<>();
    private EntityDirector director;

    public Main(int windowWidth, int windowHeight) {

        windowHeight = windowHeight;
        windowWidth = windowWidth;
        this.appWindow = new AppWindow(windowHeight, windowHeight, 50, 50, 16, 16);
        this.director = new EntityDirector(new ShipBuilder());
        this.world = this.createBasicWorld();
        for (int i = 0; i < 50; i++) {
            List<List<Integer>> encoded = UTileMatrixDecoder.decodeIntoIntMatrix(world.getMap().getTileMatrix());
            System.out.println(encoded.get(i));
        }
        this.initializeEntities();
        UMovementUtility.setTileMatrix(this.world.getMap().getTileMatrix());
        this.aiCommander = new AICommander(this.entityMatrix, this.world.getMap().getTileMatrix());
        this.appWindow.updateTerrain((UTileMatrixDecoder.decodeIntoIntMatrix(world.getMap().getTileMatrix())));
        this.appWindow.updateEntities(UEntityMatrixDecoder.decodeIntoIntMatrix(this.entityMatrix));
    }

    private void initializeEntities() {
        this.entityMatrix = UEntityMatrixGenerator.createEntityMatrix(50, 50);
        this.enemyList = this.createEnemyWave(this.waveNumber);
        this.player = this.createBasicPlayer();
        this.entityList.add(player);
        this.entityList.addAll(this.enemyList);
        UEntityMatrixGenerator.populateEntityMatrix(this.entityList, this.entityMatrix);
    }

    private World createBasicWorld() {
        IMapGenerator mapGenerator = new AdvancedMapGenerator();
        IWorldGenerator worldGenerator = new BasicWorldGenerator(mapGenerator);
        return worldGenerator.generateWorld(50,50);
    }

    private CommandableEntity createBasicPlayer() {
        return (CommandableEntity) this.director.createPlayer(new Point(3,3));
    }

//    private List<List<Integer>> generatePlayerMatrix(int newPosX, int newPosY) {
//        List<List<Integer>> playerMatrix = new ArrayList<>();
//        for (int i = 0; i < 50; i++) {
//            ArrayList<Integer> row = new ArrayList<>();
//            for (int j = 0; j < 50; j++) {
//                if (i == newPosX && j == newPosY) {
//                    row.add(0);
//                } else {
//                    row.add(-1);
//                }
//            }
//            playerMatrix.add(row);
//        }
//        return playerMatrix;
//    }

    private void decodeController() {
        Set<Integer> request = Main.keyboardController.getInput();
        int command = -1;

        if (request.contains(65) && request.contains(83)) {
            command = 5;
        } else if (request.contains(65) && request.contains(87)) {
            command = 7;
        } else if (request.contains(68) && request.contains(83)) {
            command = 3;
        } else if (request.contains(68) && request.contains(87)) {
            command = 1;
        } else if (request.contains(87)) {
            command = 0;
        } else if (request.contains(68)) {
            command = 2;
        } else if (request.contains(65)) {
            command = 6;
        } else if (request.contains(83)) {
            command = 4;
        }

        if (command != -1) {
//            Point playerPosition = this.player.getPos();
            this.player.moveIfAble(command);
//            UEntityMatrixGenerator.removeEntity(playerPosition, this.entityMatrix);
//            UEntityMatrixGenerator.addEntity(this.player.getPos(), this.player, this.entityMatrix);
//            appWindow.updateEntities(UEntityMatrixDecoder.decodeIntoIntMatrix(this.entityMatrix));
        }
    }


    /**
     * This algorithm creates a list of enemy entities based on the desired wave
     * @param waveNumber The wave number for which to generate
     * @return A list of enemies
     */
    // TODO
    // We should play around with this algorithm and tweak it for improvements. Or rewrite it if necessary.
    // It is difficult to test in current state of development since nothing can use the information
    public List<CommandableEntity> createEnemyWave(int waveNumber) {
        ArrayList<CommandableEntity> enemyList = new ArrayList<>();
        EntityDirector director = new EntityDirector(new ShipBuilder());
        // The lower limit for the enemy level increases by one every three waves
        int enemyLevel = (int) (1 + Math.floor(waveNumber/3));
        int maximumEnemies = 20;

        // Decreasing order so that more low level enemies are created than high level ones
        for (int i = waveNumber; i >= 0; i--) {
            // The factor in which to increase the number of enemies per wave
            int numEnemies = (int) Math.floor(1.2 * i);
            // Checks so that the number of enemies does not exceed the maximum amount allowed
            numEnemies = Math.min(numEnemies, maximumEnemies - enemyList.size());
            for (int j = 0; j < numEnemies; j++) {
                enemyList.add((CommandableEntity) director.createEnemy(new Point(i,j), enemyLevel));
            }
            enemyLevel++;
            if (enemyList.size() >= maximumEnemies) {
                break;
            }
        }
        return enemyList;
    }

    private void updateEntityMatrix() {
        this.entityMatrix = UEntityMatrixGenerator.createEntityMatrix(50, 50);
        UEntityMatrixGenerator.populateEntityMatrix(this.entityList, this.entityMatrix);
        this.aiCommander.setEntityMatrix(this.entityMatrix);
        appWindow.updateEntities(UEntityMatrixDecoder.decodeIntoIntMatrix(this.entityMatrix));
    }

    /**
     * Starts the game
     */
    public void run() throws InterruptedException {
        appWindow.showGame();
        while (true) {
            updatePlayer();
            this.aiCommander.moveEnemies(this.enemyList);
            this.updateEntityMatrix();
            Thread.sleep(50);
        }
    }

    private void updatePlayer() {
        int movementInput = this.keyboardInterpreter.getMovementInput();
        if (movementInput >= 0) {
            this.player.moveIfAble(movementInput);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Main main = new Main(800,800);
        main.run();
    }
}