package com.woyao.wx.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.persistence.oxm.annotations.XmlCDATA;

@XmlRootElement(name = "xml")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class OrderNotifyResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@XmlCDATA	
	@XmlElement(name = "return_code", required = true)
	private String returnCode;
	
	@XmlCDATA	
	@XmlElement(name = "return_msg")
	private String returnMsg;
	
	@XmlCDATA	
	@XmlElement(name = "result_code",required = true)
	private String result_code;//业务结果
	

}