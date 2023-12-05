package com.group11.model.utility;

import java.awt.Point;
import java.util.List;

import com.group11.model.gameentites.ABody;

/**
 * Utility class for checking if a body is going to collide with another body.
 */
public final class UBodyCollisionUtility {
    
    /**
     * List of bodies to check for collision.
     */
    private static List<List<ABody>> bodies;

    private UBodyCollisionUtility() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Set the bodies to check for collision.
     * @param bodies Matrix of bodies.
     */
    public static void setBodyMatrix(List<List<ABody>> bodies) {
        UBodyCollisionUtility.bodies = bodies;
    }

    private static void checkBodies() {
        if (bodies == null) {
            throw new IllegalStateException("Bodies not set in BodyCollisionUtility");
        }
    }

    /**
     * Check if a position is occupied by another body.
     * @param pos Position in the matrix to check.
     * @return (ABody) if the position is occupied by a body, null otherwise.
     */
    public static ABody isPositionOccupied(Point pos) {

        checkBodies();

        int x = (int) pos.getX();
        int y = (int) pos.getY();

        return bodies.get(x).get(y);
    }

    /**
     * Check if a body is colliding with another body.
     * @param body Body to check.
     * @return (ABody) if the position is occupied by a body, null otherwise.
     */
    public static ABody isBodyColliding(ABody body) {
        
        checkBodies();

        Point bodyPos = body.getPos();
        int x = (int) bodyPos.getX();
        int y = (int) bodyPos.getY();

        return bodies.get(x).get(y);
    }
}