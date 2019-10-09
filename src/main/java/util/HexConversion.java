package util;
/**
 * 二进制转十六进制
 */
public class HexConversion {
    static String bytesToHex(byte[] bytes){
        StringBuffer str=new StringBuffer();
        int num;
        for (int i=0;i<bytes.length;i++){
            num=bytes[i];//一字节8位
            if(num<0){
                num+=256;
            }
            if(num<16){
                str.append("0");
            }
            str.append(Integer.toHexString(num));
        }
        return str.toString().toUpperCase();
    }
}
