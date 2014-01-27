import java.util.*;

// Rotates a Vertex by a certain Degree

public class Rotation extends Transformation
{
	
   // Variable
   private double m[][];
   private Vertex center; 

   // Constructor
   public Rotation(double x, double y, double z,
                   double a, double b, double c,
                   double r)
   {
	   
	  center = new Vertex(x,y,z); 
	  
	  double sin_r = Math.sin(r);
      double cos_r = Math.cos(r);

      double i = 1.0 - cos_r;

      double a_a_i = a * a * i;
      double b_b_i = b * b * i;
      double c_c_i = c * c * i;
      double a_b_i = a * b * i;
      double a_c_i = a * c * i;
      double b_c_i = b * c * i;

      double a_sin_r = a * sin_r;
      double b_sin_r = b * sin_r;
      double c_sin_r = c * sin_r;

      m = new double [3][3];

      m[0][0] = a_a_i + cos_r;
      m[1][0] = a_b_i - c_sin_r;
      m[2][0] = a_c_i + b_sin_r;

      m[0][1] = a_b_i + c_sin_r;
      m[1][1] = b_b_i + cos_r;
      m[2][1] = b_c_i - a_sin_r;

      m[0][2] = a_c_i - b_sin_r;
      m[1][2] = b_c_i + a_sin_r;
      m[2][2] = c_c_i + cos_r;
   }

   // Rotates a Vertex
   public void transform(Vertex point)
   {
      if (point == null) return;

      // Translate (Center = 0,0,0)
      double x = point.x-center.x;
      double y = point.y-center.y;
      double z = point.z-center.z;
      
      // Perform Rotation
      double x2 = x * m[0][0] + y * m[0][1] + z * m[0][2];
      double y2 = x * m[1][0] + y * m[1][1] + z * m[1][2];
      double z2 = x * m[2][0] + y * m[2][1] + z * m[2][2];

      // Translate Back
      x2 = x2+center.x;
      y2 = y2+center.y;
      z2 = z2+center.z;
      
      // Show Results
      point.x = x2;
      point.y = y2;
      point.z = z2;
   }
   
}
