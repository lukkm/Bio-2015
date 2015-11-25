package bioinfo;


public class Application {

	public static void main(String[] args) throws Exception {
		if (args.length >= 1) {
			int query = Integer.valueOf(args[0]);
			switch (query) {
				case 1:
					if (args.length >= 3) {
						GenbankToFASTA.transform(args[1], args[2]);
					}
					break;
				case 2:
					if (args.length >= 3) {
						BLASTQuery.transform(args[1], args[2]);
					}
					break;
				case 3:
					if (args.length >= 3) {
						String reg = "";
						String out = args[2];
						if (args.length > 3) {
							reg = args[2];
							out = args[3];
						}
						BlastParser.parseHits(args[1], reg, out);
					}
					break;
				case 4:
					break;
				case 5:
					break;
			}
		}
		

	}

}
