package cn.d1m.pandora.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.d1m.pandora.dao.RuleMapper;
import cn.d1m.pandora.entry.ConsumerCard;
import cn.d1m.pandora.entry.Rule;
import cn.d1m.pandora.entry.output.CommonResponse;
import cn.d1m.pandora.service.RuleService;
import cn.d1m.pandora.utils.CommonConstants;
@Service("ruleService")
public class RuleServiceImpl implements RuleService{

	@Autowired
	private RuleMapper ruleMapper;
	
	Logger log = LoggerFactory.getLogger(RuleServiceImpl.class);
	@Override
	public CommonResponse insertCardRule(Rule cardRule) {
		ruleMapper.insertRule(cardRule);
		return  new CommonResponse(CommonConstants.HTTP_STATUS_CODE_SUCCESS,cardRule.getId(),"");
	}

	@Override
	public CommonResponse updateCardRule(Rule cardRule) {
		try {
			int count = ruleMapper.updateRule(cardRule);
			if(count > 0 ) {
				return new CommonResponse(CommonConstants.HTTP_STATUS_CODE_SUCCESS,"");
			}else {
				return new CommonResponse(CommonConstants.HTTP_STATUS_CODE_FAIL,"");
			}
		}catch(Exception e){
			log.error("更新异常",e);
			return new CommonResponse(CommonConstants.HTTP_STATUS_CODE_FAIL,"system err!");
		}
		
		
	}
	@Override
	public CommonResponse updateCardRuleDelete(Rule cardRule) {
		int used = ruleMapper.selectRuleSendById(cardRule.getId());
		if(used>0) {
			return new CommonResponse(CommonConstants.HTTP_STATUS_CODE_FAIL,"Rule con’t delete");
		}
		cardRule.setIsdelete("1");
		int count = ruleMapper.updateRuleDelete(cardRule);
		if(count > 0 ) {
			return new CommonResponse(CommonConstants.HTTP_STATUS_CODE_SUCCESS,"");
		}else {
			return new CommonResponse(CommonConstants.HTTP_STATUS_CODE_FAIL,"");
		}
	}
	@Override
	public CommonResponse updateCardRuleStatus(Rule cardRule) {
		int count = ruleMapper.updateRuleStatus(cardRule);
		if(count > 0 ) {
			return new CommonResponse(CommonConstants.HTTP_STATUS_CODE_SUCCESS,"");
		}else {
			return new CommonResponse(CommonConstants.HTTP_STATUS_CODE_FAIL,"");
		}
	}
	@Override
	public CommonResponse selectRuleById(String  id) {
		Rule rule = ruleMapper.selectRuleById(id);
			return new CommonResponse(CommonConstants.HTTP_STATUS_CODE_SUCCESS,rule);
	}
	@Override
	public CommonResponse selectRuleByCode(String  code) {
		Rule rule = ruleMapper.selectRuleByCode(code);
		return new CommonResponse(CommonConstants.HTTP_STATUS_CODE_SUCCESS,rule);
	}
	@Override
	public CommonResponse selectRuleLikeByCode(String  code) {
		List<Rule> rules = ruleMapper.selectRuleLikeByCode(code);
		return new CommonResponse(CommonConstants.HTTP_STATUS_CODE_SUCCESS,rules);
	}

	@Override
	public List<Rule> selectList(Rule cardRule) {
		return ruleMapper.selectRuleList(cardRule);
//		return new CommonResponse(CommonConstants.HTTP_STATUS_CODE_SUCCESS,rule);
	}

	@Override
	public List<Rule> selectRegistRuleList() {
		return  ruleMapper.selectRegistRuleList();
	}
}
