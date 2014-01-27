import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Enumeration;
import java.util.Vector;
import java.awt.List;
import java.util.*;


public class Camera {

	// Variables
	World world;
	Vertex Position;
	Vertex Angle;
	
	private double nView;
	private double nPort;
	private Vertex coordsLight;
	private TriangleSort TSort;
	
	private double[][] ZBuffer;
	
	public boolean showWireframe;
	public boolean showVertices;
	public boolean ZBuffering;
	
	public Dimension dim;
	
	public double FieldOfVision;  // Pixels
	public double FogDistance;    // Pixels
	public double FogIntensity;   // Percent 0-100
	
	public double ShadingIntensity; // 1-5
	
	// Constructor
	public Camera(Dimension Dim, World world, boolean useZBuffer) // only World? Scenery?
	{
		this.world = world;
		
		Position    = new Vertex(0,0,0);
		Angle       = new Vertex(0,0,0);
		
		coordsLight = new Vertex(200.0, 200.0, 200.0);
		
		FieldOfVision = 300;
		FogDistance   = 150;
		FogIntensity  = 70;
		
		ShadingIntensity = 2.5;
		
		TSort = new TriangleSort();
		
		dim = Dim;
		
		double a = 5;
		double b = 2;
		System.out.println(a+"-"+b);
		Swap(a,b);
		System.out.println(a+"-"+b);
		
		nPort = -160.0;
		nView = 400.0;
		
		if(useZBuffer) ZBuffering = true;
		if(ZBuffering) ZBuffer = new double[dim.height][dim.width];
	}
	
	// Methods
	
	//Set ZBuffer to -999999999 depth
	public void ClearZBuffer()
	{
		for(int i = 0; i<ZBuffer.length; i++)
			for(int j = 0; j<ZBuffer[0].length; j++)
				ZBuffer[i][j] = -999999999;
	}
	
	// Paint World
	public void paintScene(Graphics gr)
	{
		
	   if(ZBuffering)ClearZBuffer();
		
	   Vector vec = new Vector();

	   Object o = null;

	   Enumeration e = world.triangles();

	   while (e.hasMoreElements())
	    {
	       o = e.nextElement();
	       vec.addElement(o);
	    }

	   Object [] rgo = new Object [ vec.size() ];

	   vec.copyInto(rgo);

	   // if Z-buffering?
	   TSort.sort(rgo);

	   for (int i = 0; i < rgo.length; i++)
	    {
	       o = rgo[i];
		   
	       Triangle ts = new Triangle((Triangle)o);
	       
//	       // Translate to Correct Position
//	       Translation tr = new Translation(Position);
//		   ts.transform(tr);
//	       
//		   // Rotate into Correct Position
//		   double f = Math.PI * (Angle.x / 100.0);
//		   Rotation rot = new Rotation(0, 0, 0, 1.0, 0.0, 0.0, f);
//		   ts.transform(rot);
//		   
//		   f = Math.PI * (Angle.y / 100.0);
//		   rot = new Rotation(0, 0, 0, 0.0, 1.0, 0.0, f);
//		   ts.transform(rot);
//		   
//		   f = Math.PI * (Angle.z / 100.0);
//		   rot = new Rotation(0, 0, 0, 0.0, 0.0, 1.0, f);
//		   ts.transform(rot);
		   
		   // Draw
	       paintTriangle(gr, ts);
	    }
	} 
	
	// Is Visible?
	protected boolean isVisible(Vertex coords)
	{
		boolean b = false;
		if(coords.z < nView + nPort)
		  if(coords.z > -FieldOfVision)b = true;
		return b;
	}
	
    // Is Visible?
	protected boolean isVisible(Triangle t)
	{
	   boolean b = false;
	   if(isVisible(t.a))
		  if(isVisible(t.b))
		  if(isVisible(t.c))
		  b = true;
	   
	   double normal = ((t.b.x-t.a.x)*(t.a.y-t.c.y))-((t.b.y-t.a.y)*(t.a.x-t.c.x));
	   if(normal >= 0) b = false;
	   
	   return b;
	}
	
	// Paint Triangle
	private void paintTriangle(Graphics gr, Triangle ts)
	{
	   //Dimension dim = size();
		   
	   // Transfrom 3D points into 2D Points
	   Vertex coordsA = addPerspective(ts.a);
	   Vertex coordsB = addPerspective(ts.b);
	   Vertex coordsC = addPerspective(ts.c);
	   
	   if(isVisible(new Triangle(coordsA,coordsB,coordsC)))
	   {
	     // Size of Canvas
	     int w = dim.width;
	     int h = dim.height;
  
	     // Polygon Data
	     int [] rgx = new int [4];
	     int [] rgy = new int [4];

	     // Shift X Points into Center of Screen
	     rgx[0] = (int)coordsA.x ;//+ w/2;
	     rgx[1] = (int)coordsB.x ;//+ w/2;
	     rgx[2] = (int)coordsC.x ;//+ w/2;
	     rgx[3] = (int)coordsA.x ;//+ w/2;
	      
	     // Shift Y Points into Center of Screen
	     rgy[0] = (int)coordsA.y ;//+ h/2;
	     rgy[1] = (int)coordsB.y ;//+ h/2;
	     rgy[2] = (int)coordsC.y ;//+ h/2;
	     rgy[3] = (int)coordsA.y ;//+ h/2;
	      
	     // Calculate Light/Color
	     int r = ts.getColor().getRed();
	     int b = ts.getColor().getBlue();
	     int g = ts.getColor().getGreen();
	     
	     Vertex n0 = Algorithms.getCentroid(ts);
	     Vertex n1 = Algorithms.getNormal(ts);
	     Vertex n2 = Algorithms.getLine(n0, coordsLight);
	      
	     float dot1 = (float)Algorithms.dot(n1, n2);
	     float dot = (float)(dot1 / ShadingIntensity);
	      
	     // Calculate Fog
	     if(((coordsA.z+coordsB.z+coordsC.z)/3) > -FogDistance)
	     {
	    	 
	     } 
	     else 
	     {
	    	 double dist = -FogDistance - (coordsA.z+coordsB.z+coordsC.z)/3;
	    	 dot = (float)(dot - dist*FogIntensity/10000);
		 }
	     
	     r = r+(int)(dot*255);
	     b = b+(int)(dot*255);
	     g = g+(int)(dot*255);
	     
	     // Color Check 
	     if(r < 0)   r = 0;
	     if(r > 255) r = 255;
	     if(b < 0)   b = 0;
	     if(b > 255) b = 255;
	     if(g < 0)   g = 0;
	     if(g > 255) g = 255;
	     
    	 Color clr = new Color(r, g, b);
	      
	     // Draw Triangle
	     gr.setColor(clr);
	     DrawTriangle(gr, new Triangle(coordsA,coordsB,coordsC));
	     // Draw Wire-Frame
	     if(showWireframe)
	     {
		     gr.setColor(Color.yellow);
		     gr.drawPolygon(rgx, rgy, 4);
	     }
	     // Draw Vertices
	     if(showVertices)
	     {
		     gr.setColor(Color.red);
		     int VertexSize = 8;
		     gr.fillOval(rnd(coordsA.x)-VertexSize/2, rnd(coordsA.y)-VertexSize/2, VertexSize, VertexSize);
		     gr.fillOval(rnd(coordsB.x)-VertexSize/2, rnd(coordsB.y)-VertexSize/2, VertexSize, VertexSize);
		     gr.fillOval(rnd(coordsC.x)-VertexSize/2, rnd(coordsC.y)-VertexSize/2, VertexSize, VertexSize);
	     }
	   }
	}
	
	public void DrawTriangle(Graphics gr, Triangle ts)
	{	
	  // Sort Them (x1<x2<x3)
      if (ts.a.x > ts.b.x) Swap(ts.a, ts.b);
	  if (ts.a.x > ts.c.x) Swap(ts.a, ts.c);
	  if (ts.b.x > ts.c.x) Swap(ts.b, ts.c);
		  
	  int y1 = 0, y2 = 0;
	  double z1 = 0, z2 = 0;
	  
	  double n1 = 0, n2 = 0, n3 = 0;
	  double m1 = 0, m2 = 0, m3 = 0;
	  double p1 = 0;
	  	  
	  // Precalculate Values
	  n1 = (ts.a.y-ts.c.y)/(ts.a.x-ts.c.x);  // a-c
	  n2 = (ts.a.y-ts.b.y)/(ts.a.x-ts.b.x);  // a-b
	  n3 = (ts.b.y-ts.c.y)/(ts.b.x-ts.c.x);  // b-c
	  
      // Precalculate Values (Z-Buffering)
	  if(ZBuffering)
	  {
		  m1 = (ts.a.z-ts.c.z)/(ts.a.x-ts.c.x);  // a-c
		  m2 = (ts.a.z-ts.b.z)/(ts.a.x-ts.b.x);  // a-b
		  m3 = (ts.b.z-ts.c.z)/(ts.b.x-ts.c.x);  // b-c
	  }
	   
	  // Draw
	  for (int x = Math.round(Math.round(ts.a.x+1)); x < ts.c.x; x++)
	  {
		  // For every line(x)...
		  
		  // a-b
		  if(x < ts.b.x)
		  {
			  // Calculate Start/End of Line
			  y1 = Math.round(Math.round( n1 * (x-ts.a.x) + ts.a.y ));
			  y2 = Math.round(Math.round( n2 * (x-ts.a.x) + ts.a.y ));
			  
			  // Precalculate Values (Z-Buffering)
			  if(ZBuffering)
			  {
				  z1 = m1 * (x-ts.a.x) + ts.a.z ;
				  z2 = m2 * (x-ts.a.x) + ts.a.z ;
				  
				  if(y1-y2 != 0) p1 = (z1-z2)/(y1-y2);  // a-b
				  else p1 = 0;
				  
			  }
			  
			  if(ZBuffering)
			  {
				  if(y1 < y2)y2++;
				  if(y1 > y2)y2--;
			  }
			  
			  // Draw Line		
			  if(ZBuffering)
			  for(int i = y1; i != y2;)
			  {
				  // Calculate Depth
				  double z = p1 * (i-y1) + z1;
					  
				  // Draw Pixel if Depth < Previous Depth
				  if(i>0)if(i<dim.height)
				  if(x>0)if(x<dim.width)
				  if(z>=ZBuffer[i][x])
				  {
					  gr.drawRect(x, i, 1, 1);
					  ZBuffer[i][x] = z;
				  }
				  
				  if(i < y2)i++;
				  if(i > y2)i--;
			  }
			  else
			  {
				  if(y1>=y2) gr.drawLine(x, y1, x, y2);
				  if(y1<=y2) gr.drawLine(x, y2, x, y1);
			  }
			  
		  }
		  
		  // b-c
		  if(x > ts.b.x)
		  {
              // Calculate Start/End of Line
			  y1 = Math.round(Math.round( n1 * (x-ts.a.x) + ts.a.y ));
			  y2 = Math.round(Math.round( n3 * (x-ts.b.x) + ts.b.y ));
			  
              // Precalculate Values (Z-Buffering)
			  if(ZBuffering)
			  {
				  z1 = m1 * (x-ts.a.x) + ts.a.z ;
				  z2 = m3 * (x-ts.b.x) + ts.b.z ;
				  
				  if(y1-y2 != 0) p1 = (z1-z2)/(y1-y2);  // a-b
				  else p1 = 0;
			  }
			  
			  if(ZBuffering)
			  {
				  if(y1 < y2)y2++;
				  if(y1 > y2)y2--;
			  }
			  
              // Draw Line		
			  if(ZBuffering)
			  for(int i = y1; i != y2;)
			  {
				  // Calculate Depth
				  double z = p1 * (i-y1) + z1;
				  //System.out.println(z);
					  
				  // Draw Pixel if Depth < Previous Depth
				  if(i>0)if(i<dim.height)
				  if(x>0)if(x<dim.width)
				  if(z>=ZBuffer[i][x])
				  {
					  gr.drawRect(x, i, 1, 1);
					  ZBuffer[i][x] = z;
				  }
				  
				  if(i < y2)i++;
				  if(i > y2)i--;
			  }
			  else
			  {
				  if(y1>=y2) gr.drawLine(x, y1, x, y2);
				  if(y1<=y2) gr.drawLine(x, y2, x, y1);
			  }
		  }
		  
	  }
	 
	}
	
	// Quick Round
	public int rnd(double n)
	{
		return Math.round(Math.round(n));
	}
	
	// Swap two Vertices
    public void Swap(Vertex a, Vertex b)
    {
    	double x,y,z;
    	
    	x = a.x; 
    	y = a.y; 
    	z = a.z;
    	
    	a.x = b.x;
    	a.y = b.y;
    	a.z = b.z;
    	
    	b.x = x;
    	b.y = y;
    	b.z = z;
    }
    
    // Swap two doubles
    public void Swap(double a, double b)
    {
    	double c = a;
    	
    	a = b;
    	b = c;
    }
    
	// Add Perspective (3D point into 2D point)
	protected Vertex addPerspective(Vertex point)
	{
	   if (point == null) return null;

	   Vertex outcome = new Vertex(point);

	   // Adjust to Camera Angle
	   Translation tr = new Translation(-Position.x,-Position.y,-Position.z);
	   tr.transform(outcome);
	   
//	   double x = Math.PI * (Angle.x / 100.0);
//	   double y = Math.PI * (Angle.y / 100.0);
//	   double z = Math.PI * (Angle.z / 100.0);
//	   Rotation rot = new Rotation(0, 0, 0, x, y, z, 1.0);
//	   rot.transform(outcome);
	   
// + Error - Error - Error - Error - Error - Error - Error - Error - Error - Error +
	//   outcome.x = Math.cos(-Angle.y)*Math.cos(-Angle.z)*(point.x-Position.x)+Math.sin(-Angle.z)*Math.cos(Angle.y)*(point.y-Position.y)+Math.sin(-Angle.y)*(point.z-Position.z);
	   //outcome.x = Math.cos(Angle.y)*(Math.sin(Angle.z)*(point.y-Position.y)+Math.cos(Angle.z)*(point.x-Position.x))-Math.sin(Angle.y*(point.z-Position.z));
	//   outcome.y = Math.sin(Angle.x)*(Math.cos(Angle.y)*(point.z-Position.z)+Math.sin(Angle.y)*(Math.sin(Angle.z)*(point.y-Position.y)+Math.cos(Angle.z)*(point.x-Position.x)))+Math.cos(Angle.x)*(Math.cos(Angle.z)*(point.y-Position.y)-Math.sin(Angle.z)*(point.x-Position.x));
	   //outcome.y = Math.sin(Angle.x)*(Math.cos(Angle.y)*(point.z-Position.z)+Math.sin(Angle.y)*(Math.sin(Angle.z)*(point.y-Position.y)+Math.cos(Angle.z)*(point.x-Position.x)))+Math.cos(Angle.x)*(Math.cos(Angle.z)*(point.y-Position.y)-Math.sin(Angle.z)*(point.x-Position.x));
	//   outcome.z = ((Math.cos(-Angle.x)*Math.sin(-Angle.y)*Math.cos(-Angle.z)+Math.sin(-Angle.x)*Math.sin(-Angle.z))*(point.x-Position.x)) + ((Math.cos(-Angle.x)*Math.sin(-Angle.y)*Math.sin(-Angle.z)-Math.sin(-Angle.x)*Math.cos(-Angle.z))*(point.y-Position.y)) + (Math.cos(-Angle.x)*Math.cos(-Angle.y)*(point.z-Position.z));
	   //outcome.z = Math.cos(Angle.x)*(Math.cos(Angle.y)*(point.z-Position.z)+Math.sin(Angle.y)*(Math.sin(Angle.z)*(point.y-Position.y)+Math.cos(Angle.z)*(point.x-Position.x)))-Math.sin(Angle.x)*(Math.cos(Angle.z)*(point.y-Position.y)-Math.sin(Angle.z)*(point.x-Position.x));
// + Error - Error - Error - Error - Error - Error - Error - Error - Error - Error +
	   
	   outcome.x = outcome.x * Math.abs(nView / (nView + nPort - outcome.z));
	   outcome.y = outcome.y * Math.abs(nView / (nView + nPort - outcome.z));
	   
	   outcome.x = outcome.x+dim.height/2;
	   outcome.y = outcome.y+dim.width /2;
	   
	   return outcome;
	}
	
	// Simple Rotate
	public void rotateX(double f)
	{
		Translation tr = new Translation(f, 0.0, 0.0);
		tr.transform(Angle);
	}
	   
	public void rotateY(double f)
	{
		Translation tr = new Translation(0.0, f, 0.0);
		tr.transform(Angle);
	}

	public void rotateZ(double f)
	{
		Translation tr = new Translation(0.0, 0.0, f);
		tr.transform(Angle);
	}

	// Simple Translate
	public void translateX(double f)
	{
	   Translation tr = new Translation(f, 0.0, 0.0);
	   tr.transform(Position);
	}

    public void translateY(double f)
	{
	   Translation tr = new Translation(0.0, f, 0.0);
	   tr.transform(Position);
	}

	public void translateZ(double f)
	{
	   Translation tr = new Translation(0.0, 0.0, f);
	   tr.transform(Position);
	}	
}
