import java.io.IOException;


public class FileReader3005 {

	public static void main(String[] args) throws IOException, InterruptedException {
	
		dirManpulator.setFolderPath("C:\\Users\\bbake\\Desktop\\INBOUND");
		dirManpulator.viewDirContentList(dirManpulator.getFolderPath());
		dirManpulator.extractIncomingFile(dirManpulator.getFolderPath());
	}
}
		

