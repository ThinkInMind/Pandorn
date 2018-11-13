package cn.d1m.pandora.service;

import java.util.List;

import cn.d1m.pandora.entry.ConsumerCard;
import cn.d1m.pandora.entry.Rule;
import cn.d1m.pandora.entry.output.CommonResponse;

public interface RuleService {

	CommonResponse insertCardRule(Rule cardRule);

	CommonResponse updateCardRule(Rule cardRule);

	CommonResponse updateCardRuleDelete(Rule cardRule);

	CommonResponse updateCardRuleStatus(Rule cardRule);

	CommonResponse selectRuleById(String id);

	CommonResponse selectRuleByCode(String code);
	CommonResponse selectRuleLikeByCode(String code);
	List<Rule>  selectList(Rule cardRule);

	List<Rule> selectRegistRuleList();

	
}
