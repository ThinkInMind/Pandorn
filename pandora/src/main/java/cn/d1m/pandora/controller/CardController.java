package cn.d1m.pandora.controller;

import cn.d1m.pandora.domain.dto.CardDto;
import cn.d1m.pandora.domain.entity.Card;
import cn.d1m.pandora.domain.enumeration.CardNature;
import cn.d1m.pandora.domain.enumeration.DiscountType;
import cn.d1m.pandora.domain.enumeration.ValidityPeriodType;
import cn.d1m.pandora.domain.web.BaseResponse;
import cn.d1m.pandora.entry.Rule;
import cn.d1m.pandora.service.CardTKService;
import cn.d1m.pandora.utils.UuidUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by jone.wang on 2018/10/23.
 * Description:
 */
@Api(value = "礼券种类操作")
@RestController
@RequestMapping(path = "/api/card/")
public class CardController {


    @Autowired
    private CardTKService cardTKService;

    @PostMapping
    @ApiOperation(value = "新增优惠券种类")
    public BaseResponse insert(@Validated InsertCardRuleReq cardRule) {

        final Card card = new Card();
        BeanUtils.copyProperties(cardRule, card);
//        card.setId(UuidUtil.shortUUID());
       cardTKService.addCardAndLevels(card, cardRule.getLevelIds());

        return BaseResponse.buildSuccessfully("操作成功");
    }

    @SuppressWarnings("NullableProblems")
    @Data
    private static class InsertCardRuleReq {
        @NotNull(message = "礼券属性不能为空")
        @ApiModelProperty(value = "礼券性质", required = true)
        private CardNature nature;
        @ApiModelProperty(value = "应用规则id")
        private String ruleId;
        @ApiModelProperty(value = "张数", required = true)
        private Integer quantity;
        @ApiModelProperty(value = "礼券名称", required = true)
        private String name;
        @ApiModelProperty(value = "礼券标题", required = true)
        private String title;
        @ApiModelProperty(value = "优惠形式", required = true)
        private DiscountType discountType;
        @ApiModelProperty(value = "最低消费金额")
        private BigDecimal minimumCharge;
        @ApiModelProperty(value = "现金优惠力度")
        private BigDecimal cashDiscounts;
        @ApiModelProperty(value = "折扣百分比")
        private Integer discountRate;
        @ApiModelProperty(value = "礼券应用时间区间")
        private ValidityPeriodType validityPeriodType;
        @JsonFormat(pattern = "yyyy-MM-dd")
        private Timestamp beginTime;
        @JsonFormat(pattern = "yyyy-MM-dd")
        private Timestamp endTime;
        @ApiModelProperty(value = "发放后多少天开始计算")
        private Integer beginDayAfterSend;
        @ApiModelProperty(value = "持续天数")
        private Integer durationDay;
        @ApiModelProperty(value = "库存", notes = "默认最大integer值")
        private Integer stock = Integer.MAX_VALUE;
        @ApiModelProperty(value = "应用的会员等级")
        private List<Integer> levelIds;
        @ApiModelProperty(value = "到期前提醒")
        private Integer reminderBeforeExpiration;
        @ApiModelProperty(value = "礼券描述")
        private String description;
    }

    @PatchMapping("/{id}")
    @ApiOperation(value = "更新礼券库存")
    public BaseResponse stockUpdate(@PathVariable String id, @RequestBody @Validated StockUpdateReq req) {
        final Card card = Card.builder()
                .id(id)
                .stock(req.getStock())
                .version(req.getVersion())
                .build();
        cardTKService.updateWithVersion(card);
        return BaseResponse.buildSuccessfully();
    }

    @Data
    private static class StockUpdateReq {
        @NotNull(message = "库存不能为空")
        @ApiModelProperty(value = "库存数量", required = true, example = "20")
        private Integer stock;
        @NotNull(message = "版本号不能为空")
        @ApiModelProperty(value = "版本号", required = true, example = "1")
        private Integer version;
    }

    //根据ID删除礼券
    @DeleteMapping("{id}"  )
    @ApiOperation(value = "根据ID删除礼券")
    public BaseResponse stockDelete(@PathVariable String id,
                                    @ApiParam(value = "版本号", required = true, example = "1")
                                    @RequestParam Integer version
                                     ) {
        final Card card = Card.builder()
                .id(id)
                .version(version)
                .build();
        cardTKService.deleteStockById(card);
        return BaseResponse.buildSuccessfully();
    }


    //查询card信息
    @GetMapping("list")
    @ApiOperation(value = "查询所有的礼券")
    public BaseResponse<PageInfo<CardDto>> selectListCard(@RequestParam(required = false) String ruleId,
                                                          @ApiParam(value = "开始时间", example = "2018-10-20", name = "beginTimestamp")
                                                          @RequestParam(required = false, name = "beginTime") @DateTimeFormat(pattern = "yyyy-MM-dd") Timestamp beginTime,
                                                          @ApiParam(value = "结束时间", example = "2018-10-28", name = "endTimestamp")
                                                          @RequestParam(required = false, name = "endTime") @DateTimeFormat(pattern = "yyyy-MM-dd") Timestamp endTime,
                                                          @RequestParam(required = false) String description,
                                                          @RequestParam(required = false) String typeCode,
                                                          @RequestParam(required = false) String name,
                                                          @RequestParam(required = false) String title,
                                                          @RequestParam(required = false) Integer stock,
                                                          @RequestParam(required = false) Integer version,
                                                          @RequestParam(required = false) BigDecimal cashDiscounts,
                                                          @RequestParam(required = false) CardNature nature,
                                                          @RequestParam(required = false) DiscountType discountType,
                                                          @RequestParam(required = false) Integer reminderBeforeExpiration,
                                                          @RequestParam(required = false) Integer quantity,
                                                          @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                                          @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        final Card card = new Card();
        card.setRuleId(ruleId);
        card.setName(name);
        card.setBeginTime(beginTime);
        card.setEndTime(endTime);
        card.setDescription(description);
        card.setTypeCode(typeCode);
        card.setTitle(title);
        card.setStock(stock);
        card.setVersion(version);
        card.setCashDiscounts(cashDiscounts);
        card.setNature(nature);
        card.setDiscountType(discountType);
        card.setReminderBeforeExpiration(reminderBeforeExpiration);
        card.setQuantity(quantity);
        PageHelper.startPage(pageNum, pageSize);
        final List<CardDto> cards = cardTKService.selectListCard(card);

        final PageInfo<CardDto> rulePageInfo = new PageInfo<>(cards);
        return BaseResponse.build(true, 200,null,"查询成功",null,rulePageInfo);
    }


}
