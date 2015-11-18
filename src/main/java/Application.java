import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.NoSuchElementException;

import org.biojava.bio.BioException;
import org.biojava.bio.seq.Sequence;
import org.biojava.bio.seq.SequenceIterator;
import org.biojava.bio.seq.io.SeqIOTools;

public class Application {

	public static void main(String[] args) {
		BufferedReader br = null;
		
	    try {
	      // Create a buffered reader to read the sequence file specified by args[0]
	      br = new BufferedReader(new FileReader(args[0]));
	 
	    }
	    catch (FileNotFoundException ex) {
	      // Can't find the file specified by args[0]
	      ex.printStackTrace();
	      System.exit(-1);
	    }
	 
	    //read the GenBank File
	    SequenceIterator sequences = SeqIOTools.readGenbank(br);
	 
	    //iterate through the sequences
	    while(sequences.hasNext()){
	      try {
	        Sequence seq = sequences.nextSequence();
	        System.out.println("Seq");
	        System.out.println(seq.seqString());
	      }
	      catch (BioException ex) {
	        // Not in GenBank format
	        ex.printStackTrace();
	      }catch (NoSuchElementException ex) {
	        // Request for more sequence when there isn't any
	        ex.printStackTrace();
	      }
	    }

	}
	
}
