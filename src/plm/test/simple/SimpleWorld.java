package plm.test.simple;

import javax.script.ScriptEngine;
import javax.script.ScriptException;
import javax.swing.ImageIcon;

import plm.core.lang.ProgrammingLanguage;
import plm.core.model.Game;
import plm.universe.World;

public class SimpleWorld extends World {

	private boolean objectif = false;
	
	public SimpleWorld(String name) {
		super(name);
	}
	public SimpleWorld(String name, boolean objectif) {
		super(name);
		this.objectif = objectif;
	}
	
	public SimpleWorld(SimpleWorld w) {
		super(w);
		this.objectif = w.objectif;
	}
	
	public SimpleWorld copy(SimpleWorld w) {
		SimpleWorld res = new SimpleWorld(w);
		return res;
	}
	
	@Override
	public ImageIcon getIcon() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setupBindings(ProgrammingLanguage lang, ScriptEngine engine)
			throws ScriptException {
		if (lang.equals(Game.PYTHON)) {
			engine.put("w", this);
		}
	}

	@Override
	public boolean equals(Object o){
		if (!(o instanceof SimpleWorld)) {
			return false;
		}
		SimpleWorld other = (SimpleWorld) o;
		if(this.objectif != other.objectif) {
			return false;
		}
		return true;
	}
	
	@Override
	public String diffTo(World world) {
		SimpleWorld other = (SimpleWorld) world;
		String s = "No diff";
		if(this.objectif != other.objectif) {
			s = "Returned "+other.objectif+" while "+objectif+" was expected...";
		}
		return s;
	}

	public void setObjectif(boolean val) {
		objectif = val;
	}
	
	public boolean getObjectif() {
		return objectif;
	}
	
}
