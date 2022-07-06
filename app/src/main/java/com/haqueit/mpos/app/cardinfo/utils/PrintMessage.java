package com.haqueit.mpos.app.cardinfo.utils;

public class PrintMessage {
	
	public static String merchantName;
	public static String merchantNo;
	public static String terminalNo;
	public static String operatorNo;
	public static String cardNo;
	public static String creditCompanyCode;
	public static String bankCode;
	public static String merchantCode;
	public static String transactionType;
	public static String cardValidThru;
	public static String batchNo;
	public static String voucherNo;
	public static String authorizeNo;
	public static String tradeTime ;
	public static String tradeDate ;
	public static String referenceNo;
	public static String tradeAmount;
	public static String icData55;
	public static String barCode;// ������
	public static String QRCode;// ��ά��
	public static String currencyType;// ����
	public static String additionalData;
	
	public static String platformDiscountAmount;//ƽ̨֧���Żݽ��
	public static String memberDiscountAmount;//��Ա�Żݽ��
	public static String actualAmount;//ʵ��֧�����
	public static String memberNo;//��Ա��
	public static String platformName;//ƽ̨����
	public static String tradeType;//��������
	public static String menberDiscountType;//��Ա�Ż�����
	public static String payAccount;//֧���˻�
	public static String tradeVoucherNo;//������ˮ��(˽��)
	
	public static byte printType;
	
    public static void setMessage(int languageID){
    			
    	if(languageID == 0){
			merchantName = "�人������Ϣ��ҵ�ɷ����޹�˾";
			merchantNo = "001210381234567";
			terminalNo = "00123456";
			operatorNo = "01";
			cardNo = "6222003202100054698F";
			creditCompanyCode = "622";
			bankCode = "62284826   ";
			merchantCode = "00965840   ";
			transactionType = "����";
			cardValidThru = "2512";
			batchNo = "002018";
			voucherNo = "000018";
			authorizeNo = "008295";
			tradeTime = "194923";
			tradeDate = "20151231";
			referenceNo = "171820187826";
			tradeAmount = "150";
			icData55 = null;
			barCode = "654345";// ������
			QRCode = "abc12345";// ��ά��
			currencyType = "�����";// ����
			additionalData = "�Һø��˰��ø��ˣ��Һø��˰��ø��ˣ��Һø��˰��ø��ˣ��Һø��˰��ø��ˣ��Һø��˰��ø��ˣ��Һø��˰��ø��ˣ��Һø��˰��ø��ˣ��Һø��˰��ø��ˣ�";
			
			platformDiscountAmount = "4.50";//ƽ̨֧���Żݽ��
			memberDiscountAmount = "2.00";//��Ա�Żݽ��
			actualAmount = "10.00";//ʵ��֧�����
			memberNo = "3135134162";//��Ա��
			platformName = "֧����";//ƽ̨����
			tradeType = "��ά��";//��������
			menberDiscountType = "�Ż�ȯ";//��Ա�Ż�����
			payAccount = "123456789987654632";//֧���˻�
			tradeVoucherNo = "000094";//������ˮ��(˽��)
			
			printType = (byte)0x02;
    	}else if(languageID == 1){
			merchantName = "��h������Ϣ�a�I�ɷ����޹�˾";
			merchantNo = "001210381234567";
			terminalNo = "00123456";
			operatorNo = "01";
			cardNo = "6222003202100054698F";
			creditCompanyCode = "622";
			bankCode = "62284826   ";
			merchantCode = "00965840   ";
			transactionType = "���M";
			batchNo = "002018";
			voucherNo = "000018";
			authorizeNo = "008295";
			tradeTime = "194923";
			tradeDate = "20151231";
			referenceNo = "171820187826";
			tradeAmount = "150";
			icData55 = null;
			barCode = "654345";// ������
			QRCode = "abc12345";// ��ά��
			currencyType = "�����";// ����
			additionalData = "�Һø��d���ø��d���Һø��d���ø��d���Һø��d���ø��d���Һø��d���ø��d���Һø��d���ø��d���Һø��d���ø��d���Һø��d���ø��d���Һø��d���ø��d��";
			
			platformDiscountAmount = "4.50";//ƽ̨֧���Żݽ��
			memberDiscountAmount = "2.00";//��Ա�Żݽ��
			actualAmount = "10.00";//ʵ��֧�����
			memberNo = "3135134162";//��Ա��
			platformName = "֧����";//ƽ̨����
			tradeType = "���S�a";//��������
			menberDiscountType = "����ȯ";//��Ա�Ż�����
			payAccount = "123456789987654632";//֧���˻�
			tradeVoucherNo = "000094";//������ˮ��(˽��)
			
			printType = (byte)0x02;
    	}else if(languageID == 2){
			merchantName = "WuHan TianYu Information Industry Co.,Ltd";
			merchantNo = "001210381234567";
			terminalNo = "00123456";
			operatorNo = "01";
			cardNo = "6222003202100054698F";
			creditCompanyCode = "622";
			bankCode = "62284826   ";
			merchantCode = "00965840   ";
			transactionType = "SALE";
			batchNo = "002018";
			voucherNo = "000018";
			authorizeNo = "008295";
			tradeTime = "194923";
			tradeDate = "20151231";
			referenceNo = "171820187826";
			tradeAmount = "150";
			icData55 = null;
			barCode = "654345";// ������
			QRCode = "abc12345";// ��ά��
			currencyType = "RMB";// ����
			additionalData = "I am so happy��I am so happy��I am so happy��I am so happy��I am so happy��I am so happy��I am so happy��I am so happy��";
			platformDiscountAmount = "4.50";//ƽ̨֧���Żݽ��
			memberDiscountAmount = "2.00";//��Ա�Żݽ��
			actualAmount = "10.00";//ʵ��֧�����
			memberNo = "3135134162";//��Ա��
			platformName = "Alipay";//ƽ̨����
			tradeType = "QRCode";//��������
			menberDiscountType = "Coupon";//��Ա�Ż�����
			payAccount = "123456789987654632";//֧���˻�
			tradeVoucherNo = "000094";//������ˮ��(˽��)
			
			printType = (byte)0x01;
    	}
    
    }

}
