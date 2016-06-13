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
	private List<String> imagePath=new ArrayList<String>();//ͼƬ�ļ���·��
	private static String[] imageFormatSet=new String[]{"jpg","png","gif"};//�Ϸ���ͼƬ�ļ���ʽ
	/*
	 * ����:�ж��Ƿ�ΪͼƬ�ļ�
	 * ����:String pathͼƬ·��
	 * ����:boolean �Ƿ���ͼƬ�ļ�����true����false
	 * */
	private static boolean isImageFile(String path){
		for(String format:imageFormatSet){//��������
			if(path.contains(format)){//�ж��Ƿ�Ϊ�Ϸ���ͼƬ�ļ�
				return true;
			}
		}
		return false;
	} 
	/*
	 * ����:���ڱ���ָ��·��
	 * ����:String url����·��
	 * �޷���ֵ
	 * */
	private void getFiles(String url){
		File files=new File(url);//�����ļ�����
		File[] file=files.listFiles();
		try {
			for(File f:file){//ͨ��forѭ��������ȡ�����ļ�����
				if(f.isDirectory()){//�����Ŀ¼��Ҳ�����ļ���
					getFiles(f.getAbsolutePath());//�ݹ����
				}else{
					if(isImageFile(f.getPath())){//�����ͼƬ�ļ�
						imagePath.add(f.getPath());//���ļ���·����ӵ�List������
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();//����쳣��Ϣ
		}
	}
	
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_photo_album); 
        
        //��ӷ����¼�
        ImageView ai_back = (ImageView)findViewById(R.id.ai_back);
        ai_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
        
        String path=Environment.getExternalStorageDirectory()+"/PhotoDiary";//���SD����·��
        getFiles(path);//����getFiles()������ȡSD���ϵ�ȫ��ͼƬ
        if(imagePath.size()<1){//����������ļ�ͼƬ
        	Toast.makeText(PhotoAlbumActivity.this, "û���ļ�", Toast.LENGTH_SHORT).show();
        }
        
        /*���Ȼ�ȡGrivView�����Ȼ�󴴽�BaseAdapter��Ķ��󣬲���д���е�
         * getView()��getItemId()��getItem()��getConut()��������������Ҫ������д
         * getView()����������Ҫ��ʾ��ͼƬ�����BaseAdapter��������GridView����*/
        GridView gridview=(GridView)findViewById(R.id.gridView1);//��ȡGridView���
        BaseAdapter adapter=new BaseAdapter(){


        	@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				ImageView iv;//����ImageView�Ķ���
				if(convertView==null){
					iv=new ImageView(PhotoAlbumActivity.this);//ʵ����ImageView�Ķ���
					/**************����ͼ��Ŀ�Ⱥ͸߶�**************/
					iv.setAdjustViewBounds(true);
//					iv.setMaxWidth(150);
//					iv.setMaxHeight(113);
					/****************************/
//					iv.setPadding(5, 5, 5, 5);//����ImageView���ڱ߾�
				}else{
					iv=(ImageView)convertView;
				}
				//ΪImageView����Ҫ��ʾ��ͼƬ
				Bitmap bm=BitmapFactory.decodeFile(imagePath.get(position));
				iv.setImageBitmap(bm);
				return iv;
			}
        	
        	//�������
			@Override
			public int getCount() {
				return imagePath.size();
			}


			//��õ�ǰѡ��
			@Override
			public Object getItem(int position) {
				return position;
			}


			//��õ�ǰѡ���id
			@Override
			public long getItemId(int position) {
				return position;
			}
        };
        
        gridview.setAdapter(adapter);//����������GridView����
        
      //���б����¼�������
        gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position,
					long id) {
				//���ĳһ��itemʱ������Ӧ��
				Intent i = new Intent(PhotoAlbumActivity.this, ImageActivity.class);
				i.putExtra("p", imagePath.get(position));
				startActivity(i);
			}
		});
	}


}