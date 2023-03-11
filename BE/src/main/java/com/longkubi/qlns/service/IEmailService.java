package com.longkubi.qlns.service;

public interface IEmailService {
    public void sendEmail(String to, String subject, String body);
}
