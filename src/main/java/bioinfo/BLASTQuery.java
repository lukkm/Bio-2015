package bioinfo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.biojava.nbio.core.sequence.io.util.IOUtils;
import org.biojava.nbio.ws.alignment.qblast.*;

public class BLASTQuery {

	public static void main(String[] args) {
		NCBIQBlastService service = new NCBIQBlastService();

		NCBIQBlastAlignmentProperties props = new NCBIQBlastAlignmentProperties();
		props.setBlastProgram(BlastProgramEnum.blastx);
		props.setBlastDatabase("nr");

		NCBIQBlastOutputProperties outputProps = new NCBIQBlastOutputProperties();

		String rid = null;
		FileWriter writer = null;
		BufferedReader reader = null;
		
		String filename = "target/BLASTOutput.xml";
		String accession = "NM_000518.4";
		
		try {
			rid = service.sendAlignmentRequest(accession, props);
			while (!service.isReady(rid)) {
				System.out.println("Esperando 5 segundos");
				Thread.sleep(5000);
			}

			InputStream in = service.getAlignmentResults(rid, outputProps);
			reader = new BufferedReader(new InputStreamReader(in));

			File f = new File(filename);
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
