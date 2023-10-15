package com.norm.timemall.app.base.util.zoho;

import com.google.gson.Gson;
import com.norm.timemall.app.base.pojo.ZohoEmailAddress;
import com.norm.timemall.app.base.pojo.ZohoEmailInputContent;
import com.norm.timemall.app.base.pojo.ZohoFrom;
import com.norm.timemall.app.base.pojo.ZohoToEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
@Component
public class ZohoEmailApi {
    @Autowired
    private ZohoTransactionalEmailResource resource;

    public void sendNoreplyEmail(String to,String subject,String htmlbody){

        sendTransactionalEmail(resource.getNoReplayAccount(),to,subject,htmlbody);
    }
    private void sendTransactionalEmail(String from,String to,String subject,String htmlbody){

        ZohoEmailInputContent content=new ZohoEmailInputContent();

        ZohoFrom fromField=new ZohoFrom();
        fromField.setAddress(from);
        fromField.setName(resource.getAccountName());
        content.setFrom(fromField);

        content.setSubject(subject);
        content.setHtmlbody(htmlbody);

        ZohoEmailAddress address =new ZohoEmailAddress();
        address.setAddress(to);
        ZohoToEmail toEmail=new ZohoToEmail();
        ArrayList<ZohoToEmail> toEmails=new ArrayList<>();
        toEmails.add(toEmail);
        toEmail.setEmail_address(address);
        content.setTo(toEmails);

        doHttpConnectionUsePost(content);

    }

    private void doHttpConnectionUsePost(ZohoEmailInputContent content){
        BufferedReader br = null;
        HttpURLConnection conn = null;
        String output = null;
        StringBuffer sb = new StringBuffer();
        try {
            URL url = new URL(resource.getPostUrl());
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Authorization", resource.getAuthorization());

            Gson gson=new Gson();
            OutputStream os = conn.getOutputStream();
            os.write(gson.toJson(content).getBytes());
            os.flush();
            br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            while ((output = br.readLine()) != null) {
                sb.append(output);
            }
            System.out.println(sb.toString());
        } catch (Exception e) {
            br = new BufferedReader(new InputStreamReader((conn.getErrorStream())));
            while (true) {
                try {
                    if (!((output = br.readLine()) != null)) break;
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                sb.append(output);
            }
            System.out.println(sb.toString());
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();

            }
        }

    }
}


