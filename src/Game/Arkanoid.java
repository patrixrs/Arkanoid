package Game;

import java.util.Random;
import java.io.IOException;
import javax.swing.JOptionPane;
import com.senac.SimpleJava.Graphics.*;
import com.senac.SimpleJava.Graphics.events.KeyboardAction;

public class Arkanoid extends GraphicApplication {

	private Bola bola;
	private int qtde = 12; // qtde de blocos	
	private Bloco  linha1Blocos[] = new Bloco[qtde]; //array de blocos tipo1
	private Bloco  linha2Blocos[] = new Bloco[qtde];
	private Bloco linha1Blocos2[] = new Bloco[qtde]; //array de blocos tipo2
	private Bloco linha2Blocos2[] = new Bloco[qtde]; 
	private Bloco linha3Blocos2[] = new Bloco[qtde];	
	private Bloco linha1Blocos3[] = new Bloco[qtde]; //array de blocos tipo3
	private Bloco linha2Blocos3[] = new Bloco[qtde];
	private Bloco linha3Blocos3[] = new Bloco[qtde];
	private Bloco linha4Blocos3[] = new Bloco[qtde];	
	private Random gerador = new Random(); // pra gerar as cores aleatórias
	private Paddle paddle;	
	private Image fundo, fundo1, fundo2, fundo3, fundo4, fundo5, paddleSkin1, bolaAzul, bolaCinza;
	private Image blocoVermelho, blocoPurple, blocoCinza, blocoVerde, blocoAmarelo, blocoAzul;	
	private int score = 0, hiscore = 0, pontos, stage = 0;
	private String nome = null;// pause = "";	
		
	@Override  // SETUP**************************
	protected void setup() { 
		//JANELA=========================================================
		setResolution(Resolution.HIGHRES);
		setFramesPerSecond(25);
		this.nome = JOptionPane.showInputDialog("Digite seu nome: ");		
		if(this.nome == null) this.nome = "SEM NOME";		
		this.setTitle("ARKANOID by patrix");		
		importaImagens();
		mudaFundo();	
		
		//BOLA============================================================
		bola = new Bola(bolaAzul);
		bola.setPosition(Resolution.HIGHRES.width / 2, 485); //posição inicial da bola centro da janela		
		
		//PADDLE==========================================================
		paddle = new Paddle(paddleSkin1); //cor do paddle
		paddle.setPosition(Resolution.HIGHRES.width / 2 - 50, Resolution.HIGHRES.height - 75); //posição inicial do paddle
		
		bindKeyPressed("LEFT", new KeyboardAction() { //VERIFICAÇÂO DE TECLAS
			@Override
			public void handleEvent() {
				Point posPaddle = paddle.getPosition();
				if (posPaddle.x < 22) { //verifica se o paddle chegou no limita esquerdo da janela
					paddle.move(0, 0);
				}else{
					paddle.move(-35, 0); //move para a esquerda
				}
			}
		});
		
		bindKeyPressed("RIGHT", new KeyboardAction() { 
			@Override
			public void handleEvent() {
				Point posPaddle = paddle.getPosition();
				if (posPaddle.x > Resolution.HIGHRES.width - 121) { 
					paddle.move(0, 0);
				}else{
					paddle.move(35, 0); 
				}
			}
		});// FIM DA VERIFICAÇÂO DE TECLAS==================================	
		
	}//**************final do setup

	@Override  // DRAW*****************************
	protected void draw(Canvas canvas) {	
		//JANELA============================================================
		canvas.clear();		
		canvas.drawImage(fundo,0,0); // desenha as informações do jogo
		canvas.setForeground(Color.BLACK);
		canvas.putText(2, 2, 30, "PLAYER:");
		canvas.putText(128, 2, 30, nome);
		canvas.putText(520, 2, 31, "HISCORE");
		canvas.putText(675, 2, 31, String.valueOf(hiscore));
		canvas.putText(290, 2, 31, "SCORE");
		canvas.putText(420, 2, 31, String.valueOf(pontos));
		canvas.putText(350, 566, 31, "VIDAS");
		canvas.putText(2, 567, 27, "ESTAGIO: " + (stage+1));
		if (bola.getVidas()!= 0){ // mostra a qtde de vidas com a imagem bola cinza em cima
			int posX = 450;
			for(int i=1;i<=bola.getVidas();i++){				
				canvas.drawImage(bolaCinza, posX, 574);
				posX += 30; 
			}
		}
		
		//BOLA==============================================================
		bola.draw(canvas);// desenha a bola
		
		//BLOCOS============================================================
		if(stage == 0){
			for(int i=0;i<qtde;i++){  // estagio 1 #########################
				linha1Blocos[i].draw(canvas);// desenha os blocos
				linha2Blocos[i].draw(canvas);				
			}			
		}else if(stage == 1){			
			for(int i=0;i<qtde;i++){  // estagio 2 ########################
				linha1Blocos2[i].draw(canvas);
				linha2Blocos2[i].draw(canvas);
				linha3Blocos2[i].draw(canvas);						
			}
		}else if(stage == 2){			
			for(int i=0;i<qtde;i++){  // estagio 3 ########################
				linha1Blocos3[i].draw(canvas);
				linha2Blocos3[i].draw(canvas);
				linha3Blocos3[i].draw(canvas);
				linha4Blocos3[i].draw(canvas);				
			}
		}
				
		//PADDLE============================================================
		paddle.draw(canvas);// desenha o paddle
		
	}//FIM DO DRAW**********************************************************

	@Override  // LOOP******************************************
	protected void loop() {
		bola.move();
		bola.limiteJanela();
		verificaPaddle();
		verificaScore();
		verificaBlocos();
		
		if (gameOver() == 0){ // verifica a qtde de vidas e termina o jogo
			JOptionPane.showMessageDialog(null,"MAIOR SCORE: "+hiscore,"FIM DE JOGO",JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
		
		if (pontos > hiscore){
			hiscore = score;
		}
		redraw();
		
	}//FIM DO LOOP**********************************************************
	
	private void importaImagens() { //seta as imagens do jogo
		try { // imagens do fundo, paddle e bolas*************************
			fundo1 = new Image("Images/fundo1.jpg");
			fundo2 = new Image("Images/fundo2.jpg");
			fundo3 = new Image("Images/fundo3.jpg");
			fundo4 = new Image("Images/fundo4.jpg");
			fundo5 = new Image("Images/fundo5.jpg");
			paddleSkin1 = new Image("Images/paddleRed.png");
			bolaAzul = new Image("Images/ballBlue.png");
			bolaCinza = new Image("Images/ballGrey.png");
		} catch (IOException e) {
			e.printStackTrace(System.err);
		}		
		try { // imagens dos blocos set1*********************************		
			blocoVermelho = new Image("Images/blocoRed.png");
			blocoPurple = new Image("Images/blocoPurple.png");
			blocoCinza = new Image("Images/blocoGrey.png");
			blocoVerde = new Image("Images/blocoGreen.png");
			blocoAmarelo = new Image("Images/blocoYellow.png");
			blocoAzul = new Image("Images/blocoBlue.png");
		} catch (IOException e) {
			e.printStackTrace(System.err);
		}
	}//FIM DOS SETS DE IMAGENS========================================
	
	private void mudaFundo() {// muda o fundo aleatoriamente
		Image[] fundos = {fundo1, fundo2, fundo3, fundo4, fundo5};
		int setFundo = gerador.nextInt(4);
		fundo = fundos[setFundo];		
	}
	
	private int gameOver(){// verifica as vidas 
		Point posicao = bola.getPosition();		
		if (posicao.y >= Resolution.HIGHRES.height - 40){ // para a bola se chegar no limite de baixo da janela
			bola.setDx(0);
			bola.setDy(0);			
			int numVidas = bola.getVidas();  // avisa que chegou na parte de baixo e informa as vidas restantes
			JOptionPane.showMessageDialog(null, "Vidas restantes: " + (numVidas -1),"MORREU",JOptionPane.WARNING_MESSAGE);
			paddle.setPosition(Resolution.HIGHRES.width / 2 - 50, Resolution.HIGHRES.height - 75); //posição inicial do paddle
			bola.setPosition(Resolution.HIGHRES.width / 2, 500); //posição inicial da bola centro da janela
			numVidas -= 1;
			pontos = 0;
			bola.setVidas(numVidas);
			bola.setDx(11);
			bola.setDy(11);			
		}
		return bola.getVidas();
	}//FIM DO GAMEOVER=====================================================
	
	private void verificaPaddle() {	// verifica se a bola bateu no paddle
		if (paddle.bateu(bola)){ 
			if (paddle.getLocal().equals("cima")){
				bola.invertVertical(); // inverte se bateu em cima do paddle				
			}
			else if (paddle.getLocal().equals("esquerda") || paddle.getLocal().equals("direita")){
				bola.invertVertical(); // inverte se bateu nas laterais
				bola.invertHorizontal();				
			}
		}		
	}// FIMA DO VERIFICA PADDLE=============================================

	private void verificaLayout(int valor) {// altera o layout baseado no score
		Image[] blocos1 = {blocoVermelho, blocoPurple, blocoVermelho};
		Image[] blocos2 = {blocoCinza, blocoVerde, blocoVermelho};
		Image[] blocos3 = {blocoAmarelo, blocoAzul, blocoVermelho};		
		int setCor = gerador.nextInt(2); // armazena a posição da array de cores		
		int posX = 4;		
		switch(stage){// SWITCH DE ESTAGIOS=====================
			case 0:			
				for(int i=0; i<qtde; i++){   // estagio 1 ###################
					linha1Blocos[i] = new Bloco(blocos1[setCor]); //cor dos blocos gerados randômicamente
					linha1Blocos[i].setPosition(posX, 85); //define posição dos blocos
					linha2Blocos[i] = new Bloco(blocos2[setCor]); 
					linha2Blocos[i].setPosition(posX, 117);					
					posX = posX + 66; // distancia dos blocos					
				}				
			break;
			case 1:				
				for(int i=0; i<qtde; i++){  // estagio 2 ###################
					linha1Blocos2[i] = new Bloco(blocos2[setCor]);
					linha1Blocos2[i].setPosition(posX, 85);
					linha2Blocos2[i] = new Bloco(blocos1[setCor]); 
					linha2Blocos2[i].setPosition(posX, 117); 
					linha3Blocos2[i] = new Bloco(blocos3[setCor]); 
					linha3Blocos2[i].setPosition(posX, 149);					
					posX = posX + 66; // distancia dos blocos					
				}
			break;
			case 2:
				for(int i=0; i<qtde; i++){  // estagio 3 ###################
					linha1Blocos3[i] = new Bloco(blocos3[setCor]);
					linha1Blocos3[i].setPosition(posX, 85);
					linha2Blocos3[i] = new Bloco(blocos2[setCor]); 
					linha2Blocos3[i].setPosition(posX, 117); 
					linha3Blocos3[i] = new Bloco(blocos1[setCor]); 
					linha3Blocos3[i].setPosition(posX, 149);
					linha4Blocos3[i] = new Bloco(blocos3[setCor]); 
					linha4Blocos3[i].setPosition(posX, 181);					
					posX = posX + 66; // distancia dos blocos						
				}
			break;
		}// FIM DO SWITCH DE ESTAGIOS======================================
		
	}//FIM DO LAYOUT=======================================================
			
	private void verificaScore() { // muda o estagio conforme o score		
		if(score == 0){ 		 // estagio 1 ###########
			stage = 0;
			verificaLayout(stage);
		}else if(score == 3600){ // estagio 2 ###########			
			stage = 1;			
			verificaLayout(stage);
		}else if(score == 8400){// estagio 3 ###########
			stage = 2;			
			verificaLayout(stage);
		}else if(score == 14400){
			JOptionPane.showMessageDialog(null,"MAIOR SCORE: "+hiscore,"FIM DE JOGO",JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
	}//FIM DO VERIFICA SCORE================================================
	
	private void verificaBlocos() { //verifica se a bola bateu nos blocos
		if(stage == 0){  // estagio 1 ######################################
			for(int i=0; i<qtde; i++){
				if(linha1Blocos[i].bateu2(bola)
				|| linha2Blocos[i].bateu(bola))
				{					
					score = score +100;
					pontos = pontos +100;
				}
			}
		}else if(stage == 1){  // estagio 2 ################################
			for(int i=0; i<qtde; i++){	 
				if(linha1Blocos2[i].bateu2(bola)
				|| linha2Blocos2[i].bateu(bola)
				|| linha3Blocos2[i].bateu(bola))
				{
					score = score +100;
					pontos = pontos +100;
				}
			}			
		}else if(stage == 2){  // estagio 3 ################################
			for(int i=0; i<qtde; i++){	  
				if(linha1Blocos3[i].bateu2(bola)
				|| linha2Blocos3[i].bateu(bola)
				|| linha3Blocos3[i].bateu(bola)
				|| linha4Blocos3[i].bateu(bola))
				{
					score = score +100;
					pontos = pontos +100;
				}
			}			
		}		
	}//FIM DO VERIFICA BLOCOS===============================================
		
}// FIM ARKANOID **##==##**