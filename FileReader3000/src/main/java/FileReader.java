import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class FileReader {

	public static void main(String[] args) throws IOException, InterruptedException {
		
		// Create a file object
		String folderpath = "C:\\Users\\bbake\\Desktop\\INBOUND";
		String filecontent = "";
		File folder = new File(folderpath);
		
		// View list of files
		File[] files = folder.listFiles();
		
		// Iterate the files array
		for(File file:files) {
	
			// Check if object is a file
			if(file.isFile()) {
				
				// Convert MilSec from long to Date type.
				DateFormat obj = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss:SSS Z");
				Date modifiedDate = new Date(file.lastModified());
				String formattedModifiedDate = obj.format(modifiedDate);
				
				// Display file metadata: ModifiedDate
				System.out.println("File -> "+file.getName()+" \n Modified Date: " + formattedModifiedDate);
			} else
				if(file.isDirectory()) {
					System.out.println("Folder -> "+file.getName());
				}
			
		}
		
		Path dir = Paths.get(folderpath);
		
		// Create a WatchService instance
		WatchService watcher = FileSystems.getDefault().newWatchService();
		
		// Register the directory to be monitored for new files
		dir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE);
		
		// Use While Loop to continuously check for new events by using the take() method
		while (true) {
			WatchKey key = watcher.take();
			// Check for the type of event that has occurred
			for (WatchEvent<?> event : key.pollEvents()) {
				if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
					Path newFile = (Path) event.context();
					
				// Extract data from the new file
					System.out.println("New file Added: " + newFile);
					Scanner sc = new Scanner((Path) newFile);
					
					while (sc.hasNextLine()) {	
						filecontent = filecontent.concat(sc.nextLine());
						FileWriter writer = new FileWriter("C:\\Users\\bbake\\Desktop\\INBOUND\\TEST1.txt");
						writer.write(filecontent);
						writer.close();
					}
					sc.close();
					
				}		
			}
			key.reset();
		}
		
	}
	


}



/* 






*/
