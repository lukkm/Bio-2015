package bioinfo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class BlastParser {
    private static final String HIT_REGEX_LEFT = ">[^>BLAST]+[\\|][^>]*";
    private static final String HIT_REGEX_RIGHT = "[^>]*";

    public static void parseHits(String inputFilePath, String regex, String out)
            throws FileNotFoundException, IOException {
        String fileContents = new String(
                Files.readAllBytes(Paths.get(inputFilePath)));

        Pattern pattern = Pattern
                .compile(HIT_REGEX_LEFT + regex + HIT_REGEX_RIGHT);
        Matcher matcher = pattern.matcher(fileContents);

        List<String> hits = new ArrayList<String>();
        while (matcher.find()) {
            String hit = matcher.group();
            hits.add(hit);
        }

        if (hits.isEmpty()) {
            System.out.println("No se encontraron hits.");
            return;
        }

        System.out.println("Resultados: " + hits.size() + " hits");
        String outputFilePath = out + ".out";

        PrintWriter writer = new PrintWriter(outputFilePath, "UTF-8");
        for (String hit : hits) {
            writer.write(hit);
        }
        writer.close();

        System.out.println("Resultados guradados en :" + outputFilePath);
    }
}
