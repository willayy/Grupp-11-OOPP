package com.group11.model.gameentites;

import java.awt.Point;

/**
 * An entity represents anything interactive in the world, it could be a town, creature, ship etc
 */
public abstract class AEntity implements IDamageable {
    
    private String name;
    private Boolean friendly;
    private ABody body;

    /**
     * Used to construct an entity
     * @param name The entities name
     * @param friendly A single factor which assigns the life long allegience of the entity
     * @param body The body of the entity
     */
    protected AEntity(ABody body, String name, Boolean friendly) {
        this.body = body;
        this.name = name;
        this.friendly = friendly;
    }

    /**
     * 
     * @return (String) Name of the entity
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @return (Boolean) The Allegience of the entity
     */
    public Boolean isFriendly() {
        return friendly;
    }

    /**
     * 
     * @param friendly The allegience of the entity
     */
    public void setFriendly(Boolean friendly) {
        this.friendly = friendly;
    }

    /**
     * @return (ABody) The body of the entity
     */
    public ABody getBody() {
        return body;
    }

    public void takeDamage(int damage) {
        this.body.takeDamage(damage);
    }

    public int getHitPoints() {
        return (int) this.body.getHitPoints();
    }

    public void setHitPoints(int hp) {
        this.body.setHitPoints(hp);
    }

    /**
     * 
     * @param body The new body of the entity
     */
    protected void setBody(ABody body) {
        this.body = body;
    }

    /**
     * A safe (wihout giving outside access and possibilty to modify position) 
     * way to return the positon of the entities body.
     * @return (Point) The position of the entities body
     */
    public Point getPos() {
        return this.body.getPos();
    }
}
