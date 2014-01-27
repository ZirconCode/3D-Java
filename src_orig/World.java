
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.*;

public class World {

	// Variables
	private Vector v;
	private double nView =  400.0;
	private double nPort = -160.0;
	private Vertex coordsLight;
	private Vertex Center;
	private TriangleSort TSort = new TriangleSort();
	public Dimension dim;
	
	// Constructor
	public World()
	{
       v           = new Vector();
       Center      = new Vertex(0.0,0.0,0.0);
       coordsLight = new Vertex(200.0, 200.0, 200.0);
	}
	
	// Methods
	   
	// Add Scenery/Object
	public void add(Object o)
	{
	   v.addElement(o);
    }

	// Remove Scenery/Object
	public void remove(Object o)
	{
	   v.removeElement(o);
	}

	// Set Light Source
	public void setLight(Vertex point)
	{
	   coordsLight = point;
	}
	   
	// Set Center
	public void setCenter(Vertex point)
	{
	   Center = point;
	}
	
//	   // Calculates Center
//	   public void calculateCenter()
//	   {
//		   Vertex min = new Vertex();
//		   Vertex max = new Vertex();
//		   
//		   Object o  = null;
//		   Object o2 = null;
//		   Enumeration e = v.elements();
//		   
//		   min.x = ((Triangle)v.firstElement()).a.x;
//		   min.y = ((Triangle)v.firstElement()).a.y;
//		   min.z = ((Triangle)v.firstElement()).a.z;
//		   max.x = ((Triangle)v.firstElement()).a.x;
//		   max.y = ((Triangle)v.firstElement()).a.y;
//		   max.z = ((Triangle)v.firstElement()).a.z;
//		   
//		   while (e.hasMoreElements())
//		   {
//		      o = e.nextElement(); 
//		      if (o instanceof Triangle)
//		      {
//		         Enumeration e2 = ((Triangle)o).elements();
//		         while (e2.hasMoreElements())
//		         {
//		        	 o2 = e2.nextElement();
//		        	 if (o2 instanceof Vertex)
//		        	 {
//		        		 if(((Vertex)o2).x > max.x) 
//		        			 max.x = ((Vertex)o2).x;
//		        		 if(((Vertex)o2).y > max.y) 
//		        			 max.y = ((Vertex)o2).y;
//		        		 if(((Vertex)o2).z > max.z) 
//		        			 max.z = ((Vertex)o2).z;
//		        		 if(((Vertex)o2).x < min.x) 
//		        			 min.x = ((Vertex)o2).x;
//		        		 if(((Vertex)o2).y < min.y) 
//		        			 min.y = ((Vertex)o2).y;
//		        		 if(((Vertex)o2).z < min.z) 
//		        			 min.z = ((Vertex)o2).z;
//		        	 }
//		         }
//		      }
//		   }
//		   
//		   Center = new Vertex
//		       ((min.x+max.x)/2,(min.y+max.y)/2,(min.z+max.z)/2);
//	       System.out.println(Center);
//	   }
	   
	// Present Scenery/Objects
	public Enumeration elements()
	{
	   return v.elements();
	}
	
	// Present Triangles/Objects
	public Enumeration triangles()
	{
		Vector vec = new Vector();

		Object o = null;

		Enumeration e = v.elements();
	    Enumeration f = v.elements();

		while (e.hasMoreElements())
		{
		   o = e.nextElement();

		   if (o instanceof Scenery)
		   {
		     f = ((Scenery)o).elements();
		     while (f.hasMoreElements())
		  	    {
		  	       o = f.nextElement();

		  	       if (o instanceof Triangle)
		  	        {
		  	    	   vec.add(o);
		  	        }
		  	    }
		    }
	   }
	   return vec.elements();
	}

	// Apply Tranformation to All Triangles
	public void transform(Transformation tr)
	{
		   Object o = null;

		   Enumeration e = v.elements();
		   Enumeration f = v.elements();

		   while (e.hasMoreElements())
		   {
		      o = e.nextElement();

		      if (o instanceof Scenery)
		      {
		    	  f = ((Scenery)o).elements();
		    	  while (f.hasMoreElements())
				   {
				      o = f.nextElement();

				      if (o instanceof Triangle)
				      {
				          ((Triangle)o).transform(tr);
				      }
				   }
		      }
		   }
	}
	
	// Paint World
	public void paintWorld(Graphics gr)
	{
	   Vector vec = new Vector();

	   Object o = null;

	   Enumeration e = v.elements();
	   Enumeration f = v.elements();

	   while (e.hasMoreElements())
	   {
	      o = e.nextElement();

	      if (o instanceof Scenery)
	      {
	    	 f = ((Scenery)o).elements();
	    	 while (f.hasMoreElements())
	  	      {
	  	         o = f.nextElement();

	  	         if (o instanceof Triangle)
	  	         {
	  	    	    vec.add(o);
	  	         }
	  	      }
	      }
	   }

	   Object [] rgo = new Object [ vec.size() ];

	   vec.copyInto(rgo);

	   TSort.sort(rgo);

	   for (int i = 0; i < rgo.length; i++)
	   {
	      o = rgo[i];

	      Triangle ts = (Triangle)o;

	      paintTriangle(gr, ts);
	   }
	}  
	   
	// Paint Triangle
	private void paintTriangle(Graphics gr, Triangle ts)
	{
	   //Dimension dim = size();
		   
	   // Transfrom 3D points into 2D Points
	   Vertex coordsA = addPerspective(ts.a);
	   Vertex coordsB = addPerspective(ts.b);
	   Vertex coordsC = addPerspective(ts.c);
	      
	   // Size of Canvas
	   int w = dim.width;
	   int h = dim.height;

	   // Polygon Data
	   int [] rgx = new int [4];
	   int [] rgy = new int [4];

	   // Shift X Points into Center of Screen
	   rgx[0] = (int)coordsA.x + w/2;
	   rgx[1] = (int)coordsB.x + w/2;
	   rgx[2] = (int)coordsC.x + w/2;
	   rgx[3] = (int)coordsA.x + w/2;
	      
	   // Shift Y Points into Center of Screen
	   rgy[0] = (int)coordsA.y + h/2;
	   rgy[1] = (int)coordsB.y + h/2;
	   rgy[2] = (int)coordsC.y + h/2;
	   rgy[3] = (int)coordsA.y + h/2;
	      
	   // Calculate Light/Color
	   Vertex n0 = Algorithms.getCentroid(ts);
	   Vertex n1 = Algorithms.getNormal(ts);
	   Vertex n2 = Algorithms.getLine(n0, coordsLight);
	      
	   float dot1 = (float)Algorithms.dot(n1, n2);
	   float dot = (float)(dot1 / 2.0 + 0.5);
	      
	   // Dot = Percentage in Decimals?
	      
	   Color clr = new Color(dot, dot, dot);
	      
	   // Draw Triangle
	   gr.setColor(clr);
	   gr.fillPolygon(rgx, rgy, 4);
	   gr.setColor(Color.white);
	   gr.drawPolygon(rgx, rgy, 4);
	      
	}
	   
	// Add Perspective (3D point into 2D point)
	protected Vertex addPerspective(Vertex point)
	{
	   if (point == null) return null;

	   point = new Vertex(point);

	   point.x = point.x * Math.abs(nView / (nView + nPort - point.z));
	   point.y = point.y * Math.abs(nView / (nView + nPort - point.z));

	   return point;
	}
	
	// Simple Rotate
	public void rotateX(double f)
	{
	   f = Math.PI * (f / 100.0);
	   Rotation rot = new Rotation(Center.x, Center.y, Center.z, 1.0, 0.0, 0.0, f);
	   transform(rot);
	}
	   
	public void rotateY(double f)
	{
	   f = Math.PI * (f / 100.0);
	   Rotation rot = new Rotation(Center.x, Center.y, Center.z, 0.0, 1.0, 0.0, f);
	   transform(rot);
	}

	public void rotateZ(double f)
	{
	   f = Math.PI * (f / 100.0);
	   Rotation rot = new Rotation(Center.x, Center.y, Center.z, 0.0, 0.0, 1.0, f);
	   transform(rot);
	}

	// Simple Translate
	public void translateX(double f)
	{
	   Translation tr = new Translation(f, 0.0, 0.0);
	   transform(tr);
	}

    public void translateY(double f)
	{
	   Translation tr = new Translation(0.0, f, 0.0);
	   transform(tr);
	}

	public void translateZ(double f)
	{
	   Translation tr = new Translation(0.0, 0.0, f);
	   transform(tr);
	}
	   
	// Adjust View
	public void setView(double f)
	{
	   nView = f;
	}

	// Adjust Port
	public void setPort(double f)
	{
	   nPort = f;
	}
	
}
