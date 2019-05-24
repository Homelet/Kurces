package homelet.visual.ContentDrawer;

import homelet.utils.Alignment;

public class ContentLine{
	
	public class Style implements Styling{
		
		@Override
		public Style duplicate(){
			return null;
		}
		
		// if > 0 then follow the box's style, in px
		private double    lineSpacing;
		// if null then follow the box's style
		private Alignment line;
	}
}
