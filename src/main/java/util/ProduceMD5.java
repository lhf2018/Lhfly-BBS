package util;

import java.security.MessageDigest;

/**
 * 生成MD5值
 */
public class ProduceMD5 {

    public static String getMD5(String password) {
        String MD5="";
        try {
            MessageDigest md=MessageDigest.getInstance("MD5");
            byte[] messageByte=password.getBytes("UTF-8");
            byte[] md5Byte=md.digest(messageByte);
            MD5=HexConversion.bytesToHex(md5Byte);
        }catch (Exception e){
            e.printStackTrace();
        }
        return MD5;
    }
}
