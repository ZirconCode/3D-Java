import java.io.*;
import java.util.Vector;

import javax.imageio.ImageIO; 
import java.awt.*;  
import java.awt.image.*; 

public class Loader {
	
	// Methods
	
	// Load Object (Milkshape 3D - Text)
	public static Scenery LoadObject(String path, boolean color)
	{
		List text      = new List();
		text           = loadText(path);
		
		Vector v       = new Vector();
		String data    = new String("...");
		String mode    = new String("Vertex");
		Scenery Output = new Scenery();
		
		int i = 2;
		
		while(data != "")
		{
			 data = text.getItem(i);
			 
			 if(data.split(" ").length == 3)
			 {
			     if(mode == "Face") v.clear();
				 
				 v.add(new Vertex(Double.parseDouble(data.split(" ")[0]),
						          Double.parseDouble(data.split(" ")[1]),
						          Double.parseDouble(data.split(" ")[2]) ));
			     System.out.println(v.lastElement());
			     
			     mode = "Vertex";
			 }
			 
			 if(!color)
			 if(data.split(" ").length == 19)
			 {
				 Output.add(new Triangle(
						   (Vertex)v.elementAt(Integer.parseInt(data.split(" ")[1])),
						   (Vertex)v.elementAt(Integer.parseInt(data.split(" ")[2])),
						   (Vertex)v.elementAt(Integer.parseInt(data.split(" ")[3]))
						                                   ));
				 System.out.println(" ");
			     mode = "Face";
			 }
			 
			 i++;
			 if(i >= text.getItemCount()) data = "";
		}
		
		return Output;
	}
	
	// Load Text
	public static List loadText(String path)
	{
	   List text = new List();
	   try {
	   	      BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
	   	      String input;
		 
		      while( (input = br.readLine()) != null ) 
		        {
		          text.add(input);
		        }
		 
		   }  
	
	   catch (FileNotFoundException fnfe) {fnfe.printStackTrace();}
       catch (IOException ioe) {ioe.printStackTrace();}
	
	   return text;
	}
	
	// Load Image
	public static BufferedImage loadImage(String path)
	{ 
	   BufferedImage picture = null;
	   File file = new File(path); 
	   try { 
	         picture = ImageIO.read(file);
	       } 
	   catch (IOException e) 
	       { 
	         e.printStackTrace(); 
	       } 
	   return picture;
	} 
	
}
