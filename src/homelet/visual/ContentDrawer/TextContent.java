package homelet.visual.ContentDrawer;

import com.sun.xml.internal.ws.api.model.MEP;
import homelet.utils.Border;
import homelet.utils.ShapeUtil;
import homelet.utils.ToolBox;

import java.awt.*;
import java.awt.font.LineMetrics;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.text.AttributedCharacterIterator;

public class TextContent extends Content{
	
	public static final double SCRIPT_RATIO = 0.66;
	public static final Font   DEFAULT_FONT = new Font("Lucida Grande", Font.PLAIN, 13);
	private             String content;
	private             Style  style;
	
	class TextContentInfo implements Positioning{
		
		@Override
		public void position(double x, double y){
			point.setLocation(x, y);
		}
		
		final Dimension   bound;
		final Point2D     point;
		final String      content;
		final int         baseLine;
		final LineMetrics line;
		
		public TextContentInfo(String content, Graphics2D g, FontMetrics metrics, double fontHeight){
			this.content = content;
			this.line = metrics.getLineMetrics(content, g);
			this.bound = metrics.getStringBounds(content, g).getBounds().getSize();
			this.baseLine = (int) Math.floor(ToolBox.max(line.getBaselineOffsets()));
			this.point = ShapeUtil.point(0, 0);
		}
		
		void render(Graphics2D g){
			g.setColor(style.background);
			g.draw(ShapeUtil.rectangle(point, bound));
			g.setColor(style.color);
			g.setFont(style.font);
			g.drawString(content, (float) point.getX(), (float) point.getY());
		}
	}
	
	public class Style implements Styling{
		
		@Override
		public Styling duplicate(){
			return new Style(font, color, background, underline, strikeTrough, scriptStyle, border);
		}
		
		private Font        font;
		private Color       color;
		private Color       background;
		private boolean     underline;
		private boolean     strikeTrough;
		private ScriptStyle scriptStyle;
		private Border      border;
		
		public Style(){
			this.font = DEFAULT_FONT;
			this.color = Color.BLACK;
			this.background = null;
			this.underline = false;
			this.strikeTrough = false;
			this.scriptStyle = ScriptStyle.NORMAL;
			this.border = null;
		}
		
		public Style(Font font, Color color, Color background, boolean underline, boolean strikeTrough, ScriptStyle scriptStyle, Border border){
			this.font = font;
			this.color = color;
			this.background = background;
			this.underline = underline;
			this.strikeTrough = strikeTrough;
			this.scriptStyle = scriptStyle;
			this.border = border;
		}
		
		public Font font(){
			return font;
		}
		
		public Style font(Font font){
			if(font == null)
				this.font = DEFAULT_FONT;
			else
				this.font = font;
			return this;
		}
		
		public Color color(){
			return color;
		}
		
		public Style color(Color color){
			this.color = color;
			return this;
		}
		
		public Color background(){
			return background;
		}
		
		public Style background(Color background){
			this.background = background;
			return this;
		}
		
		public Style fontSize(float size){
			this.font = font.deriveFont(size);
			return this;
		}
		
		public boolean isItalic(){
			return font.isItalic();
		}
		
		public Style italic(){
			this.font = font.deriveFont(Font.ITALIC);
			return this;
		}
		
		public boolean isBold(){
			return font.isBold();
		}
		
		public Style bold(){
			this.font = font.deriveFont(Font.BOLD);
			return this;
		}
		
		public boolean isPlain(){
			return font.isPlain();
		}
		
		public Style plain(){
			this.font = font.deriveFont(Font.PLAIN);
			return this;
		}
		
		public boolean underline(){
			return underline;
		}
		
		public Style underline(boolean underline){
			this.underline = underline;
			return this;
		}
		
		public boolean strikeTrough(){
			return strikeTrough;
		}
		
		public Style strikeTrough(boolean strikeTrough){
			this.strikeTrough = strikeTrough;
			return this;
		}
		
		public ScriptStyle scriptStyle(){
			return scriptStyle;
		}
		
		public Style scriptStyle(ScriptStyle scriptStyle){
			this.scriptStyle = scriptStyle;
			return this;
		}
		
		public Border border(){
			return border;
		}
		
		public Style border(Border border){
			this.border = border;
			return this;
		}
	}
	
	private enum ScriptStyle{
		/**
		 * Normal Style content will be treated like normal content
		 */
		NORMAL,
		/**
		 * Super Script style content will be treated with a extended bound (the width of the sub and super script is
		 * depend on the final rendering font) and rendered with a smaller font, 0.66 times smaller
		 *
		 * @see TextContent#SCRIPT_RATIO
		 */
		SUPER_SCRIPT,
		/**
		 * Sub Script style content will be treated with a extended bound (the width of the sub and super script is
		 * depend on the final rendering font) and rendered with a smaller font, 0.66 times smaller
		 *
		 * @see TextContent#SCRIPT_RATIO
		 */
		SUB_SCRIPT;
	}
}
