package com.group11;

import java.awt.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.group11.controller.GlobalKeyListener;
import com.group11.model.AMovableBody;
import com.group11.model.AdvancedMapGenerator;
import com.group11.model.BasicMapGenerator;
import com.group11.model.BasicWorldGenerator;
import com.group11.model.CommandableEntity;
import com.group11.model.IMapGenerator;
import com.group11.model.IWorldGenerator;
import com.group11.model.MovementUtility;
import com.group11.model.Ship;
import com.group11.model.TileMatrixDecoder;
import com.group11.model.World;
import com.group11.view.AppWindow;
import com.group11.view.ViewTileMatrixEncoder;

class Main {
    private AppWindow appWindow;
    private static final GlobalKeyListener keyboardController = new GlobalKeyListener();
    private static int windowHeight;
    private static int windowWidth;
    private World world;
    private CommandableEntity player;
    private ArrayList<ArrayList<Integer>> playerMatrix;

    public Main(int windowWidth, int windowHeight) {

        windowHeight = windowHeight;
        windowWidth = windowWidth;
        this.appWindow = new AppWindow(windowHeight, windowHeight, 50, 50, 16, 16);
        this.world = this.createBasicWorld();
        this.player = this.createBasicPlayer();
        MovementUtility.setTileMatrix(this.world.getMap().getTileMatrix());
        this.appWindow.updateTerrain(ViewTileMatrixEncoder.createTerrainTileMatrix(TileMatrixDecoder.decodeIntoIntMatrix(world.getMap().getTileMatrix())));
    }

    private World createBasicWorld() {
        IMapGenerator mapGenerator = new AdvancedMapGenerator();
        IWorldGenerator worldGenerator = new BasicWorldGenerator(mapGenerator);
        return worldGenerator.generateWorld(50);
    }

    private CommandableEntity createBasicPlayer() {
        AMovableBody ship = new Ship(new Point(3,3));
        return new CommandableEntity(ship, "Player", true);
    }

    private List<List<Integer>> generatePlayerMatrix(int newPosX, int newPosY) {
        List<List<Integer>> playerMatrix = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            ArrayList<Integer> row = new ArrayList<>();
            for (int j = 0; j < 50; j++) {
                if (i == newPosX && j == newPosY) {
                    row.add(0);
                } else {
                    row.add(-1);
                }
            }
            playerMatrix.add(row);
        }
        return playerMatrix;
    }

    private void decodeController() {
        Set<Integer> request = Main.keyboardController.getInput();

        if (request.contains(65) && request.contains(83)) {    
            this.player.moveCommand(1);
            int newPosX = (int) this.player.getPos().getX();
            int newPosY = (int) this.player.getPos().getY();
            appWindow.updateEntities(ViewTileMatrixEncoder.createEntityTileMatrix(generatePlayerMatrix( newPosX, newPosY)));
        }
        if (request.contains(65) && request.contains(87)) {
            this.player.moveCommand(7);
            int newPosX = (int) this.player.getPos().getX();
            int newPosY = (int) this.player.getPos().getY();
            appWindow.updateEntities(ViewTileMatrixEncoder.createEntityTileMatrix(generatePlayerMatrix( newPosX, newPosY)));
        }
        if (request.contains(68) && request.contains(83)) {
            this.player.moveCommand(3);
            int newPosX = (int) this.player.getPos().getX();
            int newPosY = (int) this.player.getPos().getY();
            appWindow.updateEntities(ViewTileMatrixEncoder.createEntityTileMatrix(generatePlayerMatrix( newPosX, newPosY)));
        }
        if (request.contains(65) && request.contains(87)) {
            this.player.moveCommand(5);
            int newPosX = (int) this.player.getPos().getX();
            int newPosY = (int) this.player.getPos().getY();
            appWindow.updateEntities(ViewTileMatrixEncoder.createEntityTileMatrix(generatePlayerMatrix( newPosX, newPosY)));
        }
        if (request.contains(87)) {
            this.player.moveCommand(6);
            int newPosX = (int) this.player.getPos().getX();
            int newPosY = (int) this.player.getPos().getY();
            appWindow.updateEntities(ViewTileMatrixEncoder.createEntityTileMatrix(generatePlayerMatrix( newPosX, newPosY)));
        }
        if (request.contains(68)) {
            this.player.moveCommand(4);
            int newPosX = (int) this.player.getPos().getX();
            int newPosY = (int) this.player.getPos().getY();
            appWindow.updateEntities(ViewTileMatrixEncoder.createEntityTileMatrix(generatePlayerMatrix( newPosX, newPosY)));
        }
        if (request.contains(65)) {
            this.player.moveCommand(0);
            int newPosX = (int) this.player.getPos().getX();
            int newPosY = (int) this.player.getPos().getY();
            appWindow.updateEntities(ViewTileMatrixEncoder.createEntityTileMatrix(generatePlayerMatrix( newPosX, newPosY)));
        }
        if (request.contains(83)) {
            this.player.moveCommand(2);
            int newPosX = (int) this.player.getPos().getX();
            int newPosY = (int) this.player.getPos().getY();
            appWindow.updateEntities(ViewTileMatrixEncoder.createEntityTileMatrix(generatePlayerMatrix( newPosX, newPosY)));
        }
        
    }

    /**
     * Starts the game
     */
    public void run() throws InterruptedException {
        appWindow.showGame();
        while (true) {
            this.decodeController();
            Thread.sleep(50);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Main main = new Main(800,800);
        main.run();
    }
}