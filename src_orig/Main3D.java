import java.applet.*; 
import java.awt.*; 
import java.awt.List;
import java.awt.event.*; 
import java.util.*; 
  
public class Main3D extends Applet 
implements MouseMotionListener,MouseListener,Runnable

{
	// Applet Variables
	boolean running;
	boolean Down;
	int mx, my;
	Thread UpdaterThread;
	Graphics bufferg; 
	Image bufferi;
	Dimension bufferdim; 
	Font MyFont;
	
	// Actual 3D Variables
	Scenery test;
	Scenery test2;
	Triangle t1;
	World testy;
	Camera testc;
	
	// Other Variables
	String status;
	int omx, omy;
	
	// Frame Rate Regulation
	long start=0; 
    long tick_end_time; 
    long tick_duration; 
    long sleep_duration; 
    static final int MIN_SLEEP_TIME = 10; 
    static final int MAX_FPS = 100; 
    static final int MAX_MS_PER_FRAME = 1000 / MAX_FPS; 
    float fps=0; 
    double realfps=0; 

	public void init() 
    {
        setSize(600,400);
        
        //status = "Rotate";
        status = "Translate";
        
        bufferdim = getSize(bufferdim);
	    bufferi = createImage(bufferdim.width,bufferdim.height); 
	    bufferg = bufferi.getGraphics();
	    setBackground(Color.black);
	    MyFont = new Font("Arial",Font.ITALIC,16);
	    addMouseListener(this);
	    addMouseMotionListener(this); 
		running = true;
		
		test = new Scenery();
		test2 = new Scenery();
		t1 = new Triangle(new Vertex(-50,40,0),new Vertex(0,0,70),new Vertex(0,7,0));
		Triangle t2 = new Triangle(new Vertex(-60,0,-15),new Vertex(0,25,70),new Vertex(0,-30,0));
		Triangle t3 = new Triangle(new Vertex(-50,40,0),new Vertex(0,0,70),new Vertex(0,7,0));
		Triangle t4 = new Triangle(new Vertex(50,-2,0),new Vertex(0,25,70),new Vertex(0,-30,0));
        
		test = Loader.LoadObject("C:\\Java\\testmodel.txt",false);
		//test = Algorithms.CreateCube(30, 30, 30);
		//test.translateX(40);
		//test.translateY(-65);
		test.rotateZ(100);
//		test.translateY(-65);
		//test.translateZ(50);
        
        test2.add(t3);
        test2.add(t4);
        //test.transform(new Translation(1,60,1));
        //test2.transform(new Translation(1,40,1));
        test.dim = bufferdim;
        test2.dim = bufferdim;
        //test.setCenter(new Vertex(50.0,50.0,50.0));
        test.calculateCenter();
        test2.calculateCenter();
        testy = new World();
        testy.add(test);
        //testy.add(test2);
        testy.dim = bufferdim;
        testc = new Camera(bufferdim,testy,true);
        //testc.translateY(50);
        //test.translateX(5);
        
        //testc.rotateX(50);
        
        UpdaterThread = new Thread(this);
   	    UpdaterThread.start();
    }
	
	public void stop() 
    { 
		
    }
	
	public void paint(Graphics g) 
    { 
		bufferg.setColor(new Color(0,0,0,255));
        bufferg.fillRect(0,0,bufferdim.width,bufferdim.height);
        bufferg.setFont(MyFont);
        // Paint
        
        //testy.paintWorld(bufferg);
        
        testc.paintScene(bufferg);
        
        bufferg.setColor(Color.gray);
        //bufferg.drawString("FPS: "+fps,4,20); 
        bufferg.drawString("Use your mouse to drag the cube around",4,20); 
        bufferg.drawString("FPS: "+realfps,4,40); 
        bufferg.drawString("Triangles: "+test.getTriangleNum(),4,60); 
        // Paint End
		g.drawImage(bufferi,0,0,this); 
		
    }
	
	public void run() 
    { 
         while (running) 
         { 
        	 start = System.currentTimeMillis(); 
        	 
        	 // Update Stuff
        	 //System.out.println("Start");
        	 //test.rotateX(0.02);
        	 //test2.rotateX(-0.02);
        	 //testy.rotateZ(-0.02);
             //testc.translateZ(0.05);
             //testc.rotateY(0.01);
        	 //testy.rotateZ(0.01);
        	 //System.out.println(t1.a.z);
        	 //testy.translateZ(0.04);
        	 //test.translateZ(-0.03);
        	 //System.out.println("End");
        	 //for(int f = 0; f<553; f++) test.rotateX(0.1);
        	 //for(int f = 0; f<500; f++) test.rotateY(0.1);
        	 //test.rotateX(1);
        	 //test.rotateY(1);
        	 //test.rotateZ(1);
        	 test.translateZ(-1);
        	 
        	 //System.out.println(test.rx);
        	 
        	 repaint();
        	 
        	 // Frame Rate Regulation
        	 tick_end_time = System.currentTimeMillis(); 
             tick_duration = tick_end_time - start; 
             sleep_duration = MAX_MS_PER_FRAME - tick_duration; 
             
             if (sleep_duration < MIN_SLEEP_TIME) 
             { 
                  sleep_duration = MIN_SLEEP_TIME; 
             } 
             fps = 1000 / (sleep_duration + tick_duration); 
             
             if(tick_duration != 0) realfps = 1000 / tick_duration; 
             
             try{ 
                  Thread.sleep(sleep_duration); 
             } catch(InterruptedException e) {} 

         }
    }
	
    public void update(Graphics g) 
    { 
       paint(g); 
    }
    
	public void destroy() 
    { 
       running = false; 
       UpdaterThread = null; 
    }

	// Mouse Events
    public void mouseMoved(MouseEvent me)  
    {  
        mx = me.getPoint().x;
        my = me.getPoint().y;
  	   if(status == "Translate")
  	   if(Down)
  	   {
  		   test.translateX(-(omx-mx)/2);
  		   test.translateY(-(omy-my)/2);
  	   }
 	   if(status == "Rotate")
 	   if(Down)
 	   {
 	       test.rotateY((omx-mx)/2);
 	       test.rotateX((omy-my)/2);
 	   }
  	   omx = mx;
  	   omy = my;
    }
    
    public void mouseDragged(MouseEvent me)  
    { 
       mx = me.getPoint().x;
       my = me.getPoint().y;
 	   if(status == "Translate")
 	   if(Down)
 	   {
 		   test.translateX(-(omx-mx)/2);
 		   test.translateY(-(omy-my)/2);
 	   }
	   if(status == "Rotate")
	   if(Down)
	   {
	       test.rotateY((omx-mx)/2);
	       test.rotateX((omy-my)/2);
	   }
 	   omx = mx;
 	   omy = my;
    } 

    public void mouseClicked (MouseEvent me) 
    {
    // Nothing 
    } 
    
    public void mouseEntered (MouseEvent me) 
    {
    // Nothing 
    } 
    
    public void mousePressed (MouseEvent me) 
    {
       Down = true;   
       testc.showVertices  = true;
       testc.showWireframe = true;
    } 
    
    public void mouseReleased (MouseEvent me) 
    {
       Down = false;
       testc.showVertices  = false;
       testc.showWireframe = false;
    }  
    
    public void mouseExited (MouseEvent me) 
    {
    // Nothing 
    }  	
	
}