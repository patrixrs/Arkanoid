package Game;

import com.senac.SimpleJava.Graphics.*;

public class Paddle extends Sprite {

	public Paddle(Image paddle) {
		super(paddle);
	}
	
	private String local;
	
	public String getLocal() {
		return local;
	}

	public boolean bateu(Bola bola) {
		
		Point posBola = bola.getPosition();
		int raio = bola.getRaio();
		
		Rect tamPaddle = getBounds(); 
		int cima = tamPaddle.y-10;
		int baixo = tamPaddle.y + tamPaddle.height;
		int esquerda = tamPaddle.x;
		int direita = tamPaddle.x + tamPaddle.width;
		
		if (posBola.x-raio > direita) {
			local = "esquerda";
			return false;
		}
		if(posBola.x+raio < esquerda) {
			local = "direita";
			return false;
		}
		if(posBola.y+raio < cima) {
			local = "cima";
			return false;
		}
		if(posBola.y-raio > baixo) {
			return false;
		}		
		return true;		
	}	
}