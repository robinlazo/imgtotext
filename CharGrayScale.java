package pixelArt;

public enum CharGrayScale {
		White(' '), Lightgray('.'), Slategray(':'), Gray('-'),
        Medium('='), MediumGray('+'), MediumDarkGray('*'),
		Darkgray('#'), Charcoal('%'), Black('@');
	
		private char representation;
		
		CharGrayScale(char ch) { representation = ch; }
		
		char getRepresentation() {return representation; }
	
}
