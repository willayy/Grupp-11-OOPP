package com.group11.view.uicomponents;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Class used to represent the game over screen of the game as a JPanel with components
 */
public class GameOverPanel extends JPanel {

    private JLabel gameOverLabel;
    private JLabel scoreLabel;
    private Button backToMenuButton;
    private boolean backToMenuButtonPressed = false;

    /**
     * Constructor for creating a GameOverPanel.
     * @param width width of the panel
     * @param height height of the panel
     */
    public GameOverPanel(int width, int height) {
        super();
        this.setPreferredSize(new Dimension(width, height));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(java.awt.Color.GRAY);

        this.gameOverLabel = new JLabel("GAME OVER");
        this.scoreLabel = new JLabel("SCORE: 0");
        this.backToMenuButton = new Button("Back to Menu");

        Font bigFont = new Font("Arial", Font.PLAIN, 50);
        Font smallFont = new Font("Arial", Font.PLAIN, 20);
        Dimension buttonSize = new Dimension(200, 50);

        gameOverLabel.setFont(smallFont);
        scoreLabel.setAlignmentX(CENTER_ALIGNMENT);
        gameOverLabel.setFont(bigFont);
        gameOverLabel.setAlignmentX(CENTER_ALIGNMENT);
        backToMenuButton.setMaximumSize(buttonSize);
        backToMenuButton.addActionListener(e -> backToMenuButtonPressed());
        
        this.add(new BufferPanel(width, 50));
        this.add(gameOverLabel);
        this.add(scoreLabel);
        this.add(backToMenuButton);
    }
    
    /**
     * Method used to set the score label for the game over screen.
     * @param score
     */
    public void setScoreLabel(int score) {
        this.scoreLabel.setText("SCORE: " + score);
    }

    /**
     * Method used to get the back to menu button pressed boolean
     * @return
     */
    public boolean getBackToMenuButtonPressed() {
        return this.backToMenuButtonPressed;
    }

    /**
     * Method used to reset the back to menu button to not pressed
     */
    public void resetBackToMenuButtonPressed() {
        this.backToMenuButtonPressed = false;
    }

    /**
     * Method used to set the back to menu button to pressed
     */
    private void backToMenuButtonPressed() {
        if (backToMenuButton.hasFocus()) {
            this.backToMenuButtonPressed = true;
        }
    }

}
