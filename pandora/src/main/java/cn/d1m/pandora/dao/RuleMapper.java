package cn.d1m.pandora.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.d1m.pandora.entry.Rule;
@Repository("ruleMapper")
public interface RuleMapper {
	public void insertRule(Rule cardRule);
	public int updateRule(Rule cardRule);
	public int updateRuleStatus(Rule cardRule);
	public int updateRuleDelete(Rule cardRule);
	public Rule selectRuleById(String id);
	public Rule selectRuleByCode(String code);
	public List<Rule> selectRuleLikeByCode(String code);
	public int selectRuleSendById(String id);
	public List<Rule>  selectRuleList(Rule cardRule);
	public List<Rule> selectRegistRuleList();
	public int getQuality(Rule rule);
}
