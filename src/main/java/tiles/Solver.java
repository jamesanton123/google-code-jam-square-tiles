package tiles;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class Solver {

	public static void main(String[] args) throws IOException, URISyntaxException {
		new Solver().solve();	
	}
	
	private void solve() throws IOException, URISyntaxException {
		URL url = Solver.class.getClassLoader().getResource("A-large-practice.in");
		System.out.println(url);
		File inputFile = new File(url.toURI());
		List<String> lines = FileUtils.readLines(inputFile);
		List<Pattern> patterns = deserialize(lines);
		List<Pattern> convertedPatterns = convertPatterns(patterns);
		for(Pattern p: convertedPatterns){
			p.print();
		}
	}

	private List<Pattern> convertPatterns(List<Pattern> patterns) {
		List<Pattern> convertedPatterns = new ArrayList<Pattern>();
		for(Pattern p: patterns){
			convertedPatterns.add(p.convert());
		}
		return convertedPatterns;
	}

	private List<Pattern> deserialize(List<String> lines) {
		List<Pattern> patterns = new ArrayList<Pattern>();
		for(int i = 1; i < lines.size(); i++){
			String currentLine = lines.get(i);
			if(currentLine.startsWith(".") || currentLine.startsWith("#")){
				patterns.get(patterns.size() - 1).lines.add(currentLine);
			}else{
				patterns.add(new Pattern());
			}
		}
		return patterns;
	}

	public class Pattern{
		public List<String> lines = new ArrayList<String>();
		public void print(){
			System.out.println("============PATTERN======================");
			for(String line: lines){
				System.out.println(line);
			}
		}
		int matchSum = 140;
		
		public Pattern clone(){
			Pattern p = new Pattern();
			p.lines = lines;
			return p;
		}
		
		public Pattern convert() {
			Pattern clone = clone();		
			
			for(int i = 0; i < lines.size() - 1; i++){		
				for(int j = 0; j < lines.get(i).length() - 1; j++){
					char curLineCurChar = lines.get(i).charAt(j);
					char curLineNextChar = lines.get(i).charAt(j+1);
					char nextLineCurChar = lines.get(i+1).charAt(j);
					char nextLineNextChar = lines.get(i+1).charAt(j+1);
					int sum = curLineCurChar + curLineNextChar + nextLineCurChar + nextLineNextChar;
					if(sum == matchSum){
						// Replace the correct chars in the clone
						clone.setTileToRed(i,j);
					}
				}				
			}			
			return clone;
		}

		/**
		 * Replaces the values of the items in the lines list
		 * @param i The upper row number of the tile to replace
		 * @param j The left column number of the tile to replace
		 */
		private void setTileToRed(int rowIndex, int colIndex) {
			String topString = lines.get(rowIndex);
			String bottomString = lines.get(rowIndex+1);
			StringBuilder top = new StringBuilder(topString);
			StringBuilder bottom = new StringBuilder(bottomString);

			top.setCharAt(colIndex, '/');			
			top.setCharAt(colIndex + 1, '\\');
			bottom.setCharAt(colIndex, '\\');
			bottom.setCharAt(colIndex + 1, '/');
			
			lines.set(rowIndex, top.toString());
			lines.set(rowIndex + 1, bottom.toString());			
		}
	}
}
