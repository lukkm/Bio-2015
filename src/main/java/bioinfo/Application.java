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
					break;
				case 4:
					break;
				case 5:
					break;
			}
		}
		

	}

}
