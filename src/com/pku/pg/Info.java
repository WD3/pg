package com.pku.pg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class Info extends Activity implements OnClickListener {

	private TextView userName;
	private TextView userPhone;
	private TextView userHospital;
	private Context infoContext;
	static Activity activity;

	private String nurseName;
	private String nursePhone;
	private String relativeName;
	private String relativePhone;

	private String nursesStr;
	private String relativesStr;
	private String userStr;

	private EditText regUserMac;
	private EditText regUserName;
	private EditText regUserPhone;
	private EditText regUserHospital;
	private EditText regNurseName;
	private EditText regNursePhone;
	private EditText regRelativeName;
	private EditText regRelativePhone;

	private ScrollView infoScrollView;
	private ListViewForScrollView mListviewNurses;
	private ListViewForScrollView mListviewRelatives;

	private SharedPreferences infoSharedPreferences;
	private Editor editor;

	private ArrayList<HashMap<String, Object>> mListItemNurses;
	private ArrayList<HashMap<String, Object>> mListItemRelatives;

	private JSONArray nursesJsonArray;
	private JSONArray relativesJsonArray;
	private JSONArray userJsonArray;
	private SimpleAdapter mSimpleAdapter_Nurses;
	private SimpleAdapter mSimpleAdapter_Relatives;
	
	static boolean checkFlag;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		super.onCreate(savedInstanceState);
		setContentView(R.layout.info);

		infoScrollView = (ScrollView) findViewById(R.id.info_scrollview);
		infoScrollView.smoothScrollTo(0, 0);

		findViewById(R.id.info_btn_DeviceChanging).setOnClickListener(this);
		findViewById(R.id.info_btn_UserChange).setOnClickListener(this);
		findViewById(R.id.info_imageview_AddNurses).setOnClickListener(this);
		findViewById(R.id.info_imageview_AddRelative).setOnClickListener(this);
		findViewById(R.id.info_btn_MacCheck).setOnClickListener(this);
		findViewById(R.id.commit).setOnClickListener(this);
		
		
		regUserMac = (EditText)findViewById(R.id.info_textview_MacAddrss);
		
		ActionBar infoActionBar = this.getActionBar();

		infoActionBar.setHomeButtonEnabled(true);
		infoActionBar.setDisplayHomeAsUpEnabled(true);
		infoActionBar.setDisplayShowHomeEnabled(true);
		
		userJsonArray = new JSONArray();
		infoContext = Info.this;
		activity = this;
		infoSharedPreferences = infoContext.getSharedPreferences("UserInfo",Context.MODE_PRIVATE);
		editor = infoSharedPreferences.edit();
		// spclear();

		userName = (TextView) findViewById(R.id.info_textview_UserName);
		userPhone = (TextView) findViewById(R.id.info_textview_UserPhone);
		userHospital = (TextView) findViewById(R.id.info_textview_UserHospital);

		userName.setText(infoSharedPreferences.getString("userName", "������δд��"));
		userPhone.setText(infoSharedPreferences
				.getString("userPhone", "������δд��"));
		userHospital.setText(infoSharedPreferences.getString("geracomium",
				"������δд��"));
		// Log.i("User", "UserName:" + userName + "UserPhone:"
		// + userPhone+"Hospital"+userHospital);
		// Log.i("UserName sp",infoSharedPreferences.getString("UserName",
		// "null"));
		// Log.i("UserPhone SP",infoSharedPreferences.getString("UserPhone",
		// "null"));
		// Log.i("Hospital sp",infoSharedPreferences.getString("Hospital",
		// "null"));

		/*
		 * ����
		 * *******************************************************************
		 * **********************************************************����OnCreate
		 */
		mListviewNurses = (ListViewForScrollView) findViewById(R.id.info_list_Nurses); /* ����һ����̬���� */

		nursesStr = infoSharedPreferences.getString("nursesInfo", "");

		mListItemNurses = new ArrayList<HashMap<String, Object>>(); /* �������д������ */
		if (nursesStr.equals("")) {
			nurseName = "������ϵ��";
			nursePhone = "��δ�����Ϣ";
			HashMap<String, Object> map_nurses = new HashMap<String, Object>();
			map_nurses.put("ItemTitle", nurseName);
			map_nurses.put("ItemText", nursePhone);
			mListItemNurses.add(map_nurses);
			Log.e("====", "nurseName:" + nurseName + "NursePhon:" + nursePhone);
		} else {
			try {
				nursesJsonArray = new JSONArray(nursesStr);
				for (int i = 0; i < nursesJsonArray.length(); i++) {
					nurseName = nursesJsonArray.getJSONObject(i).getString("nurseName").toString();
					nursePhone = nursesJsonArray.getJSONObject(i).getString("nursePhone").toString();
					HashMap<String, Object> map_nurses = new HashMap<String, Object>();
					// map_nurses.put("ItemImage", R.drawable.choose);// ����ͼƬ
					map_nurses.put("ItemTitle", nurseName);
					map_nurses.put("ItemText", nursePhone);
					mListItemNurses.add(map_nurses);
					Log.e("NurseInfo", "nurseName:" + nurseName + "nursePhone:"
							+ nursePhone);
				}
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		mSimpleAdapter_Nurses = new SimpleAdapter(this, mListItemNurses,R.layout.list_nurses, new String[] { "ItemCheckbox","ItemTitle", "ItemText" }, new int[] {
						R.id.item_checkbox_Nurse, R.id.item_title_Nurse,
						R.id.item_info_Nurse }); // ���ݰ�

		mListviewNurses.setAdapter(mSimpleAdapter_Nurses);
		/* end */

		mListviewNurses.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
				// TODO Auto-generated method stub
				setTitle("��ѡ���˻�����Ա" + arg2);// ���ñ�������ʾ�������
				////////////////////////////////////////////////////////////////////////////////////////    ��ӵ����CheckBox�¼�
				mSimpleAdapter_Nurses.getItem(arg2);
				CheckBox checkBox = (CheckBox)findViewById(R.id.item_checkbox_Nurse);
				if(checkBox.isChecked()){
					checkBox.setChecked(false);
				}
				else {
					checkBox.setChecked(true);
				}
				Log.e("info", "ѡ����"+arg2);
			}

		});
		mListviewNurses.setOnItemLongClickListener(new OnItemLongClickListener() {
					private int position;

					@Override
					public boolean onItemLongClick(AdapterView<?> arg0,
							View arg1, int arg2, long arg3) {
						// TODO Auto-generated method stub
						position = arg2;
						new AlertDialog.Builder(infoContext)
								.setTitle("ȷ��ɾ��?")
								.setPositiveButton("ȷ��",
										new DialogInterface.OnClickListener() {

											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {
												// TODO Auto-generated method
												// stub
												// �����SharedPreferences
												JSONArray newJSONArray = new JSONArray();
												for (int i = 0; i < nursesJsonArray.length(); i++) {
													if (i != position) {
														try {
															newJSONArray.put(nursesJsonArray.getJSONObject(i));
														} catch (JSONException e) {
															// TODO
															// Auto-generated
															// catch block
															e.printStackTrace();
														}
													}
												}
												nursesJsonArray = newJSONArray;
												if (nursesJsonArray.length() == 0) {
													nursesStr = "";
												} else {
													nursesStr = nursesJsonArray.toString();
												}
												editor.putString("nursesInfo",nursesStr);
												editor.commit();
												mListItemNurses.remove(position);
												mSimpleAdapter_Nurses.notifyDataSetChanged();
											}
										}).setNegativeButton("ȡ��", null).show();

						return false;
					}
				});

		// Log.i("����ע��sp",
		// infoSharedPreferences.getString("NursesInfo","Nothing Doh!l"));

		/*
		 * ���� ************************************************************
		 * ******
		 * *******************************************************Relatives
		 * onCreate
		 */
		mListviewRelatives = (ListViewForScrollView) findViewById(R.id.info_list_Relatives); /* ����һ����̬���� */

		relativesStr = infoSharedPreferences.getString("relativesInfo", "");

		mListItemRelatives = new ArrayList<HashMap<String, Object>>(); /* �������д������ */
		if (relativesStr.equals("")) {
			relativeName = "������ϵ��";
			relativePhone = "��δ�����Ϣ";
			HashMap<String, Object> map_relatives = new HashMap<String, Object>();
			// map_relatives.put("ItemImage", R.drawable.choose);// ����ͼƬ
			map_relatives.put("ItemTitle", relativeName);
			map_relatives.put("ItemText", relativePhone);
			mListItemRelatives.add(map_relatives);
			Log.e("====", "RelativeName:" + relativeName + "RelativePhon:"
					+ relativePhone);
		} else {
			try {
				relativesJsonArray = new JSONArray(relativesStr);
				for (int i = 0; i < relativesJsonArray.length(); i++) {
					relativeName = relativesJsonArray.getJSONObject(i)
							.getString("relativeName").toString();
					relativePhone = relativesJsonArray.getJSONObject(i)
							.getString("relativePhone").toString();
					HashMap<String, Object> map_relatives = new HashMap<String, Object>();
					// map_relatives.put("ItemImage", R.drawable.choose);// ����ͼƬ
					map_relatives.put("ItemTitle", relativeName);
					map_relatives.put("ItemText", relativePhone);
					mListItemRelatives.add(map_relatives);
					Log.e("RelativeInfo", "RelativeName:" + relativeName
							+ "RelativePhone:" + relativePhone);
				}
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		mSimpleAdapter_Relatives = new SimpleAdapter(this, mListItemRelatives,
				R.layout.list_relatives, new String[] { "ItemImage",
						"ItemTitle", "ItemText" }, new int[] {
						R.id.list_imgbtn_Relative, R.id.list_title_Relative,
						R.id.list_info_Relative }); // ���ݰ�
		mListviewRelatives.setAdapter(mSimpleAdapter_Relatives);
		/* end */

		mListviewRelatives.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				setTitle("��ѡ��������" + arg2);// ���ñ�������ʾ�������
			}

		});
		mListviewRelatives.setOnItemLongClickListener(new OnItemLongClickListener() {
					private int position;

					@Override
					public boolean onItemLongClick(AdapterView<?> arg0,
							View arg1, int arg2, long arg3) {
						// TODO Auto-generated method stub
						position = arg2;
						new AlertDialog.Builder(infoContext)
								.setTitle("ȷ��ɾ��?")
								.setPositiveButton("ȷ��",
										new DialogInterface.OnClickListener() {

											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {
												// TODO Auto-generated method
												// stub
												// �����SharedPreferences
												JSONArray newJSONArray = new JSONArray();
												for (int i = 0; i < relativesJsonArray.length(); i++) {
													if (i != position) {
														try {
															newJSONArray.put(relativesJsonArray.getJSONObject(i));
														} catch (JSONException e) {
															// TODO
															// Auto-generated
															// catch block
															e.printStackTrace();
														}
													}
												}
												relativesJsonArray = newJSONArray;
												if (relativesJsonArray.length() == 0) {
													relativesStr = "";
												} else {
													relativesStr = relativesJsonArray
															.toString();
												}
												
												editor.putString(
														"relativesInfo",
														relativesStr);
												editor.commit();
												mListItemRelatives
														.remove(position);
												mSimpleAdapter_Relatives
														.notifyDataSetChanged();
											}
										}).setNegativeButton("ȡ��", null).show();

						return false;
					}
				});

		// Log.i("����ע��sp",
		// infoSharedPreferences.getString("RelativesInfo","Nothing Doh!l"));

		// onCreate����*******************************************onCreate����*******************************************onCreate����

	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;

		default:
			return false;

		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.info_btn_DeviceChanging:
			// ======================================================================�����豸
			// ��ť���
			// �����Ի���
			LayoutInflater inflater_DeviceChange = getLayoutInflater();
			View layout_DeviceChange = inflater_DeviceChange.inflate(
					R.layout.dialog_devicechange,
					(ViewGroup) findViewById(R.id.dialog_DeviceChange));
			
			new AlertDialog.Builder(this)
					.setTitle("����")
					.setView(layout_DeviceChange)
					.setPositiveButton("ȷ��",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									// �����SharedPreferences
									
									editor.putString("Mac", "");
									editor.putString("userName", "");
									editor.putString("userPhone", "");
									editor.putString("geracomium", "");
//									editor.putString("nursesInfo", "");
//									editor.putString("relativesInfo", "");
									editor.commit();

									refresh();

								}
							}).setNegativeButton("ȡ��", null).show();

			break;

		case R.id.info_btn_UserChange:
			// ======================================================================�û�ע��
			// ��ť���
			// �����Ի���
			LayoutInflater inflater_UserReg = getLayoutInflater();
			View layout_UserReg = inflater_UserReg.inflate(
					R.layout.user_reg_dialog,
					(ViewGroup) findViewById(R.id.user_reg_v01));
			regUserName = (EditText)layout_UserReg.findViewById(R.id.user_reg_EditText_name);
			regUserPhone = (EditText)layout_UserReg.findViewById(R.id.user_reg_EditText_phone);
			regUserHospital = (EditText)layout_UserReg.findViewById(R.id.user_reg_EditText_hospital);
			new AlertDialog.Builder(this)
					.setTitle("����дע����Ϣ")
					.setView(layout_UserReg)
					.setPositiveButton("ȷ��",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									// �����SharedPreferences
									JSONObject jsonObject = new JSONObject();
									try {
										jsonObject.put("ID", regUserMac.getText().toString());
										jsonObject.put("userName", regUserName.getText().toString());
										jsonObject.put("userPhone", regUserPhone.getText().toString());
										jsonObject.put("geracomium", regUserHospital.getText().toString());
										jsonObject.put("IMSI", "1111111111111111");
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									editor.putString("userStr", jsonObject.toString());
									editor.putString("userName",regUserName.getText().toString());
									/*
									 * editor.putInt("UserPhone",
									 * Integer.parseInt
									 * (regPhone.getText().toString()));
									 */
									editor.putString("userPhone", regUserPhone
											.getText().toString());
									editor.putString("geracomium",
											regUserHospital.getText()
													.toString());
									editor.commit();

									refresh();

								}
							}).setNegativeButton("ȡ��", null).show();

			break;

		case R.id.info_imageview_AddNurses:
			/*
			 * ==================================================================
			 * ==== ����ע��
			 */
			LayoutInflater inflater_NurseReg = getLayoutInflater();
			View layout_NurseReg = inflater_NurseReg.inflate(
					R.layout.nurses_reg_dialog,
					(ViewGroup) findViewById(R.id.nurse_reg_v01));
			regNurseName = (EditText) layout_NurseReg
					.findViewById(R.id.reg_NurseName_EditText_name);
			regNursePhone = (EditText) layout_NurseReg
					.findViewById(R.id.reg_NursePhone_EditText_phone);

			new AlertDialog.Builder(this)
					.setTitle("����дע����Ϣ")
					.setView(layout_NurseReg)
					.setNegativeButton("ȡ��", null)
					.setPositiveButton("ȷ��",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									/* �����Json */
									JSONObject jsonObject = new JSONObject();
									nurseName = regNurseName.getText().toString();
									nursePhone = regNursePhone.getText().toString();
									try {
										jsonObject.put("nurseName", nurseName);
										jsonObject.put("nursePhone", nursePhone);
										// nurseName =
										// jsonObject.getString("NurseName");
										// nursePhone =
										// jsonObject.getString("NursePhone");
										Log.d("jsonObjectд��", regNurseName.getText().toString());
										/* JsonArray */
										if (nursesJsonArray == null) {
											nursesJsonArray = new JSONArray();
										}
										nursesJsonArray.put(jsonObject);

										// /*array�����SharedPreferences*/

										nursesStr = nursesJsonArray.toString();

										editor.putString("nursesInfo",nursesStr);
										editor.commit();
										Log.i("����ע��Str", nursesStr);
										Log.i("����ע��SP", infoSharedPreferences.getString("nursesInfo","Nothing Doh!l"));
										
										HashMap<String, Object> map_nurses = new HashMap<String, Object>();
										map_nurses.put("ItemImage",R.drawable.choose);// ����ͼƬ
										map_nurses.put("ItemTitle", nurseName);
										map_nurses.put("ItemText", nursePhone);
										mListItemNurses.add(map_nurses);
										mSimpleAdapter_Nurses
												.notifyDataSetChanged();
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

								}
							}).show();

			break;

		case R.id.info_imageview_AddRelative:
			/*
			 * ==================================================================
			 * ==== ����ע��
			 */
			LayoutInflater inflater_RelativeReg = getLayoutInflater();
			View layout_RelativeReg = inflater_RelativeReg.inflate(
					R.layout.relatives_reg_dialog,
					(ViewGroup) findViewById(R.id.relative_reg_v01));
			regRelativeName = (EditText) layout_RelativeReg
					.findViewById(R.id.reg_RelativeName_EditText_name);
			regRelativePhone = (EditText) layout_RelativeReg
					.findViewById(R.id.reg_RelativePhone_EditText_phone);

			new AlertDialog.Builder(this)
					.setTitle("����дע����Ϣ")
					.setView(layout_RelativeReg)
					.setNegativeButton("ȡ��", null)
					.setPositiveButton("ȷ��",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									/* �����Json */
									JSONObject jsonObject = new JSONObject();
									relativeName = regRelativeName.getText()
											.toString();
									relativePhone = regRelativePhone.getText()
											.toString();
									try {
										jsonObject.put("relativeName",
												relativeName);
										jsonObject.put("relativePhone",
												relativePhone);
										// relativeName =
										// jsonObject.getString("RelativeName");
										// relativePhone =
										// jsonObject.getString("RelativePhone");
										Log.d("jsonObjectд��", regRelativeName
												.getText().toString());
										/* JsonArray */
										if (relativesJsonArray == null) {
											relativesJsonArray = new JSONArray();
										}
										relativesJsonArray.put(jsonObject);

										// /*array�����SharedPreferences*/

										relativesStr = relativesJsonArray
												.toString();

										editor.putString("relativesInfo",
												relativesStr);
										editor.commit();
										Log.i("����ע��Str", relativesStr);
										Log.i("����ע��SP", infoSharedPreferences
												.getString("relativesInfo",
														"Nothing Doh!l"));
//										if (mListItemRelatives.get(0)
//												.get("ItemTitle")
//												.equals("������ϵ��")) {
//											mListItemRelatives.remove(0);
//										}
										HashMap<String, Object> map_relatives = new HashMap<String, Object>();
										map_relatives.put("ItemImage",
												R.drawable.choose);// ����ͼƬ
										map_relatives.put("ItemTitle",
												relativeName);
										map_relatives.put("ItemText",
												relativePhone);
										mListItemRelatives.add(map_relatives);
										mSimpleAdapter_Relatives
												.notifyDataSetChanged();
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

								}
							}).show();

			break;
		case R.id.info_btn_MacCheck:
			checkFlag = true;
			Intent intent = new Intent(infoContext,BtService.class);
			String inputID = regUserMac.getText().toString();
			if(inputID.length()!=12)
//			regUserMac.setText("08:D4:2B:EF:57:AF");
//			regUserMac.setText("00:0E:EA:CA:06:05");
			if(regUserMac.getText().toString()!=null){
				Bundle bundle = new Bundle();
				bundle.putString("userName", userName.getText().toString());			
				bundle.putString("deviceID", regUserMac.getText().toString());
				bundle.putString("nurseTel", nursesStr);
				bundle.putString("relativesTel", relativesStr);
				bundle.putString("userTel", userPhone.getText().toString());
				intent.putExtras(bundle);
				infoContext.startService(intent);
			}			
			break;
		case R.id.commit:
			//ע���û�
			String registerUserStr = infoSharedPreferences.getString("registerUserStr", "registerNurseStr");
			String userStr = infoSharedPreferences.getString("userStr", "userStr");
			String newUserStr = null;
			try {
				JSONObject obj1 = new JSONObject(userStr);
				JSONObject obj2 = new JSONObject();
				obj2.put("user", obj1);
				newUserStr = obj2.toString();
			} catch (JSONException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			if(userStr!= "userStr"&&registerUserStr!= newUserStr){		
				RegisterUserThread thread = new RegisterUserThread(newUserStr,infoSharedPreferences);
				thread.start();	
			}
			
			//ע�Ụ��
			String registerNurseStr = infoSharedPreferences.getString("registerNurseStr", "registerNurseStr");
//			String nurseStr = infoSharedPreferences.getString("nurseStr", "nurseStr");
			String newNurseStr = null;
			nursesStr = infoSharedPreferences.getString("nursesInfo", "nursesInfo");
			JSONArray array = null;
			try {
				array = new JSONArray(nursesStr);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}			
			try {
				JSONObject obj3 = new JSONObject();
				obj3.put("ID", regUserMac.getText().toString());
				obj3.put("nursesInfo", array);
				JSONObject obj4 = new JSONObject();
				obj4.put("nurse", obj3);
				newNurseStr = obj4.toString();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			if(nursesStr!= "nursesInfo"&&registerNurseStr!= newNurseStr){				
				RegisterNurseThread thread = new RegisterNurseThread(newNurseStr,infoSharedPreferences);
				thread.start();
			}
			
			//ע������
			String registerRelativesStr = infoSharedPreferences.getString("registerRelativesStr", "registerRelativesStr");
//			String nurseStr = infoSharedPreferences.getString("nurseStr", "nurseStr");
			String newRelativeStr = null;
			relativesStr = infoSharedPreferences.getString("relativesInfo", "relativesInfo");
			JSONArray array1 = null;
			try {
				array1 = new JSONArray(relativesStr);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				JSONObject obj5 = new JSONObject();
				obj5.put("ID", regUserMac.getText().toString());
				obj5.put("relativesInfo", array1);
				JSONObject obj6 = new JSONObject();
				obj6.put("relative", obj5);
				newRelativeStr = obj6.toString();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if(relativesStr!= "relativesInfo"&&registerRelativesStr!= newRelativeStr){				
				RegisterRelativesThread thread = new RegisterRelativesThread(newRelativeStr,infoSharedPreferences);
				thread.start();
			}
			
			JSONObject alertJson = new JSONObject();
			try {
				alertJson.put("ID", "123");
				alertJson.put("type", 1);
				alertJson.put("mobile", "15600000000");
				alertJson.put("recordTime", "2014-08-15 16:03");
				JSONObject newAlertJson = new JSONObject();
				newAlertJson.put("alert", alertJson);
				String alertStr = newAlertJson.toString();
				UploadAlertThread thread = new UploadAlertThread(alertStr);
				thread.start();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		default:
			break;
		}
	}

	private void refresh() {
		finish();
		Intent intent = new Intent(infoContext, Info.class);
		startActivity(intent);
	}

	public void spclear() {
		SharedPreferences.Editor editor = infoSharedPreferences.edit();
		editor.clear();
		editor.commit();
	}

}
