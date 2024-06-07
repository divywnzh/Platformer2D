package entities;

public abstract class Entity { //class that you can not make object of used for extending (abstraction)
	protected float x,y;//classes that extend Entity can now use protected terms
	public Entity(float x, float y) {
		this.x=x;
		this.y=y;
	}

}
