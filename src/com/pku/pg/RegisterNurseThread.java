package com.pku.pg;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import android.content.SharedPreferences;
import android.util.Log;

public class RegisterNurseThread extends Thread{
	private String nurseStr;
	private SharedPreferences sp;

	public RegisterNurseThread(String nurseStr,SharedPreferences sp){
		this.nurseStr = nurseStr;
		this.sp = sp;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		String path = "http://162.105.76.252:8082/api/insertNurse";
		String enc = "UTF-8";
		Map<String, String> params = new HashMap<String,String>();
		params.put("nurse", nurseStr);
		System.out.println("nurseStr:"+params.toString());
		try {
			System.out.println("ע�Ụ��");
			if(HttpRequest.sendPostRequest(path, params, enc)){
				System.out.println("POST�������ݳɹ�");
				JSONObject jsonObj = new JSONObject(HttpRequest.reply);
				int device_exist = jsonObj.getInt("device_exist");
				int user_exist = jsonObj.getInt("user_exist");
				if(device_exist == 1&&user_exist == 1){
					sp.edit().putString("registerNurseStr", nurseStr).commit();
					//������ʾ										
				}
			}else System.out.println("ע�Ụ��ʧ��");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
//			Log.e("RegisterNurseThread", "ע�Ụ��ʧ��");
		}
		
	}

}


