import java.io.*;
import java.util.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;

class Main
{
	public static void main(String arg[])
	{
		Scanner sobj = new Scanner(System.in);
		System.out.println("---------------------");
		System.out.println("Packer & Unpacker");

		while(true)
		{   System.out.println("---------------------");
			System.out.println("Enter your choice");
			System.out.println("1 : Packing");
			System.out.println("2 : Unpacking");
			System.out.println("3 : Exit");
			System.out.println("---------------------");

			String Dir,Filename;
			int choice = 0;
			choice = sobj.nextInt();

			switch(choice)
			{
				case 1:
					System.out.println("Enter Directory name");
					Dir = sobj.next();

					System.out.println("Enter the file name for packing");
					Filename = sobj.next();

					Packer pobj = new Packer(Dir,Filename);

				break;

				case 2:
				System.out.println("Enter Packed filename");
				String name = sobj.next();
				
				Unpacker obj = new Unpacker(name);
		
					break;

				case 3:
				    	System.out.println("---------------------");
					System.out.println("Thank you for using Packer Unpacker");
						System.out.println("---------------------");
					System.exit(0);
					break;

				default:
					System.out.println("Wrong choice");
				break;
			
			}
		}

	}
}

class Packer
{   
	// for file writing
	public FileOutputStream outstream = null; 
	
	public Packer(String FolderName, String FileName)
	{
		try
		{
		
		System.out.println("Inside Packer Class");
		
		File outfile = new File(FileName); // will create new file for packing
		outstream = new FileOutputStream(FileName);
		
		// Set the curr. working dir.
		//System.setProperty("user.dir", FolderName);
		
		TravelDirectory(FolderName);
		}
		catch(Exception obj)
		{
			System.out.println(obj);
		}
	}
	
	public void TravelDirectory(String path)
	{
	File directoryPath = new File(path);
	int counter = 0;
	// Get all File names from dirr.
    
	File arr[] = directoryPath.listFiles(); 
	
	System.out.println("---------------------");
     for(File filename : arr)
 {
	

	
	if(filename.getName().endsWith(".txt") || filename.getName().endsWith(".docx"))
	{
		counter++;
	System.out.println("Packed File :"+filename.getName());
		System.out.println(filename.getAbsolutePath());
	
	PackFile(filename.getAbsolutePath());
	}
}
	
System.out.println("---------------------");
System.out.println("Successfully Packed Files :"+counter);
System.out.println("---------------------");
}


public void PackFile(String FilePath)
	{
//		System.out.println("File name received "+ FilePath);
		byte Header[] = new byte[100];
		byte Buffer[] = new byte[1024];
		int length = 0;

		FileInputStream istream = null;

		File fobj = new File(FilePath);

		String temp = FilePath+" "+fobj.length();
		
		// Create header of 100 bytes
		for(int i = temp.length(); i< 100; i++)
		{
			temp = temp + " ";
		}		
		Header = temp.getBytes();
		
		try
		{
			// open the file for reading
			istream = new FileInputStream(FilePath);

			outstream.write(Header,0,Header.length);
			while((length = istream.read(Buffer)) > 0)
			{
				outstream.write(Buffer,0,length);
				
				//System.out.println("Header : "+temp.length());
			}
             
			istream.close();
		}
		catch(Exception obj)
		{

		}
		
	}
}

// UN-Packing --------
class Unpacker
{
	public FileOutputStream outstream = null;
	
	public Unpacker(String src)
	{
		
		unpackFile(src);
	}

	
	public void unpackFile(String FilePath)
	{
		try
		{
			System.out.println("Inside Unpacker Class");
			FileInputStream instream = new FileInputStream(FilePath);
			
			byte Header[] = new byte[100];
			int length = 0;
			int counter = 0;
			
			while((length = instream.read(Header,0,100))>0)
			{
				String str = new String(Header);
				String ext = str.substring(str.lastIndexOf("\\"));
				ext = ext.substring(1);
				
				String words[] = ext.split("\\s");
				String name = words[0];
				int size = Integer.parseInt(words[1]);
				
				byte arr[] = new byte[size];
				instream.read(arr,0,size);
				
				System.out.println("New File gets created as:"+name);
				
				FileOutputStream fout = new FileOutputStream(name);
				fout.write(arr,0,size);
				counter++;
			}
			
			System.out.println("File Successfully Unpacked!!"+counter);
		}
		catch(Exception obj)
		{

		}
	}
}