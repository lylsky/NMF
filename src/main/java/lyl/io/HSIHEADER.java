package lyl.io;

import java.io.*;

/********************************
 * @author lyl
 *功能：hrc图片的头文件信息，读取相关文件信息
 *
 *
 */


/*
 * data type - parameter identifying the type of data representation,
 *  where 
 *  1=8 bit byte        java =====>java byte
 *  2=16-bit signed integer; =====>java short
 *  3=32-bit signed long integer;   ==>java int
 *  4=32-bit floating point;    =====>java float
 *  5=64-bit double precision floating point;    =====>java double
 *  6=2x32-bit complex,real-imaginary pair of double precision;
 *  9=2x64-bit double precision complex, real-imaginary pair of double precision; 
 *  12=16-bit unsigned  integer;            
 *  13=32-bit unsigned long integer; 
 *  14=64-bit signed long integer;                 =====>java long
 *  15=64-bit unsigned long integer.
 *
 */



public class HSIHEADER {
	private int samples;		//行数
	private int lines;	 		//列数
	private int bands;			//波段数
	private int dataType;		//数据类型
	private String interleave;			//图像格式
	private int byteOrder;				//字节顺序
/*	private String mapinfo;				//地理信息
	private String[] describe;			//图像描述
	private	int ndescribe;				//图像描述大小
*/	
	public HSIHEADER(){
		this.samples =0;
		this.lines =0;
		this.bands = 0;
		this.dataType = 0;
		this.interleave =null;
		this.byteOrder = -1;
	}

	public int getSamples() {
		return samples;
	}

	public void setSamples(int samples) {
		this.samples = samples;
	}

	public int getLines() {
		return lines;
	}

	public void setLines(int lines) {
		this.lines = lines;
	}

	public int getBands() {
		return bands;
	}

	public void setBands(int bands) {
		this.bands = bands;
	}

	public int getDataType() {
		return dataType;
	}

	public void setDataType(int dataType) {
		this.dataType = dataType;
	}

	public String getInterleave() {
		return interleave;
	}

	public void setInterleave(String interleave) {
		this.interleave = interleave;
	}

	public int getByteOrder() {
		return byteOrder;
	}

	public void setByteOrder(int byteOrder) {
		this.byteOrder = byteOrder;
	}

	public void ReadHead(File file) throws IOException{
		BufferedReader reader =null;
		String strLine,strLeft,strRight;
		try {
			reader = new BufferedReader(new FileReader(file));
			while((strLine = reader.readLine())!=null){
				int pos =strLine.indexOf('=');
				if(pos >=0){
					strLeft =strLine.substring(0, pos).trim();
					strRight = strLine.substring(pos+1,strLine.length()).trim();

					if(strLeft.equals("samples")){
						setSamples(Integer.parseInt(strRight));
						continue;
					}
					if(strLeft.equals("lines")){
						setLines(Integer.parseInt(strRight));
						continue;
					}
					if(strLeft.equals("bands")){
						setBands(Integer.parseInt(strRight));
						continue;
					}
					if(strLeft.equals("interleave")){
						setInterleave(strRight);
						continue;
					}
					if(strLeft.equals("data type")){
						setDataType(Integer.parseInt(strRight));
						continue;
					}
					if(strLeft.equals("byte order")){
						setByteOrder(Integer.parseInt(strRight));
						continue;
					}
					if (strLeft == "map info")
					{
						//...
						continue;
					}
					if (strLeft == "band names")
					{
						//...
						continue;
					}
					if (strLeft == "wavelength")
					{
						//...
						continue;
					}
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("文件读取失败.....");
			e.printStackTrace();
		}

	}
}

