package com.haqueit.mpos.app.cardinfo.utils;

public class UIMessage {
    public static String exit_dialog_title;
    public static String exit_dialog_message;
    public static String exit_dialog_positive;
    public static String exit_dialog_negative;
    
    public static String device_list_title;
    public static String device_list_positive;
    public static String device_list_negative;
    
    public static String show_result_text;
    public static String click_button;
    
    public static String init_device_success;
    public static String init_device_fail;
    
    public static String donot_select_device;
    public static String donot_connect_device;
    public static String selected_device;
    public static String connected_device;
    public static String scanning_device;
    public static String connecting_device;
    
    public static String connected_device_success;
    public static String connected_device_fail;
    public static String disconnected_device_success;
    public static String disconnected_device_fail;
    
    public static String update_TDK;
    public static String update_PIK;
    public static String update_MAK;
    public static String update_working_key_fail;
    public static String read_card_fail;
    public static String calculate_mac_fail;
    public static String please_enter_password;
    public static String downGrade_transaction;
    
    public static void setMessage(int languageID) {
        if (languageID == 0) {
            exit_dialog_title = "��ʾ";
            exit_dialog_message = "ȷ���˳����ߣ�";
            exit_dialog_positive = "ȷ��";
            exit_dialog_negative = "ȡ��";
            device_list_title = "ɨ�赽�������豸";
            device_list_positive = "����ɨ��";
            device_list_negative = "ȷ��";
            show_result_text = "�����ʾ����";
            click_button = "������ť";
            init_device_success = "��ʼ���ɹ�";
            init_device_fail = "��ʼ��ʧ��";
            donot_select_device = "û��ѡ���豸";
            donot_connect_device = "��ǰû�������豸";
            selected_device = "��ѡ���豸";
            connected_device = "�������豸";
            scanning_device = "����ɨ���豸";
            connecting_device = "���������豸";
            connected_device_success = "�����豸�ɹ�";
            connected_device_fail = "�����豸ʧ��";
            disconnected_device_success = "�Ͽ��豸�ɹ�";
            disconnected_device_fail = "�Ͽ��豸ʧ��";
            update_TDK = "���´ŵ���Կ";
            update_PIK = "����pin��Կ";
            update_MAK = "����mac��Կ";
            update_working_key_fail = "���¹�����Կʧ��";
            read_card_fail = "ˢ��û�гɹ�";
            calculate_mac_fail = "����macʧ��";
            please_enter_password = "����������";
            downGrade_transaction = "������������";
        } else if (languageID == 1) {
            exit_dialog_title = "��ʾ";
            exit_dialog_message = "�_�J�˳����ߣ�";
            exit_dialog_positive = "�_�J";
            exit_dialog_negative = "ȡ��";
            device_list_title = "���赽���{���O��";
            device_list_positive = "������";
            device_list_negative = "�_��";
            show_result_text = "�Y���@ʾ�^��";
            click_button = "�Γ����o";
            init_device_success = "��ʼ���ɹ�";
            init_device_fail = "��ʼ��ʧ��";
            donot_select_device = "�]���x���O��";
            donot_connect_device = "��ǰ�]���B���O��";
            selected_device = "���x���O��";
            connected_device = "���B���O��";
            scanning_device = "���ڒ����O��";
            connecting_device = "�����B���O��";
            connected_device_success = "�B���O��ɹ�";
            connected_device_fail = "�B���O��ʧ��";
            disconnected_device_success = "���_�O��ɹ�";
            disconnected_device_fail = "���_�O��ʧ��";
            update_TDK = "���´ŵ����";
            update_PIK = "����pin���";
            update_MAK = "����mac���";
            update_working_key_fail = "���¹������ʧ��";
            read_card_fail = "ˢ���]�гɹ�";
            calculate_mac_fail = "Ӌ��macʧ��";
            please_enter_password = "Ոݔ���ܴa";
            downGrade_transaction = "�l����������";
        } else if (languageID == 2) {
            exit_dialog_title = "Prompt";
            exit_dialog_message = "Confirm exit the tool?";
            exit_dialog_positive = "Confirm";
            exit_dialog_negative = "Cancel";
            device_list_title = "Scan to Bluetooth devices";
            device_list_positive = "Rescan";
            device_list_negative = "Confirm";
            click_button = "Click on the button";
            init_device_success = "Successful initialization";
            init_device_fail = "Failed to initialize";
            show_result_text = "Results display area";
            donot_select_device = "No device is selected";
            donot_connect_device = "Not currently connected device";
            selected_device = "Selected device";
            connected_device = "Connected device";
            scanning_device = "Scanning Device";
            connecting_device = "Connecting device";
            connected_device_success = "Connected device successfully";
            connected_device_fail = "Connect device failed";
            disconnected_device_success = "Disconnect device successfully";
            disconnected_device_fail = "Disconnect device failed";
            update_TDK = "Update track key";
            update_PIK = "Update pin key";
            update_MAK = "Update mac key";
            update_working_key_fail = "Update the work key failed";
            read_card_fail = "Credit card without success";
            calculate_mac_fail = "Calculation mac failed";
            please_enter_password = "Please enter password";
            downGrade_transaction = "Demotion transaction occurred";
        }
    }
    
}
