package Game;

import com.senac.SimpleJava.Graphics.*;

public class Bola extends Sprite {
	private int dx = 11; // valor de movimento em X
	private int dy = 11; // valor de movimento em Y
	private int vidas = 3;
	
	public int getDy() {
		return dy;
	}
	public void setDy(int dy) {
		this.dy = dy;
	}
	public int getDx() {
		return dx;
	}
	public void setDx(int dx) {
		this.dx = dx;
	}	
	public Bola(Image bola) {
		super(bola);
	}
	public void move() {
		super.move(dx, dy);
	}
	public void invertHorizontal() {
		dx *= -1;
	}
	public void invertVertical() {
		dy *= -1;
	}	
	public int getVidas() {
		return vidas;
	}	
	public void setVidas(int vidas) {
		this.vidas = vidas;
	}
	public int getRaio() {
		return getWidth()/2;		
	}	
	public void limiteJanela() { // verifica os limites da janela para a bola
		Point posicao = getPosition();		
		if (posicao.x < 0 || posicao.x >= Resolution.HIGHRES.width - 15) {
			invertHorizontal();
		}
		if (posicao.y < 50 || posicao.y >= Resolution.HIGHRES.height - 40) {
			invertVertical();
		}		
	}//**************final limiteJanela	
	
}