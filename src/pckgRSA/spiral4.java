package pckgRSA;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.*;

public class spiral4
{
	private BufferedImage p;
    private String message;
	private String filename;
	private String mesout;
	private String fileout;
	int k,m;
	int width,height,len,i1,j1,k1,m1,i2,j2;
    public spiral4(BufferedImage img, String mes)throws IOException 
	{
		//filename=fil;
		//fileout=filo;
		p = img; 
        message = mes;
		k=0;
		m=0;
		mesout="";
		width=p.getWidth();
		height=p.getHeight();
		//width=8;
		//height=8;
		len=0;
		i1=0;
		j1=0;
		k1=0;
		m1=0;
		i2=0;
		j2=0;
	}
	/*public spiral4(String fil)throws IOException 
	{
		p = ImageIO.read(new File(fil)); 
        k=0;
		m=0;
		mesout="";
		width=p.getWidth();
		height=p.getHeight();
		len=0;
		i1=0;
		j1=0;
		k1=0;
		m1=0;
		i2=0;
		j2=0;
	}*/
	public int[] bin(int a)
	{
		int n=a;
		int count=0;
		int b[];
		int r;
		
		//System.out.println(len);
		b=new int[8];
		while(n>0)
		{
			r=n%2;
			b[count++]=r;
			n=n/2;
		}
		while(count<8)
			b[count++]=0;
		return b;
	}
	public int dec(int []a)
	{
		int sum=0;
		for(int i=0;i<a.length;i++)
		{
			sum=sum+(int)(Math.pow(2,i)*a[i]);
		}
		return sum;
	}
		
	public int nextbit()
	{
		
		try 
		{
			byte arr[]= message.getBytes( "UTF-8" );
			//System.out.println("k="+k +" m="+m);
			while(k<arr.length)
			{
				int arr2[]=bin(arr[k]);
				while(m<arr2.length)
				{
					return arr2[m++];		
				}
				
				k++;
				m=0;

			}
		}
		catch( UnsupportedEncodingException e) 
		{
			System.out.println("Unsupported character set");
		}
		return -1;
	}
    public void convert() throws IOException
	{
		int t=0,q=0,nb=-1,cnt=1,rgb;
		System.out.println("Hidding Message...");
		
		while(cnt<=3)
		{
			for (int i = 0; i < p.getWidth(); i++) 
			{
				for (int j = 0; j < p.getHeight(); j++) 
				{
					Color c = new Color(p.getRGB(i, j));
					int r=c.getRed();
					int g=c.getGreen();
					int b=c.getBlue();
					rgb=r;
					nb=nextbit();
					switch(cnt)
					{
						case 1:
							rgb=r;
						case 2:
							rgb=g;
						case 3:
							rgb=b;
					}
					if(nb==-1) break;
					if(nb!=-1)
					{
						if((rgb%2==0)&&(nb==1))
							rgb++;
						else if((rgb%2==1)&&(nb==0))
							rgb--;
						switch(cnt)
						{
							case 1:
								c=new Color(rgb,g,b);
							case 2:
								c=new Color(r,rgb,b);
							case 3:
								c=new Color(r,g,rgb);
						}
						p.setRGB(i, j, c.getRGB());
						q=j+1;
					}
				}
				if(nb==-1) break;
				t=i+1;
			}
		
			if(nb==-1)
			{ 
				int count=0,ev;
				for (; t< p.getWidth()&&count<8; t++) 
				{
					for (; q < p.getHeight()&&count<8; q++) 
					{
						Color c = new Color(p.getRGB(t, q));
						int r=c.getRed();
						int g=c.getGreen();
						int b=c.getBlue();
						rgb=r;
						switch(cnt)
						{
							case 1:
								rgb=r;
							case 2:
								rgb=g;
							case 3:
								rgb=b;
						}
						if(rgb%2==1)
							rgb--;
						switch(cnt)
						{
							case 1:
								c=new Color(rgb,g,b);
							case 2:
								c=new Color(r,rgb,b);
							case 3:
								c=new Color(r,g,rgb);
						}
						p.setRGB(t, q, c.getRGB());
						count++;
						//ev=r%2;
						//System.out.println(ev);
					}
				}
				break;
			}
			cnt++;
		}
		System.out.println("Message is successfully hidden.");
		String suffix = fileout.substring(fileout.lastIndexOf('.') + 1);
		File outputfile = new File(fileout);
		ImageIO.write(p, suffix, outputfile);
    }
	public void unconvert()
	{
		int count=0,ch,d,cnt=1,rgb;
		char chr;
		int a[]=new int[8];
		System.out.println("Recovering Message...");
		while(cnt<=3)
		{
			for (int i = 0; i < p.getWidth(); i++) 
			{
				for (int j = 0; j < p.getHeight(); j++) 
				{
					Color c = new Color(p.getRGB(i, j));
					int r=c.getRed();
					int g=c.getGreen();
					int b=c.getBlue();
					rgb=r;
					switch(cnt)
					{
						case 1:
							rgb=r;
						case 2:
							rgb=g;
						case 3:
							rgb=b;
					}
					if(rgb%2==0)
						ch=0;
					else	
						ch=1;
					a[count++]=ch;
					if(count==8)
					{
						d=dec(a);
						chr=(char)d;
						if(chr=='\0')
							return;
						mesout=mesout.concat(Character.toString(chr));
						//System.out.println(mesout);
						count=0;
					}
				}
			}
			cnt++;
		//System.out.println(mesout);
		}
	}  
	public int spiral()
	{
		
		while(true)
		{
			if(m1%4==0)
			{
				if(j1==width)
					return -1;
				if(j1<width)
				{
					//System.out.println("("+i1+","+j1+")");
					i2=i1;
					j2=j1;
					j1++;
				}
				if(j1==width)
				{
					j1--;
					i1++;
					width--;
					m1++;
				}
				return 0;
			}
			if(m1%4==1)
			{
				if(i1==height)
					return -1;
				if(i1<height)
				{
					//System.out.println("("+i1+","+j1+")");
					i2=i1;
					j2=j1;
					i1++;
					
				}
				if(i1==height){
				i1--;
				j1--;
				height--;
				m1++;
				}
				return 0;
			}
			if(m1%4==2)
			{
				if(j1<k1)
					return -1;
				if(j1>=k1)
				{
					//System.out.println("("+i1+","+j1+")");
					i2=i1;
					j2=j1;
					j1--;
					
				}
				if(j1<k1){
				j1++;
				i1--;
				m1++;
				}
				return 0;
			}
			if(m1%4==3)
			{	
				if(i1==k1)
					return -1;
				if(i1>k1)
				{
					//System.out.println("("+i1+","+j1+")");
					i2=i1;
					j2=j1;
					i1--;
					//return 0;
				}
				if(i1==k1){
				i1++;
				j1++;
				k1++;
				m1++;
				}
				return 0;
			}
		}
		
	}
	public void clear()
	{
		len=0;
		i1=0;
		j1=0;
		k1=0;
		m1=0;
		i2=0;
		j2=0;
	}
	public BufferedImage convert2() throws IOException
	{
		int t=0,q=0,nb=-1,cnt=1,rgb,ft,res;
		//System.out.println("Hidding Message...");
		//System.out.println(width+" "+height);
		while(cnt<=3)
		{
			while(true)
			{
				//ft=spiral();
				//if(ft==0)
				//System.out.println(i2+" "+j2+" "+m1);
				/*else{
					clear();
					break;
				}*/
				ft=spiral();
				
				if(ft==0)
				{
					//System.out.println(i2+" "+j2);
					Color c = new Color(p.getRGB(j2, i2));
					int r=c.getRed();
					int g=c.getGreen();
					int b=c.getBlue();
					rgb=r;
					nb=nextbit();
					switch(cnt)
					{
						case 1:
							rgb=r;
						case 2:
							rgb=g;
						case 3:
							rgb=b;
					}
					if(nb==-1){ 
						//System.out.println(i2+" "+j2);	
						break;
					}
					if(nb!=-1)
					{
						if((rgb%2==0)&&(nb==1))
							rgb++;
						else if((rgb%2==1)&&(nb==0))
							rgb--;
						switch(cnt)
						{
							case 1:
								c=new Color(rgb,g,b);
							case 2:
								c=new Color(r,rgb,b);
							case 3:
								c=new Color(r,g,rgb);
						}
						p.setRGB(j2, i2, c.getRGB());
						
					}
				}
				else{
					clear();
					break;
				}	
				//if(nb==-1) break;
				//t=i+1
			}
		
			if(nb==-1)
			{ 
				int count=0,ev;
				System.out.println("ok");
				for (; count<8;count++) 
				{
					
					if(ft==0)
					{	
						//System.out.println(i2+" "+j2);	
						Color c = new Color(p.getRGB(j2, i2));
						int r=c.getRed();
						int g=c.getGreen();
						int b=c.getBlue();
						rgb=r;
						switch(cnt)
						{
							case 1:
								rgb=r;
							case 2:
								rgb=g;
							case 3:
								rgb=b;
						}
						if(rgb%2==1)
							rgb--;
						switch(cnt)
						{
							case 1:
								c=new Color(rgb,g,b);
							case 2:
								c=new Color(r,rgb,b);
							case 3:
								c=new Color(r,g,rgb);
						}
						p.setRGB(j2, i2, c.getRGB());
						
						//System.out.println("count= "+count);
						//ev=r%2;
						//System.out.println(ev);
					}
					else
					{
						cnt++;
						clear();
					}
					ft=spiral();
				}
				
				
				/*clear();
				System.out.println("Message is successfully hidden.");
				String suffix = fileout.substring(fileout.lastIndexOf('.') + 1);
				File outputfile = new File(fileout);
				ImageIO.write(p, suffix, outputfile);
				return;*/
				break;
			}
			
			cnt++;
			clear();
		}
		clear();
		return p;
		/*System.out.println("Message is successfully hidden.");
		String suffix = fileout.substring(fileout.lastIndexOf('.') + 1);
		File outputfile = new File(fileout);
		ImageIO.write(p, suffix, outputfile);*/
    }
	public String unconvert2()
	{
		int count=0,ch,d,cnt=1,rgb,ft,flag=0;
		char chr;
		int a[]=new int[8];
		//System.out.println("Recovering Message...");
		while(cnt<=3)
		{
			clear();
			while(true)
			{
				ft=spiral(); 
				//System.out.println(i2+" "+j2);
				//System.out.println(width+" "+height);
				if(ft==0)
				{
					Color c = new Color(p.getRGB(j2, i2));
					int r=c.getRed();
					int g=c.getGreen();
					int b=c.getBlue();
					rgb=r;
					switch(cnt)
					{
						case 1:
							rgb=r;
						case 2:
							rgb=g;
						case 3:
							rgb=b;
					}
					if(rgb%2==0)
						ch=0;
					else	
						ch=1;
					a[count++]=ch;
					
					if(count==8)
					{
						d=dec(a);
						chr=(char)d;
						if(chr=='\0'){
                                                    flag=1;
                                                    break;
                                                }
						//System.out.print(chr);
						mesout=mesout.concat(Character.toString(chr));
						//System.out.println(mesout);
						count=0;
					}
				}
				else
					break;
			}
                        if(flag==1)
                            break;
			cnt++;
		//System.out.println(mesout);
		}
		return mesout;
	}  
	
}