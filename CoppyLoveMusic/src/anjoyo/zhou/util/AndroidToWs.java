package anjoyo.zhou.util;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;



public class AndroidToWs {
	
	private static final String NAMESPACE = "http://musiccbh.aimo.anjoyo/";
	// ����WS��·��
	public static String SERVICEURL = "http://192.168.1.29:9999/MusicTest/musictestPort";
	public String GetUserWS(String methodName,int[] pageIndex) {
		// ����SoapObject���󣬲�ָ��WebService�������ռ�͵��õķ�����
		SoapObject request = new SoapObject(NAMESPACE, methodName);
		// ���õĺ�������в������������������Ҫ���ݵĲ��� ע��:��һ������ʹ��arg0 ��������������� arg1,arg2...
		if(pageIndex != null)
		{
			for (int i = 0; i < pageIndex.length; i++) {
				request.addProperty("arg"+i, pageIndex[i]);
			}
		}
		
		// ���ɵ���WebService������SOAP������Ϣ,��ָ��SOAP�İ汾
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		// envelope.setOutputSoapObject(request);
		// �ϱߵ�һ��ȼ����±ߵ���� ��SoapObject���󸳸�envelope����
		envelope.bodyOut = request;
		// ��ǰ��������Java WS ������Ҫ������.net WS
		envelope.dotNet = false;
		/*
		 * ���ﲻҪʹ�� AndroidHttpTransport ht = new AndroidHttpTransport(URL)��
		 * ����һ��Ҫ���ڵ���
		 * ����HttpTransportSE����ͨ��HttpTransportSE��Ĺ��췽������ָ��WebService��WSDL�ĵ���URL
		 */
		HttpTransportSE ht = new HttpTransportSE(SERVICEURL);
		try {
			// ����WS
			ht.call(null, envelope);
			if (envelope.getResponse() != null) {
				// ���WS��������ֵ��Ϣ
				return envelope.getResponse().toString();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return null;
	}
}
