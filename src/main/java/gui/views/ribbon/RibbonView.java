package gui.views.ribbon;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import javafx.scene.Group;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;

import db.DatabaseManager;
import gui.views.phylogeny.NewickColourMatching;
import parsers.XlsxParser;

public class RibbonView {
	
	private static String xlsxpath = System.getProperty("user.dir") + File.separator + "Data"
			+ File.separator + "TB10" + File.separator + "metadata" + ".xlsx";
	
	private DatabaseManager dbm;
	private HashMap<String, String> lineages = updateLineages();
	private ArrayList<Integer> genomeIds = createList();
	
	public RibbonView(DatabaseManager dbm) {
		this.dbm = dbm;
	}
	
	private HashMap<String, String> updateLineages() {
		XlsxParser xlsxparser = new XlsxParser();
		xlsxparser.parse(xlsxpath);
		return xlsxparser.getLineages();
	}
	
	public void setGenomeIds(ArrayList<Integer> ids) {
		genomeIds = ids;
	}
	
	public ArrayList<Integer> createList() {
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < 10; i++) {
			list.add(i);
		}
		return list;
	}
	
	/**
	 * Creates all paths that make up the ribbons and returns a {@link Group}
	 * containing those paths.
	 * 
	 * @return A group containing the ribbons.
	 */
	public Group createNormalRibbons() {
		System.out.println("Creating normal ribbons");
		Group res = new Group();
		ArrayList<ArrayList<Integer>> links = dbm.getDbReader().getLinks(genomeIds);
		ArrayList<ArrayList<Integer>> counts = dbm.getDbReader().getLinkWeights(genomeIds);
		ArrayList<ArrayList<Paint>> colours = calculateColours(links, genomeIds);
		ArrayList<Integer> xcoords = dbm.getDbReader().getAllXCoord();
		ArrayList<Integer> ycoords = dbm.getDbReader().getAllYCoord();
		
		for (int fromId = 1; fromId <= links.size(); fromId++) {
			for (int j = 0; j < links.get(fromId - 1).size(); j++) {
				int toId = links.get(fromId - 1).get(j);
				Line line = new Line(xcoords.get(fromId - 1), ycoords.get(fromId - 1), 
						xcoords.get(toId - 1), ycoords.get(toId - 1));
				line.setStrokeWidth(counts.get(fromId - 1).get(j));
				line.setStroke(colours.get(fromId - 1).get(j));
		        res.getChildren().add(line);
			}
		}
		System.out.println("Finished normal ribbons");
		return res;
	}
	
	
	
	
	
	/**
	 * Given a from and toId from a bubble. It determines if the bubble is a snip or not.
	 * This means that between the fromId and toId there is a single nucleotide that is different
	 * compared to another genome. It is not a snip if one of the toIds from the fromId is equal to 
	 * the toId. This means that there is a deletion. It is not a snip if the length of one of the 
	 * toIds is greater than 1, this means that the difference is not only a single nucleotide. It 
	 * is also not a snip if one segment from the toIds does not contain the toId. This means that 
	 * there is something in between that segment and the toId.
	 * 
	 * @param fromId	the starting segment of the bubble
	 * @param toId		the ending segment of the bubble
	 * @return			returns true if it is a snip else false
	 */
	public boolean isSnip(int fromId, int toId) {
		ArrayList<Integer> toIds = dbm.getDbReader().getToIDs(fromId);
		for (int current : toIds) {
			String currentContent = dbm.getDbReader().getContent(current);
			String toIdContent = dbm.getDbReader().getContent(toId);
			if (currentContent.length() > 1)
				return false;
			else if (currentContent.equals(toIdContent))
				return false;
			else {
				if (!dbm.getDbReader().getToIDs(current).contains(toId))
					return false;
				else return true;
			}
		}
		return false;
			
			
			
//			if (dbm.getDbReader().getContent(current).equals(dbm.getDbReader().getContent(toId))) {
////				System.out.println("from content is: " + dbm.getDbReader().getContent(fromId));
////				System.out.println("to content is: " + dbm.getDbReader().getContent(toId));
////				System.out.println("in/del detected");
//				return false;
//			}
//			if (dbm.getDbReader().getContent(current).length() > 1) {
////				System.out.println("current content: " + dbm.getDbReader().getContent(current) + " is longer than 1");
////				System.out.println("from content is: " + dbm.getDbReader().getContent(fromId));
////				System.out.println("to content is: " + dbm.getDbReader().getContent(toId));
////				System.out.println("from: " + fromId + " to: " + toId +" is not a snip");
//				return false;
//			}
//			if (!dbm.getDbReader().getToIDs(current).contains(toId)) {
////				System.out.println("does not contain destination");
////				System.out.println("from content is: " + dbm.getDbReader().getContent(fromId));
////				System.out.println("to content is: " + dbm.getDbReader().getContent(toId));
////				System.out.println("from: " + fromId + " to: " + toId +" is not a snip");
//				return false;
//			}
//		}
		
//		System.out.println("from content is: " + dbm.getDbReader().getContent(fromId));
//		System.out.println("to content is: " + dbm.getDbReader().getContent(toId));
//		System.out.println("from: " + fromId + " to: " + toId +" is a snip");
//		return true;
	}
	
	
	
	public boolean snipTest(int startBubble, int endBubble, Set<Integer> set) {
		int current = startBubble + 1;
		while (current != endBubble) {
			if (!set.contains(current)) {
				return false;
			}
			++current;
		}
		return true;
	}
	
	
	public Set<Integer> calculateSnipSegments() {
		//Set<Integer> snipSet = new HashSet<Integer>();
		List<int[]> bubblesList = dbm.getDbReader().getBubbles();
		Set<Integer> set = dbm.getDbReader().fakeTest();
		Set<Integer> set2 = new HashSet<Integer>();
		
		for (int i = 0; i < bubblesList.size(); ++i) {
			//System.out.println(i +"/" + bubblesList.size());
			
			int startBubble = bubblesList.get(i)[0];
			int endBubble = bubblesList.get(i)[1];
			if (endBubble - startBubble > 2) {
				if (snipTest(startBubble, endBubble, set)) {
					int current = startBubble;
					while (current != endBubble +1) {
						set2.add(current);
						++current;
					}
				}

			}
		}
		

		
		System.out.println("DONE SNIP SEGMENTS CALC");
		return set2;
	}
	
	public Group highLightSnips() {
		System.out.println("highLighting snips");
		Group res = new Group();
		ArrayList<ArrayList<Integer>> links = dbm.getDbReader().getLinks(genomeIds);
		ArrayList<ArrayList<Integer>> counts = dbm.getDbReader().getLinkWeights(genomeIds);
		ArrayList<ArrayList<Paint>> colours = calculateColours(links, genomeIds);
		ArrayList<Integer> xcoords = dbm.getDbReader().getAllXCoord();
		ArrayList<Integer> ycoords = dbm.getDbReader().getAllYCoord();
		Set<Integer> snipSet = calculateSnipSegments();
		
		
		for (int fromId = 1; fromId <= links.size(); fromId++) {
			for (int j = 0; j < links.get(fromId - 1).size(); j++) {
				int toId = links.get(fromId - 1).get(j);
				Line line = new Line(xcoords.get(fromId - 1), ycoords.get(fromId - 1), 
						xcoords.get(toId - 1), ycoords.get(toId - 1));
				line.setStrokeWidth(counts.get(fromId - 1).get(j));
				if (snipSet.contains(fromId) && snipSet.contains(toId)) {
					line.setStroke(colours.get(fromId - 1).get(j));
				}
				else
					line.setStroke(NewickColourMatching.getDeactivatedColour());
		        res.getChildren().add(line);
			}
		}
		
		
		System.out.println("Finished snips");
		return res;
		
	}
	
	
	
	
	
	
	
	
	
	public Paint getLineColor(int ff, int tt) {
		Paint color = Paint.valueOf("0xff0000ff");
		ArrayList<String> from = dbm.getDbReader().getGenomesThroughSegment(ff);
		ArrayList<String> to = dbm.getDbReader().getGenomesThroughSegment(tt);
		if (from.size() > to.size()) {
			for (int i = 0; i < to.size(); i++) {
				String genome = to.get(i);
				if (lineages.containsKey(genome) && from.contains(genome) 
						&& !genome.equals("MT_H37RV_BRD_V5.ref")) {
					return NewickColourMatching.getLineageColour(lineages.get(genome));
				}
			}
		} else {
			for (int i = 0; i < from.size(); i++) {
				String genome = from.get(i);
				if (lineages.containsKey(genome) && to.contains(genome) 
						&& !genome.equals("MT_H37RV_BRD_V5.ref")) {
					return NewickColourMatching.getLineageColour(lineages.get(genome));
				}
			}
		}
		return color;
	}
	
	private ArrayList<ArrayList<Paint>> calculateColours(ArrayList<ArrayList<Integer>> linkIds, 
			ArrayList<Integer> genomes) {
		ArrayList<ArrayList<Paint>> colours = 
				new ArrayList<ArrayList<Paint>>();
		for (int i = 0; i < dbm.getDbReader().countSegments(); i++) {
			colours.add(new ArrayList<Paint>());
		}
		ArrayList<String> genomeNames = dbm.getDbReader().getGenomeNames(genomes);
		
		HashMap<Integer, ArrayList<Integer>> hash = dbm.getDbReader().getGenomesPerLink(genomes);
		for (int i = 0; i < linkIds.size(); i++) {
			for (int j = 0; j < linkIds.get(i).size(); j++) {
				ArrayList<Integer> genomeIds = hash.get(100000 * (i + 1) 
						+ linkIds.get(i).get(j));
				int id = genomeIds.get(0);
				Paint colour = Paint.valueOf("0xff0000ff");
				String genome = genomeNames.get(id - 1);
				if (!genome.startsWith("M")) {
					colour = NewickColourMatching
							.getLineageColour(lineages.get(genome));
				} 
				colours.get(i).add(colour);
			}
		}
		return colours;
	}
	
	/**
	 * Creates all paths that make up the collapsed ribbons and returns a
	 * {@link Group} containing those paths.
	 * 
	 * @return A group containing the ribbons.
	 */
	public Group createCollapsedRibbons() {
		System.out.println("Creating collapsed ribbons");
		Group res = new Group();
		ArrayList<ArrayList<Integer>> links = dbm.getDbReader().getLinks(genomeIds);
		ArrayList<ArrayList<Integer>> counts = dbm.getDbReader().getLinkWeights(genomeIds);
		ArrayList<ArrayList<Paint>> colours = calculateColours(links, genomeIds);
		ArrayList<Integer> xcoords = dbm.getDbReader().getAllXCoord();
		ArrayList<Integer> ycoords = dbm.getDbReader().getAllYCoord();
		Queue<int[]> bubbles = new LinkedList<>(dbm.getDbReader().getBubbles(genomeIds));
		
		List<Integer> ignore = new LinkedList<>();
		
		for (int fromId = 1; fromId <= links.size(); fromId++) {
			List<Integer> edges = links.get(fromId - 1);
			
			if (!bubbles.isEmpty() && fromId == bubbles.peek()[0]) {
				int[] bubble = bubbles.poll();
				Line line = new Line(xcoords.get(fromId - 1), ycoords.get(fromId - 1), 
						xcoords.get(bubble[1] - 1), ycoords.get(bubble[1] - 1));
				double width = bubble[2];
				line.setStrokeWidth(2 * width);
				line.setStroke(colours.get(fromId - 1).get(0));
		        res.getChildren().add(line);
		        ignore.addAll(edges);
			} else {
				if (ignore.contains(fromId)) {
					continue;
				}
				for (int toId : edges) {
					if (!bubbles.isEmpty() && toId == bubbles.peek()[1]) {
						break;
					}

					for (int j = 0; j < links.get(fromId - 1).size(); j++) {
						Line line = new Line(xcoords.get(fromId - 1), 
								ycoords.get(fromId - 1), 
								xcoords.get(toId - 1), 
								ycoords.get(toId - 1));
						line.setStrokeWidth(2 * counts.get(fromId - 1).get(j));
						line.setStroke(colours.get(fromId - 1).get(j));
						res.getChildren().add(line);
					}
				}
			}
		}
		System.out.println("Finished collapsed ribbons");
		
		return res;
	}
}