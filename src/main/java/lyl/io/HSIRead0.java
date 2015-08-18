/*
 * 高光谱数据读取类
 */
package lyl.io;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 备用
 */
public class HSIRead0 {

	private  String filename=null;
	private File file;
	private HSIHEADER hsiheader;
	private short[][] sdata;
	private int[][] idata;
	private float[][] fdata;
	public HSIRead0(String filename) throws IOException{
		this.filename=filename;

		String[] t=this.filename.split("\\.");


		file=new File(t[0]+".hdr");

		if(!file.exists()){
			System.out.println("图像文件没有头文件....");
			System.exit(0);
		}

		hsiheader =new HSIHEADER();
		hsiheader.ReadHead(file);

		file=new File(this.filename);
	}


	public int getDataType(){
		return hsiheader.getDataType();
	}

	public short[][] getSdata() {
		return sdata;
	}

	public int[][] getIdata() {
		return idata;
	}

	public float[][] getFdata() {
		return fdata;
	}

	public void Read(){
		int leave=0;
		String interleave=hsiheader.getInterleave();

		if(interleave.equals("bil"))
			leave=1;
		else if(interleave.equals("bip"))
			leave=2;
		else if(interleave.equals("bsq"))
			leave=3;
		switch(leave){
			case 3:
				ReadBSQ();
				break;
			case 1:
				ReadBIL();
				break;
			case 2:
				ReadBIP();
				break;
			default:
				System.out.println("暂不支持改文件格式!");
		}
	}

	public int getBands(){
		return hsiheader.getBands();
	}
	public int getRows(){
		return hsiheader.getLines();
	}
	public int getCols(){
		return hsiheader.getSamples();
	}
	public String getInterleave() {
		return hsiheader.getInterleave();
	}
	public int getByteOrder() {
		return hsiheader.getByteOrder();
	}

	public int getDataSize(){
		switch(hsiheader.getDataType()){
			case 2:
				return 2;
			case 4:
				return 4;
			case 12:
				return 2;
			default:
				System.out.println("暂不支持该文件数据类型");
				return 0;
		}
	}
	
	/*
	 * 按像素依次读取 sdata m*n m为像素点，n为波段数
	 */
	public void ReadBSQ(){
		try {
			RandomAccessFile file = new RandomAccessFile(filename,"r");
			FileChannel channel = file.getChannel();
			MappedByteBuffer imgbuffer=channel.map(FileChannel.MapMode.READ_ONLY, 0, file.length());		
		
			int nBand=getBands();
			int dataSize = getDataSize();
			int PixelSize=getRows()*getCols();
			
			/************************
			 * 还有很多数据类型有待扩展
			 * 暂且实现int16即对应java中的short类型
			 ***********************/
			switch (hsiheader.getDataType()){
				case 2:
					sdata=new short[PixelSize][nBand];
					for(int i =0;i<nBand;i++)
						for(int j =0,k=0;j<PixelSize;j++,k=k+dataSize){
							sdata[j][i]=(short)((imgbuffer.get()&0xff)|(imgbuffer.get()<<8));
						}
					break;
				default:
					System.out.println("没有此类数据类型");
			}
			
			file.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("文件映射失败.....");
			e.printStackTrace();
		}
	}
	/*
	 * 数据按照波段顺序读取sdata m*n m为波段数，n为像素点
	 */
	public void ReadBSQ1(){
		try {
			RandomAccessFile file = new RandomAccessFile(filename,"r");
			FileChannel channel = file.getChannel();
			MappedByteBuffer imgbuffer=channel.map(FileChannel.MapMode.READ_ONLY, 0, file.length());		
		
			int nBand=hsiheader.getBands();
			int dataSize = getDataSize();
			int PixelSize=hsiheader.getSamples()*hsiheader.getLines();
			
			/************************
			 * 还有很多数据类型有待扩展
			 * 暂且实现int16即对应java中的short类型
			 ***********************/
			switch (hsiheader.getDataType()){
				case 2:
					sdata=new short[nBand][PixelSize];
					for(int i =0;i<nBand;i++)
						for(int j =0,k=0;j<PixelSize;j++,k=k+dataSize){
							sdata[i][j]=(short)((imgbuffer.get()&0xff)|(imgbuffer.get()<<8));
						}
					break;
				default:
					System.out.println("没有此类数据类型");
			}
			
			file.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("文件映射失败.....");
			e.printStackTrace();
		}
	}
	
	
	public void ReadBIL(){
		try {
			RandomAccessFile file = new RandomAccessFile(filename,"r");
			FileChannel channel = file.getChannel();
			MappedByteBuffer imgbuffer=channel.map(FileChannel.MapMode.READ_ONLY, 0, file.length());		
		
			int bands=hsiheader.getBands();
			int dataSize = getDataSize();
			int cols=hsiheader.getSamples();
			int rows=hsiheader.getLines();
			int PixelSize=rows*cols;
			System.out.println("bands:"+bands+",pixels"+PixelSize+",datasize:"+dataSize+",length:"+file.length());
			/************************
			 * 还有很多数据类型有待扩展
			 * 暂且实现int16即对应java中的short类型
			 ***********************/
		
			switch (hsiheader.getDataType()){
				case 2:
					sdata=new short[PixelSize][bands];
					for(int i =0;i<rows;i++){
						int p=i*cols;
						for(int j=0;j<bands;j++)
							for(int k=0;k<cols;k++){
								sdata[p+k][j]=(short)((imgbuffer.get()&0xff)|(imgbuffer.get()<<8));
						}
					}
					break;
				case 4:
					fdata=new float[PixelSize][bands];
							for(int i =0;i<rows;i++){
								int p=i*cols;
								for(int j=0;j<bands;j++)
									for(int k=0;k<cols;k++){
										int t=(int)((imgbuffer.get()&0xff)|(imgbuffer.get()<<8)&0xffff
												|(imgbuffer.get()<<16)&0xffffff | (imgbuffer.get()<<24));
										fdata[p+k][j]=Float.intBitsToFloat(t);
									}
							}
					break;
				case 12:
					idata=new int[PixelSize][bands];
							for(int i =0;i<rows;i++){
								int p=i*cols;
								for(int j=0;j<bands;j++)
									for(int k=0;k<cols;k++){
										idata[p+k][j]=(int)((imgbuffer.get()&0xff)|(imgbuffer.get()<<8)&0xffff);
								}
							}
					break;
				default:
					System.out.println("没有此类数据类型");
			}
			channel.close();
			file.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("文件映射失败.....");
			e.printStackTrace();
		}
	}
	
	public void ReadBIP(){
		try {
			RandomAccessFile file = new RandomAccessFile(filename,"r");
			FileChannel channel = file.getChannel();
			MappedByteBuffer imgbuffer=channel.map(FileChannel.MapMode.READ_ONLY, 0, file.length());

			int bands=hsiheader.getBands();
			int dataSize = getDataSize();
			int cols=hsiheader.getSamples();
			int rows=hsiheader.getLines();
			int PixelSize=rows*cols;
			System.out.println("bands:"+bands+",pixels"+PixelSize+",datasize:"+dataSize+",length:"+file.length());
			/************************
			 * 还有很多数据类型有待扩展
			 * 暂且实现int16即对应java中的short类型
			 ***********************/

			switch (hsiheader.getDataType()){
				case 2:
					sdata=new short[PixelSize][bands];
					//		byte[] temp=new byte[(int) file.length()];
					//	imgbuffer.get(temp);
					//		int n=0;
					for(int i =0;i<PixelSize;i++){
						for(int j=0;j<bands;j++) {
								//		sdata[p+k][j]=(short)((temp[n++]&0xff)|(temp[n++]<<8));
								sdata[i][j]=(short)((imgbuffer.get()&0xff)|(imgbuffer.get()<<8));
							}
					}
					break;
				case 4:
					fdata=new float[PixelSize][bands];
					for(int i =0;i<rows;i++){
						int p=i*cols;
						for(int j=0;j<bands;j++)
							for(int k=0;k<cols;k++){
								int t=(int)((imgbuffer.get()&0xff)|(imgbuffer.get()<<8)&0xffff
										|(imgbuffer.get()<<16)&0xffffff | (imgbuffer.get()<<24));
								fdata[p+k][j]=Float.intBitsToFloat(t);
							}
					}
					break;
				case 12:
					idata=new int[PixelSize][bands];
					for(int i =0;i<rows;i++){
						int p=i*cols;
						for(int j=0;j<bands;j++)
							for(int k=0;k<cols;k++){
								idata[p+k][j]=(int)((imgbuffer.get()&0xff)|(imgbuffer.get()<<8)&0xffff);
							}
					}
					break;
				default:
					System.out.println("没有此类数据类型");
			}
			channel.close();
			file.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("文件映射失败.....");
			e.printStackTrace();
		}
	}
	
}
