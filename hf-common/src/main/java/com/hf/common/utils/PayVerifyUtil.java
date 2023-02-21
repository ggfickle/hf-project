package com.hf.common.utils;

import org.apache.commons.codec.binary.Hex;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.*;

/**
 * 支付签名验证
 */
public class PayVerifyUtil {

    /**
     * 微信支付回调验签
     * @param xmlResult
     * @param apiKey
     * @return
     * @throws Exception
     */
    public static boolean wechatVerifySign(String xmlResult, String apiKey) throws Exception {
        // 1. 将XML格式的字符串转换成Map格式
        Map<String, String> params = new HashMap<>();
        Document doc = DocumentHelper.parseText(xmlResult);
        Element root = doc.getRootElement();
        List<Element> elements = root.elements();
        for (Element e : elements) {
            params.put(e.getName(), e.getText());
        }

        // 2. 将Map中的所有参数按照字典序排序，并拼接成一个字符串
        List<String> keys = new ArrayList<>(params.keySet());
        Collections.sort(keys);
        StringBuilder sb = new StringBuilder();
        for (String key : keys) {
            String value = params.get(key);
            if (value != null && !"".equals(value) && !"sign".equals(key) && !"key".equals(key)) {
                sb.append(key).append("=").append(value).append("&");
            }
        }
        sb.append("key=").append(apiKey);
        String content = sb.toString();

        // 3. 使用MD5算法对拼接好的字符串进行加密
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] digest = md.digest(content.getBytes("UTF-8"));
        String signValue = Hex.encodeHexString(digest).toUpperCase();

        // 4. 比对签名值
        String sign = params.get("sign");
        return signValue.equals(sign);
    }

    /**
     * 支付宝支付回调验签
     * @param params
     * @param sign
     * @param privateKey
     * @param charset
     * @return
     * @throws Exception
     */
    public static boolean aliPayVerifySign(Map<String, String> params, String sign, String privateKey, String charset) throws Exception {
        // 1. 将参数按照名称排序
        List<String> keys = new ArrayList<>(params.keySet());
        Collections.sort(keys);

        // 2. 将所有参数拼接成字符串
        StringBuilder sb = new StringBuilder();
        for (String key : keys) {
            String value = params.get(key);
            sb.append(key).append("=").append(value).append("&");
        }
        String content = sb.toString();
        if (content.endsWith("&")) {
            content = content.substring(0, content.length() - 1);
        }

        // 3. 使用商户应用私钥对参数字符串进行签名
        PrivateKey priKey = getPrivateKeyFromPEM(privateKey);
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(priKey);
        signature.update(content.getBytes(charset));
        byte[] signed = signature.sign();
        String signValue = new String(Base64.getEncoder().encode(signed), charset);

        // 4. 比对签名值
        return signValue.equals(sign);
    }

    private static PrivateKey getPrivateKeyFromPEM(String privateKey) throws Exception {
        privateKey = privateKey.replace("-----BEGIN PRIVATE KEY-----", "");
        privateKey = privateKey.replace("-----END PRIVATE KEY-----", "");
        privateKey = privateKey.replaceAll("\\s+", "");
        byte[] keyBytes = Base64.getDecoder().decode(privateKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

}
