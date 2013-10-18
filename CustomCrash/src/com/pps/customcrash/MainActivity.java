package com.pps.customcrash;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.pps.customcrash.base.BaseActivity;

/**
 * �������ݷ������
 * @author jiangqingqing
 * @time 2012/10/14  14:50
 */
public class MainActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        
		Intent intent=getIntent();
		//��ȡIntent��Action
		String action=intent.getAction();
		//��ȡIntent��MIME Type
		String type=intent.getType();
		
		if(Intent.ACTION_SEND.equals(action)&&type!=null){
	       
			// ���ﴦ���ı�����
			if(type.startsWith("text/"))
			{
				handleSendText(intent);
			}
			// ���ﴦ��ͼƬ
			else if (type.startsWith("image/")) {
				handleSendImage(intent);
			}
			
			
		}else if (Intent.ACTION_SEND_MULTIPLE.equals(action)&&type!=null) {
			if(type.startsWith("image/"))
			{
				// ���ﴦ�����ͼƬ
				HandleSendImageMutiply(intent);
			}
		}
     
	}
	
	/**
	 * ͬTextView��ʾ�ı�
	 * ���Դ�һ����ı��ļ�
	 * @param pIntent
	 */
	private void handleSendText(Intent pIntent)
	{
		TextView textView=new TextView(this);
		// ���͵�ʱ��ʹ��Intent.putExtra(Intent.EXTRA_TEXT)��
		String sharedText=pIntent.getStringExtra(Intent.EXTRA_TEXT);
	    if(sharedText!=null){
	    	textView.setText(sharedText);
	    }
		
	    Uri textUri=(Uri)pIntent.getParcelableExtra(Intent.EXTRA_STREAM);
	    if(textUri!=null){
	    	try {
				InputStream inputStream= this.getContentResolver().openInputStream(textUri);
				textView.setText(inputStreamToString(inputStream));
			} catch (Exception e) {
			}
	    }
	    setContentView(textView);
	}
	
	/**
	 * 
	 * @param pInStream
	 * @return
	 */
	private String inputStreamToString(InputStream pInStream)throws Exception
	{
		ByteArrayOutputStream bos=new ByteArrayOutputStream();
		byte[] buffer=new byte[1024];
		int length=0;
		while((length=pInStream.read(buffer))!=-1)
		{
			bos.write(buffer, 0, length);
		}
		pInStream.close();
		return new String(bos.toByteArray(), "UTF-8");
	}

	
	// ������ͼƬ
	private void handleSendImage(Intent pIntent)
	{
		Uri ImageUri=(Uri)pIntent.getParcelableExtra(Intent.EXTRA_STREAM);
		if(ImageUri!=null)
		{
		 ImageView image=new ImageView(this);
		 image.setImageURI(ImageUri);
		 setContentView(image);
		}
	}
	
	// �������ͼƬ
	private void HandleSendImageMutiply(Intent pIntent)
	{ 
		final ArrayList<Uri> imageUris= pIntent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
		if(imageUris!=null)
		{
			GridView gridView=new GridView(this);
			gridView.setColumnWidth(30);
			gridView.setNumColumns(GridView.AUTO_FIT);
			gridView.setAdapter(new GridAdapter(this, imageUris));
			
			gridView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					Intent _Intent=new Intent();
					_Intent.setAction(Intent.ACTION_SEND);
					_Intent.putExtra(Intent.EXTRA_STREAM, imageUris.get(position));
					_Intent.setType("iamge/*");
					startActivity(Intent.createChooser(_Intent, "����ͼƬ"));
				}
			});
		}
	}
	
	class GridAdapter extends BaseAdapter
	{
         
		private Context mContext;
		private List<Uri> list;
		public GridAdapter(Context pContext,List<Uri> pList)
		{
			this.mContext=pContext;
			this.list=pList;
		}
		
		@Override
		public int getCount() {
			return list!=null?list.size():0;
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView;
			if(convertView==null)
			{
				imageView=new ImageView(mContext);
                imageView.setPadding(8, 8, 8, 8);
			}else {
				 imageView = (ImageView)convertView;  
			}
			imageView.setImageURI(list.get(position));
			return imageView;
		}
		
	}
}
