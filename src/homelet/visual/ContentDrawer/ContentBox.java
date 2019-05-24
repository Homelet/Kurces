package homelet.visual.ContentDrawer;

import homelet.utils.Alignment;
import homelet.utils.Border;

import java.awt.*;
import java.util.Objects;

public class ContentBox{
	
	private double x, y, width, height;
	private Style style;
	
	public final class Style implements Styling{
		
		@Override
		public Style duplicate(){
			return new Style(lineSpacing, margin[0], margin[1], margin[2], margin[3], padding[0], padding[1], padding[2], padding[3], background, bound, content, trueBottom, distributeWordSpacing, border, overflow);
		}
		
		// lineSpacing in px
		private double    lineSpacing;
		// top, bottom, left, right
		private double[]  margin;
		// top, bottom, left, right
		private double[]  padding;
		private Color     background;
		// for context bound position relative to the box
		// when context is processed it form a bound, this alignment is use to process the position of the bound
		// relative to the Content Box position
		private Alignment bound;
		// for content position relative to the line bound,
		// when a line of content is processed it form a bound, this alignment is use to process the bound of the text
		// relative to the line bound,
		// for example: Alignment.BOTTOM means the content is horizontally centered and aligned on bottom
		private Alignment content;
		// when this flag is on, when content alignment is bottom or any bottom relative alignment, it no longer
		// align on base line instead, it align on the true bottom bound of the text
		private boolean   trueBottom;
		// when this flag is on, the content in a line will have the same of amount of spacing around them, if not,
		// the default spacing is one space
		private boolean   distributeWordSpacing;
		// the border for the content box
		private Border    border;
		// Overflow indicator
		// The last few letter of a line or a content box if overflow will be replaced with the overflow symbol
		private String    overflow;
		
		public Style(){
			this.lineSpacing = 10;
			this.margin = new double[]{ 0.0, 0.0, 0.0, 0.0 };
			this.padding = new double[]{ 0.0, 0.0, 0.0, 0.0 }; ;
			this.background = null;
			this.bound = Alignment.TOP_LEFT;
			this.content = Alignment.TOP_LEFT;
			this.trueBottom = false;
			this.distributeWordSpacing = false;
			this.border = null;
			this.overflow = "...";
		}
		
		public Style(double lineSpacing, double marginTop, double marginBottom, double marginLeft, double marginRight, double paddingTop, double paddingBottom, double paddingLeft, double paddingRight, Color background, Alignment bound, Alignment content, boolean trueBottom, boolean distributeWordSpacing, Border border, String overflow){
			this.lineSpacing = lineSpacing;
			this.margin = new double[]{ marginTop, marginBottom, marginLeft, marginRight };
			this.padding = new double[]{ paddingTop, paddingBottom, paddingLeft, paddingRight };
			this.background = background;
			boundAlignment(bound);
			contentAlignment(content);
			this.trueBottom = trueBottom;
			this.distributeWordSpacing = distributeWordSpacing;
			this.border = border;
			overflowIndicator(overflow);
		}
		
		public double lineSpacing(){
			return lineSpacing;
		}
		
		public Style lineSpacing(double lineSpacing){
			this.lineSpacing = lineSpacing;
			return this;
		}
		
		public double marginTop(){
			return margin[0];
		}
		
		public double marginBottom(){
			return margin[1];
		}
		
		public double marginLeft(){
			return margin[2];
		}
		
		public double marginRight(){
			return margin[3];
		}
		
		public Style marginTop(double top){
			margin[0] = top;
			return this;
		}
		
		public Style marginBottom(double bottom){
			margin[1] = bottom;
			return this;
		}
		
		public Style marginLeft(double left){
			margin[2] = left;
			return this;
		}
		
		public Style marginRight(double right){
			margin[3] = right;
			return this;
		}
		
		public Style margin(double margin){
			marginTop(margin);
			marginBottom(margin);
			marginLeft(margin);
			marginRight(margin);
			return this;
		}
		
		public Style margin(double top_bottom, double left_right){
			marginTop(top_bottom);
			marginBottom(top_bottom);
			marginLeft(left_right);
			marginRight(left_right);
			return this;
		}
		
		public Style margin(double top, double bottom, double left, double right){
			marginTop(top);
			marginBottom(bottom);
			marginLeft(left);
			marginRight(right);
			return this;
		}
		
		public double paddingTop(){
			return padding[0];
		}
		
		public double paddingBottom(){
			return padding[1];
		}
		
		public double paddingLeft(){
			return padding[2];
		}
		
		public double paddingRight(){
			return padding[3];
		}
		
		public Style paddingTop(double top){
			padding[0] = top;
			return this;
		}
		
		public Style paddingBottom(double bottom){
			padding[1] = bottom;
			return this;
		}
		
		public Style paddingLeft(double left){
			padding[2] = left;
			return this;
		}
		
		public Style paddingRight(double right){
			padding[3] = right;
			return this;
		}
		
		public Style padding(double padding){
			paddingTop(padding);
			paddingBottom(padding);
			paddingLeft(padding);
			paddingRight(padding);
			return this;
		}
		
		public Style padding(double top_bottom, double left_right){
			paddingTop(top_bottom);
			paddingBottom(top_bottom);
			paddingLeft(left_right);
			paddingRight(left_right);
			return this;
		}
		
		public Style padding(double top, double bottom, double left, double right){
			paddingTop(top);
			paddingBottom(bottom);
			paddingLeft(left);
			paddingRight(right);
			return this;
		}
		
		public Color background(){
			return background;
		}
		
		public Style background(Color background){
			this.background = background;
			return this;
		}
		
		public Alignment boundAlignment(){
			return bound;
		}
		
		public Style boundAlignment(Alignment bound){
			this.bound = Objects.requireNonNull(bound);
			return this;
		}
		
		public Alignment contentAlignment(){
			return content;
		}
		
		public Style contentAlignment(Alignment content){
			this.content = Objects.requireNonNull(content);
			return this;
		}
		
		public boolean trueBottom(){
			return trueBottom;
		}
		
		public Style trueBottom(boolean trueBottom){
			this.trueBottom = trueBottom;
			return this;
		}
		
		public boolean distributeWordSpacing(){
			return distributeWordSpacing;
		}
		
		public Style distributeWordSpacing(boolean distributeWordSpacing){
			this.distributeWordSpacing = distributeWordSpacing;
			return this;
		}
		
		public Border border(){
			return border;
		}
		
		public Style border(Border border){
			this.border = border;
			return this;
		}
		
		public String overflowIndicator(){
			return overflow;
		}
		
		public Style overflowIndicator(String overflow){
			if(overflow == null)
				this.overflow = "";
			else
				this.overflow = overflow;
			return this;
		}
	}
}
