package Game;

import com.senac.SimpleJava.Graphics.*;

public class Bloco extends Sprite {

	private boolean vivo = true;
	private int cont = 0;
	
	public Bloco(Image bloco) {
		super(bloco);
	}		
			
	public boolean isVivo() {
		return vivo;
	}

	public void setVivo(boolean vivo) {
		this.vivo = vivo;
	}
		
	public boolean bateu(Bola bola){ // Se o bloco nas esta vivo, nao pode bater
		
		Point posBola = bola.getPosition();
		Point posBloco = super.getPosition();
		
		if(!vivo){
			return false;
		}
			
		if(posBola.x+bola.getWidth() < posBloco.x){
			return false;
		}		
		else if(posBola.x > posBloco.x+getWidth() ){
			return false;
		}
		else if(posBola.y+bola.getHeight() < posBloco.y){
			return false;
		}
		else if(posBola.y > posBloco.y+getHeight()){
			return false;
		}else{			
			if(posBola.x == posBloco.x+getWidth()){
				bola.invertHorizontal();
			}else if(posBola.x+bola.getWidth() == posBloco.x){
				bola.invertHorizontal();
			}else{
				bola.invertVertical();
			}
			vivo = false;
			return true;	
		}
	}
	
public boolean bateu2(Bola bola){ // Se o bloco nas esta vivo, nao pode bater
	
		Point posBola = bola.getPosition();
		Point posBloco = super.getPosition();		
		
		if(!vivo){			
			return false;
		}
			
		if(posBola.x+bola.getWidth() < posBloco.x){
			return false;
		}		
		else if(posBola.x > posBloco.x+getWidth() ){
			return false;
		}
		else if(posBola.y+bola.getHeight() < posBloco.y){
			return false;
		}
		else if(posBola.y > posBloco.y+getHeight()){
			return false;
		}else{			
			if(posBola.x == posBloco.x+getWidth()){
				bola.invertHorizontal();
				cont++;
			}else if(posBola.x+bola.getWidth() == posBloco.x){
				bola.invertHorizontal();
				cont++;
			}else{
				bola.invertVertical();
				cont++;
			}
			if(cont == 2){
				vivo = false;
				cont = 0;
			}else{
				vivo = true;				
			}
			return true;	
		}
	} 
	
	@Override
	public void draw(Canvas canvas) {
		if (vivo){
			super.draw(canvas);			
		} 
	}
}