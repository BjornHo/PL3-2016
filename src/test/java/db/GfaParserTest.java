//package db;
//
//import static org.junit.Assert.assertEquals;
//
//import org.junit.Before;
//import org.junit.Test;
//
///**
// * @author hugokooijman
// *
// * Test if parser reads correct content and deals with errors correctly.
// */
//public class GfaParserTest {
//	
//	private static DatabaseManager dbm;
//	private GfaParser parser;
//	
//	/**
//	 * Locations of test files.
//	 */
//	private String filename = "testread";
//	private String gfaPath = System.getProperty("user.dir") + "/Data/TestData/" + filename
//			+ ".gfa";
//	private String dbPath = System.getProperty("user.dir") + "/db/" + filename;
//	
//	
//	@Before
//	public void before() {
//		dbm = new DatabaseManager(dbPath);
//		parser = new GfaParser(dbm);
//	}
//	
//	@Test
//	public void ParsedGenomeSizeTest() throws GfaException {
//		parser.parse(gfaPath);
//		assertEquals(parser.getGenomes().size(), 4);
//	}
//	
//	@Test
//	public void parsedContentTest() throws GfaException {
//		parser.parse(gfaPath);
//		assertEquals(parser.getGenomes().toString(), "{seq3.fasta=3, seq1.fasta=1, seq4.fasta=4, seq2.fasta=2}");
//	}
//
//	/**
//	 * GfaException is thrown when the .gfa file does not have the correct format.
//	 * @throws GfaException
//	 */
//	@Test(expected=GfaException.class)
//	public void gfaExceptionTest() throws GfaException {
//		gfaPath = System.getProperty("user.dir") + "/Data/TestData/" + "testreaderror"
//				+ ".gfa";
//		parser.parse(gfaPath);
//	}
//}
