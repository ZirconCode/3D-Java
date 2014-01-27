import java.util.*;
import java.awt.*;

// A Triangle/Face

public class Triangle
{
	
   // Variables
   public Vertex a;
   public Vertex b;
   public Vertex c;
   public Color color;
   private Vector vec;
 //  private Hashtable ht = null;
   
   // Constructors
   public Triangle(Vertex x, Vertex y, Vertex z)
   {
      a = new Vertex(x);
      b = new Vertex(y);
      c = new Vertex(z);

      vec = new Vector(5);

      vec.addElement(a);
      vec.addElement(b);
      vec.addElement(c);
      vec.addElement(a);
      vec.addElement(null);
      
      color = new Color(150,150,150);
   }
   
   public Triangle(Vertex x, Vertex y, Vertex z, Color clr)
   {
      a = new Vertex(x);
      b = new Vertex(y);
      c = new Vertex(z);

      vec = new Vector(5);

      vec.addElement(a);
      vec.addElement(b);
      vec.addElement(c);
      vec.addElement(a);
      vec.addElement(null);
      
      color = clr;
   }

   public Triangle(Triangle triangle)
   {
	   a = new Vertex(triangle.a);
	   b = new Vertex(triangle.b);
	   c = new Vertex(triangle.c);

	   vec = new Vector(5);

	   vec.addElement(a);
	   vec.addElement(b);
	   vec.addElement(c);
	   vec.addElement(a);
	   vec.addElement(null);
	   
	   color = triangle.color;
   }
   
   // Change Color
   public void setColor(Color clr)
   {
	   color = clr;
   }
   
   // Get Color
   public Color getColor()
   {
	   return color;
   }

   // Give List of Vertices
   public Enumeration elements()
   {
      return vec.elements();
   }

   // Clone
   public Triangle Clone()
   {
	   Triangle clone = new Triangle(a,b,c);
	   return clone;
   }
   
   // Transform/Change
   public void transform(Transformation tr)
   {
      tr.transform(a);
      tr.transform(b);
      tr.transform(c);
   }
   
}