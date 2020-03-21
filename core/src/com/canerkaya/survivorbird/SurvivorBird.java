package com.canerkaya.survivorbird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;

import java.util.Random;

public class SurvivorBird extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture background;
	private Texture bird;
	private Texture enemy;
	private Texture enemy2;
	private Texture enemy3;
	private Texture enemy4;
	private float birdX;
	private float birdY;
	private int gameState=0;// oyun başlamadı
	private float velocity=0;
	private float gravity=0.4f;
	private int numberOfEnemies=5;
	private float[] enemyX= new float[numberOfEnemies];
	private float[]enemyOffset=new float[numberOfEnemies];
	private float[]enemyOffset2=new float[numberOfEnemies];
	private float[]enemyOffset3=new float[numberOfEnemies];
	private float[]enemyOffset4=new float[numberOfEnemies];
	private float distance;
	private float enemyVelocity=3;
	private Random random;
	private Circle birdCircle;
	private Circle[]enemyCircle;
	private Circle[]enemyCircle2;
	private Circle[]enemyCircle3;
	int score=0;
	int scoredEnemy=0;
	BitmapFont font;
	BitmapFont font2;

	@Override
	public void create () {
		batch=new SpriteBatch();
		background=new Texture("background.png");
		bird=new Texture("bird.png");
		enemy=new Texture("enemy.png");
		enemy2=new Texture("enemy.png");
		enemy3=new Texture("enemy.png");
		enemy4=new Texture("enemy.png");
		birdX=Gdx.graphics.getWidth()/5;
		birdY=Gdx.graphics.getHeight()/2;
		birdCircle=new Circle();
		enemyCircle=new Circle[numberOfEnemies];
		enemyCircle2=new Circle[numberOfEnemies];
		enemyCircle3=new Circle[numberOfEnemies];
		distance=Gdx.graphics.getWidth()/2;
		random=new Random();
		font=new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(4);
		font2=new BitmapFont();
		font2.setColor(Color.WHITE);
		font2.getData().setScale(6);

		for (int i=0;i<numberOfEnemies;i++){
			enemyX[i]=Gdx.graphics.getWidth()-enemy.getWidth()/2+i*distance;
			enemyOffset[i]=(random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-200);
			enemyOffset2[i]=(random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-200);
			enemyOffset3[i]=(random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-200);
			enemyOffset4[i]=(random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-200);
			enemyCircle[i]=new Circle();
			enemyCircle2[i]=new Circle();
			enemyCircle3[i]=new Circle();
		}
	}

	@Override
	public void render () {
		batch.begin();
		batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

		if (gameState==1){ // oyun başladıysa

			if (enemyX[scoredEnemy]<birdX){
				score++;
				if (scoredEnemy<numberOfEnemies-1){
					scoredEnemy++;
				}else {
					scoredEnemy=0;
				}
			}
			if (birdY<Gdx.graphics.getHeight()-Gdx.graphics.getHeight()/10){
				if (Gdx.input.justTouched()){
					velocity=-8;
				}
			}





			for (int i=0;i<numberOfEnemies;i++){
				if (enemyX[i]<-enemy.getWidth()){
					enemyX[i]+=numberOfEnemies*distance;
					enemyOffset[i]=(random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-200);
					enemyOffset2[i]=(random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-200);
					enemyOffset3[i]=(random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-200);

				}else {
					enemyX[i]-=enemyVelocity;
					enemyVelocity+=0.0001;
				}

				batch.draw(enemy,enemyX[i],Gdx.graphics.getHeight()/2+enemyOffset[i],Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight()/10);
				batch.draw(enemy2,enemyX[i],Gdx.graphics.getHeight()/2+enemyOffset2[i],Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight()/10);
				batch.draw(enemy3,enemyX[i],Gdx.graphics.getHeight()/2+enemyOffset3[i],Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight()/10);
				batch.draw(enemy4,enemyX[i],Gdx.graphics.getHeight()/2+enemyOffset3[i],Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight()/10);
				enemyCircle[i]=new Circle(enemyX[i]+Gdx.graphics.getWidth()/30,Gdx.graphics.getHeight()/2+enemyOffset[i]+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/70);
				enemyCircle2[i]=new Circle(enemyX[i]+Gdx.graphics.getWidth()/30,Gdx.graphics.getHeight()/2+enemyOffset2[i]+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/70);
				enemyCircle3[i]=new Circle(enemyX[i]+Gdx.graphics.getWidth()/30,Gdx.graphics.getHeight()/2+enemyOffset3[i]+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/70);
			}





			if (birdY>0){
				velocity+=gravity;
				birdY=birdY-velocity;
			}else {
				gameState=2;
			}
		}else if (gameState==0){
			if (Gdx.input.justTouched()){
				gameState=1;
			}
		}else if (gameState==2){
			enemyVelocity=3;
			font2.draw(batch,"Tekrar oynamak için dokun...",100,Gdx.graphics.getHeight()/2);
			if (Gdx.input.justTouched()){
				gameState=1;
				birdY=Gdx.graphics.getHeight()/2;
				for (int i=0;i<numberOfEnemies;i++){
					enemyX[i]=Gdx.graphics.getWidth()-enemy.getWidth()/2+i*distance;
					enemyOffset[i]=(random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-200);
					enemyOffset2[i]=(random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-200);
					enemyOffset3[i]=(random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-200);
					enemyOffset4[i]=(random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-200);
					enemyCircle[i]=new Circle();
					enemyCircle2[i]=new Circle();
					enemyCircle3[i]=new Circle();
				}
				velocity=0;
				scoredEnemy=0;
				score=0;
			}
		}


		batch.draw(bird,birdX,birdY,Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight()/10);
		font.draw(batch,String.valueOf(score),100,200);
		batch.end();
		birdCircle.set(birdX+Gdx.graphics.getWidth()/30,birdY+Gdx.graphics.getHeight()/10,Gdx.graphics.getWidth()/70);

		for (int i =0;i<numberOfEnemies;i++){
			if (Intersector.overlaps(birdCircle,enemyCircle[i]) || Intersector.overlaps(birdCircle,enemyCircle2[i]) || Intersector.overlaps(birdCircle,enemyCircle3[i])){
				gameState=2; // oyun bitti
			}
		}
	}

	@Override
	public void dispose () {

	}
}
