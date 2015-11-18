package bioinfo;

import java.io.File;
import java.util.LinkedHashMap;

import org.biojava.nbio.core.sequence.ProteinSequence;
import org.biojava.nbio.core.sequence.io.FastaWriterHelper;
import org.biojava.nbio.core.sequence.io.GenbankReaderHelper;

public class GenbankToFASTA {

	public static void transform(String genbankFile, String fastaFile) throws Exception {
		File protFile = new File(genbankFile);

		LinkedHashMap<String, ProteinSequence> protSequences = GenbankReaderHelper
				.readGenbankProteinSequence(protFile);
		
		FastaWriterHelper.writeProteinSequence(new File(fastaFile), protSequences.values());
	}
	
}
