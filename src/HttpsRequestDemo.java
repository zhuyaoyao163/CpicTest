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
	// 太保的测试环境地址
	private String targetURL = "http://112.64.185.187/jttpitx/itxsvc/param";
	// 版本标识，固定值 3
	private String messageRouter = "3";
	// 合作伙伴编码，由太保指定，每个商户不一样
	private String partnerCode = "WESTSH";
	// 业务协议，固定值 CPIC_ECOM
	private String documentProtocol = "CPIC_ECOM";

	private String a = "";

	private String sentHttpPostRequest(String requestMsg) throws Exception {
		HttpClient httpclient = new DefaultHttpClient();
		
		// 导入数字证书并注册SSLSocketFactory
		registerSSLSocketFactory(httpclient);
		
		// 设置超时时间
		int timeout = 60000;
		HttpConnectionParams.setSoTimeout(httpclient.getParams(), timeout);

		// 注意：必须以post方式发送请求
		HttpPost httppost = new HttpPost(targetURL);

		// 设置请求参数
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		// 版本标识
		params.add(new BasicNameValuePair("messageRouter", messageRouter));
		// 业务伙伴代码
		params.add(new BasicNameValuePair("tradingPartner", partnerCode));
		// 业务协议
		params.add(new BasicNameValuePair("documentProtocol", documentProtocol));
		// xml请求报文
		params.add(new BasicNameValuePair("requestMessage", requestMsg));

		// 注意：编码必须是UTF-8
		HttpEntity request = new UrlEncodedFormEntity(params, "UTF-8");
		httppost.setEntity(request);

		// 返回内容为xml，请使用xml解析工具对返回内容进行解析
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
		// 注意：请将数字证书的路径（E:\\cer\\cpic_jttp.keystore）换成您保存本文件的具体路径
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

		// xml请求报文，详细的报文结构请考各交易的详细报文示例
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
				"        <makerModel>江淮HFC6450M2T轻型客车</makerModel>                                                                         "+
				"        <seatCount>5</seatCount>                                                                                                "+
				"        <licenseType>01</licenseType>                                                                                           "+
				"        <vehicleLicense>鲁C09876</vehicleLicense>                                                                               "+
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


