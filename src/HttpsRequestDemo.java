import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;

public class HttpsRequestDemo {
	// ̫���Ĳ��Ի�����ַ
	private String targetURL = "http://112.64.185.187/jttpitx/itxsvc/param";
	// �汾��ʶ���̶�ֵ 3
	private String messageRouter = "3";
	// ���������룬��̫��ָ����ÿ���̻���һ��
	private String partnerCode = "WESTSH";
	// ҵ��Э�飬�̶�ֵ CPIC_ECOM
	private String documentProtocol = "CPIC_ECOM";

	private String a = "";

	private String sentHttpPostRequest(String requestMsg) throws Exception {
		HttpClient httpclient = new DefaultHttpClient();
		
		// ��������֤�鲢ע��SSLSocketFactory
		registerSSLSocketFactory(httpclient);
		
		// ���ó�ʱʱ��
		int timeout = 60000;
		HttpConnectionParams.setSoTimeout(httpclient.getParams(), timeout);

		// ע�⣺������post��ʽ��������
		HttpPost httppost = new HttpPost(targetURL);

		// �����������
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		// �汾��ʶ
		params.add(new BasicNameValuePair("messageRouter", messageRouter));
		// ҵ�������
		params.add(new BasicNameValuePair("tradingPartner", partnerCode));
		// ҵ��Э��
		params.add(new BasicNameValuePair("documentProtocol", documentProtocol));
		// xml������
		params.add(new BasicNameValuePair("requestMessage", requestMsg));

		// ע�⣺���������UTF-8
		HttpEntity request = new UrlEncodedFormEntity(params, "UTF-8");
		httppost.setEntity(request);

		// ��������Ϊxml����ʹ��xml�������߶Է������ݽ��н���
		HttpResponse httpResponse = httpclient.execute(httppost);
		HttpEntity entity = httpResponse.getEntity();
		String result = null;
		if (entity != null) {
			result = EntityUtils.toString(entity);
		}

		return result;
	}
	
	private void registerSSLSocketFactory(HttpClient httpclient) throws Exception {
		KeyStore trustStore  = KeyStore.getInstance(KeyStore.getDefaultType());   
		// ע�⣺�뽫����֤���·����E:\\cer\\cpic_jttp.keystore�����������汾�ļ��ľ���·��
        FileInputStream instream = new FileInputStream(new File("E:\\Program Files\\Java\\jdk1.7.0_21\\bin\\cpic_jttp.keystore")); 
        try {
            trustStore.load(instream, "cpicJttp".toCharArray());
        } finally {
            instream.close();
        }
        
        SSLSocketFactory socketFactory = new SSLSocketFactory(trustStore);
        Scheme sch = new Scheme("https", socketFactory, 443);
        httpclient.getConnectionManager().getSchemeRegistry().register(sch);
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		HttpsRequestDemo requestDemo = new HttpsRequestDemo();

		// xml�����ģ���ϸ�ı��Ľṹ�뿼�����׵���ϸ����ʾ��
		String requestMsg = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"    +
				"<request>                                                                                                                       "+
				"  <head>                                                                                                                        "+
				"    <partnerCode>WESTSH</partnerCode>                                                                                           "+
				"    <transactionCode>106002</transactionCode>                                                                                   "+
				"    <messageId>19466ba0-e9RE6-80c8-809d50ca2f59</messageId>                                                                 "+
				"    <transactionEffectiveDate>2015-12-04 15:06:27</transactionEffectiveDate>                                                    "+
				"    <user>WESTSH</user>                                                                                                         "+
				"    <password>UJNR/UNX9u7FiBqfaSRyoEiuOkHWbmbQQbOUOi6vNXn9B+ZeA2Z0INZlMv0DU+zGjI4GgC2UTPOOl/zJGPjpAQ==</password>               "+
				"  </head>                                                                                                                       "+
				"  <body>                                                                                                                        "+
				"    <QueryApplicationPremiumRequest>                                                                                            "+
				"      <ApplicationBasicInfo>                                                                                                    "+
				"        <litigationArbitration>1</litigationArbitration>                                                                        "+
				"        <branchCode>3090100</branchCode>                                                                                        "+
				"        <ip/>                                                                                                                   "+
				"        <usbKey/>                                                                                                               "+
				"        <arbitrationOrgName/>                                                                                                   "+
				"        <cardNo/>                                                                                                               "+
				"        <payWay/>                                                                                                               "+
				"        <terminalNo>TEST_3090100</terminalNo>                                                                                   "+
				"        <payChannel/>                                                                                                           "+
				"        <issuecode/>                                                                                                            "+
				"      </ApplicationBasicInfo>                                                                                                   "+
				"      <Applicant>                                                                                                               "+
				"        <name/>                                                                                                                 "+
				"        <natureType/>                                                                                                           "+
				"        <certificateCode/>                                                                                                      "+
				"        <certificateType/>                                                                                                      "+
				"        <organizationLinkman/>                                                                                                  "+
				"        <telephone/>                                                                                                            "+
				"        <address/>                                                                                                              "+
				"        <postalCode/>                                                                                                           "+
				"        <email/>                                                                                                                "+
				"        <bankAccountName/>                                                                                                      "+
				"        <benefitReceptionBank/>                                                                                                 "+
				"        <bankAccount/>                                                                                                          "+
				"        <fixedTelephone/>                                                                                                       "+
				"        <otherInfo/>                                                                                                            "+
				"      </Applicant>                                                                                                              "+
				"      <InsuredPerson>                                                                                                           "+
				"        <name/>                                                                                                                 "+
				"        <natureType/>                                                                                                           "+
				"        <customerLevel/>                                                                                                        "+
				"        <certificateCode/>                                                                                                      "+
				"        <certificateType/>                                                                                                      "+
				"        <bankAccount/>                                                                                                          "+
				"        <organizationLinkman/>                                                                                                  "+
				"        <telephone/>                                                                                                            "+
				"        <address/>                                                                                                              "+
				"        <postalCode/>                                                                                                           "+
				"        <email/>                                                                                                                "+
				"        <benefitReceptionBank/>                                                                                                 "+
				"        <bankAccountName/>                                                                                                      "+
				"        <fixedTelephone/>                                                                                                       "+
				"        <otherInfo/>                                                                                                            "+
				"      </InsuredPerson>                                                                                                          "+
				"      <Claimant>                                                                                                                "+
				"        <name/>                                                                                                                 "+
				"        <natureType/>                                                                                                           "+
				"        <certificateCode/>                                                                                                      "+
				"        <certificateType/>                                                                                                      "+
				"        <bankAccount/>                                                                                                          "+
				"        <organizationLinkman/>                                                                                                  "+
				"        <telephone/>                                                                                                            "+
				"        <address/>                                                                                                              "+
				"        <postalCode/>                                                                                                           "+
				"        <email/>                                                                                                                "+
				"        <benefitReceptionBank/>                                                                                                 "+
				"        <bankAccountName/>                                                                                                      "+
				"        <firstbeneFiciary/>                                                                                                     "+
				"      </Claimant>                                                                                                               "+
				"      <VehicleLicenseOwner>                                                                                                     "+
				"        <name>vtvv</name>                                                                                                       "+
				"        <natureType>1</natureType>                                                                                              "+
				"        <certificateCode/>                                                                                                      "+
				"        <certificateType/>                                                                                                      "+
				"      </VehicleLicenseOwner>                                                                                                    "+
				"      <InsuredVehicle>                                                                                                          "+
				"        <vin>VTCCRTVBYJHBTBTBY</vin>                                                                                            "+
				"        <engineNo>BHB</engineNo>                                                                                                "+
				"        <driveArea>2</driveArea>                                                                                                "+
				"        <engineCapacity>1598</engineCapacity>                                                                                   "+
				"        <makerModel>����HFC6450M2T���Ϳͳ�</makerModel>                                                                         "+
				"        <seatCount>5</seatCount>                                                                                                "+
				"        <licenseType>01</licenseType>                                                                                           "+
				"        <vehicleLicense>³C09876</vehicleLicense>                                                                               "+
				"        <vehiclePurpose>01</vehiclePurpose>                                                                                     "+
				"        <vehicleUsage1>101</vehicleUsage1>                                                                                      "+
				"        <vehicleUsage2/>                                                                                                        "+
				"        <vehicleVariety1>01</vehicleVariety1>                                                                                   "+
				"        <vehicleVariety2/>                                                                                                      "+
				"        <registerDate>2003-05-19</registerDate>                                                                                 "+
				"        <licenseColor>1</licenseColor>                                                                                          "+
				"        <averageMileage/>                                                                                                       "+
				"        <specialVehicle/>                                                                                                       "+
				"        <moldCharacterCode>JHAEAD0017</moldCharacterCode>                                                                       "+
				"        <specialCarFlag/>                                                                                                       "+
				"        <noDamageYears/>                                                                                                        "+
				"        <transferDate/>                                                                                                         "+
				"        <loanVehicleFlag>0</loanVehicleFlag>                                                                                    "+
				"        <glassManufacturer/>                                                                                                    "+
				"        <carryingCapacity/>                                                                                                     "+
				"        <driveAreaName/>                                                                                                        "+
				"        <purchasePrice>107800.00</purchasePrice>                                                                                "+
				"        <appointedRepairFactoryPremiumRate/>                                                                                    "+
				"        <fuelType/>                                                                                                             "+
				"        <purchaseinvoicesDate/>                                                                                                 "+
				"        <emptyWeight/>                                                                                                          "+
				"        <currentValue>20000.00</currentValue>                                                                                   "+
				"        <newCarFlag/>                                                                                                           "+
				"        <vehicleExamineClosingDay/>                                                                                             "+
				"        <VehicleInspection/>                                                                                                    "+
				"        <VehicleRegisterAddress/>                                                                                               "+
				"        <globalType/>                                                                                                           "+
				"        <VehiclePower/>                                                                                                         "+
				"      </InsuredVehicle>                                                                                                         "+
				"      <AppointedDriverList>                                                                                                     "+
				"        <personName/>                                                                                                           "+
				"        <personSex/>                                                                                                            "+
				"        <personAge/>                                                                                                            "+
				"        <driveLicense/>                                                                                                         "+
				"        <drivenYear/>                                                                                                           "+
				"        <driveLicenseRegiseterDate/>                                                                                            "+
				"        <driveType/>                                                                                                            "+
				"        <registrationNumber/>                                                                                                   "+
				"      </AppointedDriverList>                                                                                                    "+
				"      <AutoComprenhensiveInsuranceProduct>                                                                                      "+
				"        <efficientDate>2015-12-05 00:00:00</efficientDate>                                                                      "+
				"        <terminationDate>2016-12-05 00:00:00</terminationDate>                                                                  "+
				"        <policyNo/>                                                                                                             "+
				"        <applicationNo/>                                                                                                        "+
				"        <trafficIllegalTimesLastYear/>                                                                                          "+
				"        <insuranceQueryCode/>                                                                                                   "+
				"        <insurancePreConfirmCode/>                                                                                              "+
				"        <insuranceConfirmCode/>                                                                                                 "+
				"        <question/>                                                                                                             "+
				"        <answer/>                                                                                                               "+
				"        <sumInsured/>                                                                                                           "+
				"        <standardPremium/>                                                                                                      "+
				"        <policyPremium/>                                                                                                        "+
				"        <floatingRate/>                                                                                                         "+
				"        <UWResult/>                                                                                                             "+
				"        <SpecialTermItem/>                                                                                                      "+
				"        <agricultureRelated/>                                                                                                   "+
				"        <insuranceProductKind/>                                                                                                 "+
				"        <queryApplicationPremiumCode/>                                                                                          "+
				"        <saveApplicationCode/>                                                                                                  "+
				"        <VehicleClaimInfo/>                                                                                                     "+
				"        <NoClaimAdjustReason/>                                                                                                  "+
				"        <NoLoyaltyAdjustReason/>                                                                                                "+
				"        <DamageLossCoverage>                                                                                                    "+
				"          <sumInsured>50000.00</sumInsured>                                                                                            "+
				"          <premiumRate/>                                                                                                        "+
				"          <fixedPremium/>                                                                                                       "+
				"          <tablePremium/>                                                                                                       "+
				"          <tablePremiumB/>                                                                                                      "+
				"          <changeAmount/>                                                                                                       "+
				"          <standardPremium/>                                                                                                    "+
				"          <policyPremium/>                                                                                                      "+
				"          <annualPremium/>                                                                                                      "+
				"          <floatingRate/>                                                                                                       "+
				"          <totalPolicyPremium/>                                                                                                 "+
				"          <Additional>                                                                                                          "+
				"            <choosedDeductible/>                                                                                                "+
				"            <claimLimitPerDay/>                                                                                                 "+
				"            <maxClaimDays/>                                                                                                     "+
				"          </Additional>                                                                                                         "+
				"        </DamageLossCoverage>                                                                                                   "+
				"      </AutoComprenhensiveInsuranceProduct>                                                                                     "+
				"      <OriginalVehicleLibData>                                                                                                  "+
				"        <MakerModel/>                                                                                                           "+
				"        <MoldName/>                                                                                                             "+
				"        <PurchasePrice/>                                                                                                        "+
				"        <JingYouFlag/>                                                                                                          "+
				"        <RiskFlagCode/>                                                                                                         "+
				"      </OriginalVehicleLibData>                                                                                                 "+
				"    </QueryApplicationPremiumRequest>                                                                                           "+
				"  </body>                                                                                                                       "+
				"</request>                                                                                                                      ";

		try {
			String result = requestDemo.sentHttpPostRequest(requestMsg);
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}


