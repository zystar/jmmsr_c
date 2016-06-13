package com.jmmsr;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
  
public class PhotoAlbumActivity extends Activity {  
	private List<String> imagePath=new ArrayList<String>();//图片文件的路径
	private static String[] imageFormatSet=new String[]{"jpg","png","gif"};//合法的图片文件格式
	/*
	 * 方法:判断是否为图片文件
	 * 参数:String path图片路径
	 * 返回:boolean 是否是图片文件，是true，否false
	 * */
	private static boolean isImageFile(String path){
		for(String format:imageFormatSet){//遍历数组
			if(path.contains(format)){//判断是否为合法的图片文件
				return true;
			}
		}
		return false;
	} 
	/*
	 * 方法:用于遍历指定路径
	 * 参数:String url遍历路径
	 * 无返回值
	 * */
	private void getFiles(String url){
		File files=new File(url);//创建文件对象
		File[] file=files.listFiles();
		try {
			for(File f:file){//通过for循环遍历获取到的文件数组
				if(f.isDirectory()){//如果是目录，也就是文件夹
					getFiles(f.getAbsolutePath());//递归调用
				}else{
					if(isImageFile(f.getPath())){//如果是图片文件
						imagePath.add(f.getPath());//将文件的路径添加到List集合中
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();//输出异常信息
		}
	}
	
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_photo_album); 
        
        //添加返回事件
        ImageView ai_back = (ImageView)findViewById(R.id.ai_back);
        ai_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
        
        String path=Environment.getExternalStorageDirectory()+"/PhotoDiary";//获得SD卡的路径
        getFiles(path);//调用getFiles()方法获取SD卡上的全部图片
        if(imagePath.size()<1){//如果不存在文件图片
        	Toast.makeText(PhotoAlbumActivity.this, "没有文件", Toast.LENGTH_SHORT).show();
        }
        
        /*首先获取GrivView组件，然后创建BaseAdapter类的对象，并重写其中的
         * getView()、getItemId()、getItem()和getConut()方法，其中最主要的是重写
         * getView()方法来设置要显示的图片，最后将BaseAdapter适配器与GridView关联*/
        GridView gridview=(GridView)findViewById(R.id.gridView1);//获取GridView组件
        BaseAdapter adapter=new BaseAdapter(){


        	@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				ImageView iv;//声明ImageView的对象
				if(convertView==null){
					iv=new ImageView(PhotoAlbumActivity.this);//实例化ImageView的对象
					/**************设置图像的宽度和高度**************/
					iv.setAdjustViewBounds(true);
//					iv.setMaxWidth(150);
//					iv.setMaxHeight(113);
					/****************************/
//					iv.setPadding(5, 5, 5, 5);//设置ImageView的内边距
				}else{
					iv=(ImageView)convertView;
				}
				//为ImageView设置要显示的图片
				Bitmap bm=BitmapFactory.decodeFile(imagePath.get(position));
				iv.setImageBitmap(bm);
				return iv;
			}
        	
        	//获得数量
			@Override
			public int getCount() {
				return imagePath.size();
			}


			//获得当前选项
			@Override
			public Object getItem(int position) {
				return position;
			}


			//获得当前选项的id
			@Override
			public long getItemId(int position) {
				return position;
			}
        };
        
        gridview.setAdapter(adapter);//将适配器与GridView关联
        
      //绑定列表项事件监听器
        gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position,
					long id) {
				//点击某一个item时启动新应用
				Intent i = new Intent(PhotoAlbumActivity.this, ImageActivity.class);
				i.putExtra("p", imagePath.get(position));
				startActivity(i);
			}
		});
	}


}