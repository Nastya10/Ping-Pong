package application;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class Main extends Application {
	
	private static final int height = 600;
	private static final int width= 800;

	private static final int RACKET_WIDTH=10;
	private static final int RACKET_HEIGHT = 90;
	private static final int BALL_R = 30;
	double compX = width - RACKET_WIDTH;
	double compY = height/2;
	double playerX = 0;
	double playerY = height/2;
	double ballX = width/2;
	double ballY = height/2;
	
	GraphicsContext gc ;
	
	boolean gameStarted;
	double speedY = 3;
	double speedX =3;
	double n=1;

	public void drawField() {
		gc.setFill(Color.BLUE);
		gc.fillRect(0,0,width,height);
		
		gc.setFill(Color.GREEN);
		gc.fillRect(width/2, 0, 2, height);
		
		gc.setFill(Color.YELLOW);
		
		if(gameStarted) {
			ballX+=-speedX;
			ballY+=-speedY;
			if(ballX <width-width/4)
			{
				compY = ballY- RACKET_WIDTH;
			}
			if(ballY == height-BALL_R) {
				speedY = -speedY;
			}
			if( ballY == 0){
				speedY = -speedY;
			}
			if ((ballY+BALL_R >=playerY)&&(ballY+BALL_R<=playerY+RACKET_HEIGHT)&&(ballX==playerX+RACKET_WIDTH)) {
				n = Math.random();
				speedY = 1*speedY;
				speedX = -1*speedX;
			}

			
		}
		
		gc.fillOval(ballX-BALL_R/2, ballY, BALL_R, BALL_R);
		
		gc.setFill(Color.RED);
		gc.fillRect(playerX, playerY, RACKET_WIDTH, RACKET_HEIGHT);
		gc.fillRect(compX, compY, RACKET_WIDTH, RACKET_HEIGHT);
		
	}
	
	@Override
	public void start(Stage primaryStage) {
		Canvas canvas = new Canvas(width,height);
		gc = canvas.getGraphicsContext2D();
		drawField();
		
		Timeline t1 = new Timeline(new KeyFrame(Duration.millis(10), e->drawField()));
		t1.setCycleCount(Timeline.INDEFINITE);
		t1.play();
		
		canvas.setOnMouseClicked(e->gameStarted = true);
		canvas.setOnMouseMoved(e-> playerY = e.getY());
		
		primaryStage.setScene(new Scene(new StackPane(canvas)));
		primaryStage.setTitle("Ping-pong");
		
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}