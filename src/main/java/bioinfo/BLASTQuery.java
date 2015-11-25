package bioinfo;

import org.biojava.nbio.core.sequence.ProteinSequence;
import org.biojava.nbio.core.sequence.io.FastaReaderHelper;
import org.biojava.nbio.core.sequence.io.util.IOUtils;
import org.biojava.nbio.ws.alignment.qblast.*;

import java.io.*;
import java.util.Map;

public class BLASTQuery {

	public static void transform(String fasta, String output) throws IOException {
		NCBIQBlastService service = new NCBIQBlastService();

		NCBIQBlastAlignmentProperties props = new NCBIQBlastAlignmentProperties();
		props.setBlastProgram(BlastProgramEnum.blastx);
		props.setBlastDatabase("nr");

		NCBIQBlastOutputProperties outputProps = new NCBIQBlastOutputProperties();
		outputProps.setOutputFormat(BlastOutputFormatEnum.Text);

		String rid = null;
		FileWriter writer = null;
		BufferedReader reader = null;

		File file = new File(fasta);

		Map<String, ProteinSequence> proteinSequences = FastaReaderHelper.readFastaProteinSequence(file);
		for (ProteinSequence seq : proteinSequences.values()) {
			try {
				rid = service.sendAlignmentRequest(seq.getSequenceAsString(), props);

				while (!service.isReady(rid)) {
					System.out.println("Esperando 5 segundos");
					Thread.sleep(5000);
				}

				InputStream in = service.getAlignmentResults(rid, outputProps);
				reader = new BufferedReader(new InputStreamReader(in));

				File f = new File(seq.getAccession().toString().split(" ")[0] + "-" + output + ".out");
				System.out.println("Guardando resultados a: " + f.getAbsolutePath());
				writer = new FileWriter(f);

				String line;
				while ((line = reader.readLine()) != null) {
					writer.write(line + System.getProperty("line.separator"));
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			} finally {
				IOUtils.close(writer);
				IOUtils.close(reader);
				service.sendDeleteRequest(rid);
			}
		}

	}
}
