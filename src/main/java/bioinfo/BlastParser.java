package bioinfo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class BlastParser {
    private static final String HIT_REGEX_LEFT = ">[^>BLAST]+[\\|][^>]*";
    private static final String HIT_REGEX_RIGHT = "[^>]*";

    public static List<String> parseHits(String inputFilePath, String regex)
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

        return hits;
    }
    public static void main(String[] args) throws IOException {
        System.out.println(parseHits("BLASTOutput.out", ""));
    }
}
