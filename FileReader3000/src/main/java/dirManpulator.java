import java.io.*;
import java.nio.file.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import org.apache.commons.csv.*;

public class dirManpulator {
	
	private static String folderPath;
	
	public static void setFolderPath(String folderPath) {
		dirManpulator.folderPath = folderPath;
	}
	
	public static String getFolderPath() {
		return folderPath;
	}
	
	public static void viewDirContentList(String folderpath) {

	// Create a file object
	
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
				System.out.println("Folder -> " + file.getName());
			}
		}
	}
	
	public static void extractIncomingFile(String folderpath) throws InterruptedException, IOException {

		Path dir = Paths.get(folderpath);
		
		// Create a WatchService instance
		try (
		WatchService watcher = FileSystems.getDefault().newWatchService()){
		
		// Register the directory to be monitored for new files
		final WatchKey watchKey = dir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE);
		
		// Use While Loop to continuously check for new events by using the take() method
		while (true) {
			final WatchKey key = watcher.take();
			
			// Check for the type of event that has occurred
			for (WatchEvent<?> event : key.pollEvents()) {
				final Path newFile = (Path) event.context();
				System.out.println("New file Added: " + newFile);
					
					// Extract data from the new file
					if(newFile.toString().endsWith(".csv")) {
						System.out.println("Extraction in Progress...");
						try {
						//Parse the new CSV file
						File csvData = new File(dir + "/" + newFile);
							if(!csvData.exists()) {
								System.out.println("File not Found");
								return;
							}
							if(!csvData.getName().endsWith(".csv")) {
								System.out.println("File not csv");
								return;
							}
							if(csvData.length()==0) {
								System.out.println("File empty");
								return;
							}
			
						Reader in = new FileReader(csvData);
						Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);
						
						for (CSVRecord csvRecord : records) {
							
							// Accessing Values by column index
							String FirstName = csvRecord.get(0);
							if(FirstName == null || FirstName.isEmpty()) {
								FirstName = null;
							}
							String LastName = csvRecord.get(1);	
							if(LastName == null || LastName.isEmpty()) {
								LastName = null;
							}
							String Age	 = csvRecord.get(2);
							if(Age == null || Age.isEmpty()) {
								Age = null;
							}
							
							//Reformat Print statement to use printf
							System.out.println("FirstName : " + FirstName);
							System.out.println("LastName : " + LastName);
							System.out.println("Age : " + Age);
							} 
						in.close();	
						} catch (IOException e) {
							System.out.println("An error occured while parsing the CSV file: " + e.getMessage());
						}
					}
			key.reset();
				}		
			
		}
		
	}
	}
}
