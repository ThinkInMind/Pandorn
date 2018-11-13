package cn.d1m.pandora.controller;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.d1m.pandora.entry.output.CommonResponse;
import cn.d1m.pandora.service.VerifyCodeService;
import cn.d1m.pandora.utils.CommonConstants;


@RestController
@RequestMapping(path="/api/verifyCode/")
public class VerifyCodeController {
	@Autowired
	private VerifyCodeService verifyCodeService;
	
	@RequestMapping(path="/send", method=RequestMethod.POST )
	public CommonResponse send(@Valid @NotBlank(message="mobile should be not null") @RequestParam String mobilePhone) throws Exception{
		
		
		
		boolean rlt = verifyCodeService.sendNewVerifyCode(mobilePhone);
		if(rlt) {
			return new CommonResponse(CommonConstants.HTTP_STATUS_CODE_SUCCESS, null,null);//返回结果
		}else {
			return new CommonResponse(CommonConstants.HTTP_STATUS_CODE_FAIL, null,null);//返回结果
		}
	}
}
