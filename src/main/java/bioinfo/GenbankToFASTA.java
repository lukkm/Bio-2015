package bioinfo;

import org.biojava.nbio.core.sequence.AccessionID;
import org.biojava.nbio.core.sequence.DNASequence;
import org.biojava.nbio.core.sequence.ProteinSequence;
import org.biojava.nbio.core.sequence.RNASequence;
import org.biojava.nbio.core.sequence.io.FastaWriterHelper;
import org.biojava.nbio.core.sequence.io.GenbankReaderHelper;
import org.biojava.nbio.core.sequence.transcription.Frame;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class GenbankToFASTA {

    public static void transform(String genbankFile, String fastaFile) throws Exception {
        File nucleotidsFile = new File(genbankFile);


        LinkedHashMap<String, DNASequence> dnaSequences = GenbankReaderHelper.readGenbankDNASequence(nucleotidsFile, false);

        for (Map.Entry<String, DNASequence> entry : dnaSequences
                .entrySet()) {
            DNASequence nucleotideSequence = entry.getValue();
            List<ProteinSequence> seq = new LinkedList<ProteinSequence>();
            int i = 1;
            for (Frame frame : Frame.getAllFrames()) {
                RNASequence rnaSequence = nucleotideSequence.getRNASequence(frame);
                ProteinSequence proteinSequence = rnaSequence.getProteinSequence();
                proteinSequence.setAccession(new AccessionID(nucleotideSequence.getAccession().toString() + "_" + i++));
                seq.add(proteinSequence);
            }
            File outputFile = new File(fastaFile + ".fas");
            FastaWriterHelper.writeProteinSequence(outputFile, seq);
        }
    }
}
