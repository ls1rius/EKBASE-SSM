package com.lh.ekbase.controller;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ControllerWithSession{
    @Autowired
    public HttpServletRequest request;
    public void putToSession(String key, Object value) {
        HttpSession session = request.getSession();
        session.setAttribute(key, value);
    }
    public Object getFromSession(String key) {
        HttpSession session = request.getSession();
        return session.getAttribute(key);
    }
}
