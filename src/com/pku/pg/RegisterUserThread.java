package com.pku.pg;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

public class RegisterUserThread extends Thread{
	private String userStr;
	private SharedPreferences sp;
//	private OnSucRegisterListener onSucRegisterListener;

	public RegisterUserThread(String userStr,SharedPreferences sp){
		this.userStr = userStr;
		this.sp = sp;
	}
//	public interface OnSucRegisterListener {
//		public void onSucRegister();
//	}
//	public void setOnSucRegisterListener(OnSucRegisterListener listener) {
//		onSucRegisterListener = listener;
//	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		String path = "http://162.105.76.252:8082/api/insertPatient";
		String enc = "UTF-8";
		Map<String, String> params = new HashMap<String,String>();
		params.put("user", userStr);	
		System.out.println("userStr:"+params.toString());
		try {
			System.out.println("ע���û�");
			if(HttpRequest.sendPostRequest(path, params, enc)){
				System.out.println("POST�û����ݳɹ�");
				JSONObject jsonObj = new JSONObject(HttpRequest.reply);
				int device_exist = jsonObj.getInt("device_exist");
				int name_unique = jsonObj.getInt("name_unique");
				int mobile_unique = jsonObj.getInt("mobile_unique");
				int IMSI_unique = jsonObj.getInt("IMSI_unique");
				if(device_exist == 1&&name_unique == 1&&mobile_unique == 1&&IMSI_unique==1){
					sp.edit().putString("registerUserStr", userStr).commit();
					//������ʾ	
				}
//				onSucRegisterListener.onSucRegister();				
			}else System.out.println("ע���û�ʧ��");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
//			Log.e("RegisterUserThread","ע���û�ʧ��");
		}
		
	}

}

