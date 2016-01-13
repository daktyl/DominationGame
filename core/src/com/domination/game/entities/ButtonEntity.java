package com.domination.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.domination.game.engine.ResourceManager;

public class ButtonEntity extends Entity {

    private TextEntity text;
    BitmapFont font;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private boolean mouseOver;
    ClickListener clickListener = null;
    boolean clicked=false;
    int margin=20;
    private ButtonEntity prev,next;
    public ButtonEntity(String label, float x, float y, SpriteBatch batch) {
        super(batch);
        text=new TextEntity(label,(BitmapFont) ResourceManager.getInstance().get("Font50"),batch);
        text.label.setX(x);
        text.label.setY(y);
        text.label.setText(label);
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.setAutoShapeType(true);
    }
    public void setClickListener(ClickListener clickListener){
        this.clickListener = clickListener;
    }
    @Override
    public void draw() {

        text.draw();
        batch.end();
        if(mouseOver) {
            shapeRenderer.setColor(Color.BLACK);
            shapeRenderer.begin();
            shapeRenderer.rect(text.label.getX() -margin, text.label.getY()-margin, text.label.getWidth() + 2*margin,text.label.getHeight()+2*margin);
            shapeRenderer.end();
        }
        batch.begin();
    }
    boolean isOnText(int screenX,int screenY){
        return (screenX > text.label.getX() - margin) && (screenX < text.label.getX() + text.label.getWidth() + margin) &&
                (screenY > text.label.getY() -margin) && (screenY < text.label.getY() + text.label.getHeight()  +margin);
    }
    public int getWidth(){
        return (int)text.label.getWidth()+2*margin;
    }
    public int getHeight(){
        return (int)text.label.getHeight()+2*margin;
    }
    @Override
    public void update() {
        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
        if(isOnText(mouseX, mouseY)) {
            if (Gdx.input.isTouched()) {
                if(!clicked)clickListener.clicked(null, mouseX, mouseY);
                clicked=true;
            }
            else {
                mouseOver = true;
                clicked=false;
            }
        }
        else
            mouseOver=false;
    }

    public float getX(){
        return text.label.getX();
    }

    public float getY(){
        return text.label.getY();
    }
}
