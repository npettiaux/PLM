package plm.universe.bugglequest.ui.command;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Arc2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import plm.universe.bugglequest.BuggleWorldCell;
import plm.universe.ui.CommandWorld;
import plm.universe.ui.CommandWorldView;

public class BuggleCommandWorldView extends CommandWorldView {

	private static final long serialVersionUID = 8145403895802322248L;
	private static Color DARK_CELL_COLOR = new Color(0.93f,0.93f,0.93f);
	private static Color LIGHT_CELL_COLOR = new Color(0.95f,0.95f,0.95f);
	private static Color GRID_COLOR = new Color(0.8f,0.8f,0.8f);
	private static Color WALL_COLOR = new Color(0.0f,0.0f,0.5f);
	
	public BuggleCommandWorldView(CommandWorld w) {
		super(w);
	}
	
	protected double getCellWidth() {
		return (double) Math.min(getHeight() / ((BuggleCommandWorld)world).getHeight() , getWidth() /  ((BuggleCommandWorld)world).getWidth());
	}
	
	protected double getPadX() {
		return (getWidth() - getCellWidth() * ((BuggleCommandWorld)world).getWidth()) / 2;
	}
	protected double getPadY() {
		return (getHeight() - getCellWidth() * ((BuggleCommandWorld)world).getHeight()) / 2;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		drawBackground(g2);
		
		BuggleCommandWorld bcw = (BuggleCommandWorld) world;
		
		for(String key : bcw.buggles.keySet()) {
			BuggleView bv = bcw.buggles.get(key);
			drawBuggle(g2, bv);
		}
		
		drawWalls(g2);
	}
	
	// return the color of the cell located at position (x,y)
	private Color getCellColor(int x, int y) {
		BuggleCommandWorldCell cell = (BuggleCommandWorldCell) ((BuggleCommandWorld)world).getCell(x, y);

		if (BuggleWorldCell.DEFAULT_COLOR.equals(cell.getColor())) {
			if (((BuggleCommandWorld) world).getVisibleGrid()) { 
				if ((x+y)%2==0)
					return DARK_CELL_COLOR;	
				else
					return LIGHT_CELL_COLOR;
			} else {
				return Color.WHITE;
			}
		} else {
			return cell.getColor();
		}		
	}
	
	private void drawBackground(Graphics2D g) {
		double cellW = getCellWidth();
		double padx = getPadX();
		double pady = getPadY();
		BuggleCommandWorld bcw = (BuggleCommandWorld)world;

		if (bcw.getVisibleGrid() == false) {
			g.setColor(Color.white);
			g.fill(new Rectangle2D.Double(padx,pady ,(bcw.getWidth()-1)*cellW,(bcw.getHeight()-1)*cellW));				
		}
		for (int x=0; x<bcw.getWidth(); x++) {
			for (int y=0; y<bcw.getHeight(); y++) {
				g.setColor(getCellColor(x, y));
				
				BuggleCommandWorldCell cell = (BuggleCommandWorldCell) bcw.getCell(x, y);

				g.fill(new Rectangle2D.Double(padx+x*cellW, pady+y*cellW, cellW, cellW));	
				
				if (cell.hasBaggle())
					drawBaggle(g, cell);
				if (cell.hasContent())
					drawMessage(g, cell, cell.getContent());
			}
		}

		if (((BuggleCommandWorld) world).getVisibleGrid()) {
			g.setColor(GRID_COLOR);
			for (int x=0; x<=bcw.getWidth(); x++) {
				g.draw(new Line2D.Double(padx+x*cellW, pady, padx+x*cellW, pady+bcw.getHeight()*cellW));
			}
			for (int y=0; y<=bcw.getHeight(); y++) {
				g.draw(new Line2D.Double(padx+0, pady+y*cellW, padx+bcw.getWidth()*cellW, pady+y*cellW));						
			}
		}
	}
	
	private void drawWalls(Graphics2D g) {
		double cellW = getCellWidth();
		double padx = getPadX();
		double pady = getPadY();
		BuggleCommandWorld bcw = (BuggleCommandWorld)world;

		int width = bcw.getWidth();
		int height = bcw.getHeight();
		
		g.setColor(WALL_COLOR);
		
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				BuggleCommandWorldCell cell = (BuggleCommandWorldCell) bcw.getCell(x, y);

				if (cell.hasTopWall()) {
					g.draw(new Line2D.Double(padx+x*cellW, pady+y*cellW-1, padx+(x+1)*cellW, pady+y*cellW-1));						
					g.draw(new Line2D.Double(padx+x*cellW, pady+y*cellW, padx+(x+1)*cellW, pady+y*cellW));						
					g.draw(new Line2D.Double(padx+x*cellW, pady+y*cellW+1, padx+(x+1)*cellW, pady+y*cellW+1));						
				}
				
				if (cell.hasLeftWall()) {
					g.draw(new Line2D.Double(padx+x*cellW-1, pady+y*cellW, padx+x*cellW-1, pady+(y+1)*cellW));
					g.draw(new Line2D.Double(padx+x*cellW, pady+y*cellW, padx+x*cellW, pady+(y+1)*cellW));
					g.draw(new Line2D.Double(padx+x*cellW+1, pady+y*cellW, padx+x*cellW+1, pady+(y+1)*cellW));
				}
			}
		}

		// frontier walls (since the world is a torus)
		for (int y = 0; y < height; y++) {
			if (((BuggleCommandWorldCell) bcw.getCell(0, y)).hasLeftWall()) {
				g.draw(new Line2D.Double(padx+width*cellW-1, pady+y*cellW, padx+width*cellW-1, pady+(y+1)*cellW));
				g.draw(new Line2D.Double(padx+width*cellW, pady+y*cellW, padx+width*cellW, pady+(y+1)*cellW));
				g.draw(new Line2D.Double(padx+width*cellW+1, pady+y*cellW, padx+width*cellW+1, pady+(y+1)*cellW));				
			}
		}

		for (int x = 0; x < width; x++) {
			if (((BuggleCommandWorldCell) bcw.getCell(x, 0)).hasTopWall()) {
				g.draw(new Line2D.Double(padx+x*cellW, pady+height*cellW-1, padx+(x+1)*cellW, pady+height*cellW-1));						
				g.draw(new Line2D.Double(padx+x*cellW, pady+height*cellW, padx+(x+1)*cellW, pady+height*cellW));						
				g.draw(new Line2D.Double(padx+x*cellW, pady+height*cellW+1, padx+(x+1)*cellW, pady+height*cellW+1));						
			}
		}	
	}
	
	private void drawBuggle(Graphics2D g, BuggleView bv) {
		double scaleFactor = 0.6; // to scale the sprite
		double pixW = scaleFactor * getCellWidth() / BuggleView.INVADER_SPRITE_SIZE;  // fake pixel width
		double pad = 0.5*(1.0-scaleFactor)*getCellWidth(); // padding to center sprite in the cell
		double padx = getPadX();
		double pady = getPadY();
	
		double ox = bv.getX()*getCellWidth(); // x-offset of the cell
		double oy = bv.getY()*getCellWidth(); // y-offset of the cell
		
		if (bv.isBrushDown()) {
			if (Color.BLACK.equals(bv.getBrushColor())) 
				g.setColor(Color.WHITE);
			else
				g.setColor(Color.BLACK);
		} else
			g.setColor(bv.getBodyColor());

		for (int dy=0; dy<BuggleView.INVADER_SPRITE_SIZE; dy++) {
			for (int dx=0; dx<BuggleView.INVADER_SPRITE_SIZE; dx++) {
				int direction = bv.getDirection().intValue();
				if (BuggleView.INVADER_SPRITE[direction][dy][dx] == 1) {
					g.fill(new Rectangle2D.Double(padx+pad+ox+dx*pixW, pady+pad+oy+dy*pixW, pixW, pixW));
				}
			}				
		}
	}
	
	private void drawBaggle(Graphics2D g, BuggleCommandWorldCell cell) {
		double padx = getPadX();
		double pady = getPadY();
	
		double scaleFactor = 0.8; // to scale the sprite

		double d = scaleFactor*getCellWidth();
		double pad = 0.5*(1.0-scaleFactor)*getCellWidth(); // padding to center sprite in the cell

		double scaleFactor2 = 0.5; // to scale the sprite

		double d2 = scaleFactor2*scaleFactor*getCellWidth();
		double pad2 = 0.5*(1.0-scaleFactor*scaleFactor2)*getCellWidth(); // padding to center sprite in the cell
		
		double ox = cell.getX()*getCellWidth(); // x-offset of the cell
		double oy = cell.getY()*getCellWidth(); // y-offset of the cell
		
		g.setColor(BuggleWorldCell.DEFAULT_BAGGLE_COLOR);
		g.fill(new Arc2D.Double(padx+ox+pad, pady+oy+pad, d, d, 0, 360, Arc2D.CHORD));
		g.setColor(getCellColor(cell.getX(), cell.getY()));
		g.fill(new Arc2D.Double(padx+ox+pad2, pady+oy+pad2, d2, d2, 0, 360, Arc2D.CHORD));

		g.setColor(BuggleWorldCell.DEFAULT_BAGGLE_COLOR.darker().darker());
		g.draw(new Arc2D.Double(padx+ox+pad, pady+oy+pad, d, d, 0, 360, Arc2D.CHORD));
		g.draw(new Arc2D.Double(padx+ox+pad2, pady+oy+pad2, d2, d2, 0, 360, Arc2D.CHORD));
	}
	
	private void drawMessage(Graphics2D g, BuggleCommandWorldCell cell, String msg) {
		double padx = getPadX();
		double pady = getPadY();
		double ox = cell.getX()*getCellWidth(); // x-offset of the cell
		double oy = (cell.getY()+1)*getCellWidth(); // y-offset of the cell

		
		g.setColor(cell.getMsgColor());
		g.drawString(msg, (float) (padx+ox)+1, (float) (pady+oy)-4);	
	}
}
