
// A 3D Point in Space

public class Vertex
{
	
   // Variables
   public double x;
   public double y;
   public double z;

   // Constructor
   public Vertex()
   {
      x = 0.0;
      y = 0.0;
      z = 0.0;
   }

   // Constructor
   public Vertex(Vertex point)
   {
      x = point.x;
      y = point.y;
      z = point.z;
   }

   // Constructor
   public Vertex(double a, double b, double c)
   {
      x = a;
      y = b;
      z = c;
   }

   //
   public double length()
   {
      return Math.sqrt(x * x + y * y + z * z);
   }

   //
   public Vertex unitVector()
   {
      double l = length();

      return new Vertex(x / l, y / l, z / l);
   }

   // Points to String
   public String toString()
   {
      return (double)x+","+(double)y+","+(double)z;
   }
   
   // Points from String
   public void fromString(String s)
   {
      //x = s.split(",")[0];
   }
   
}