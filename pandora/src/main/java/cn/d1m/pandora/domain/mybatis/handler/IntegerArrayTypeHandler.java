package cn.d1m.pandora.domain.mybatis.handler;

/**
 * Created by jone.wang on 2018/8/10.
 * Description:
 */
public class IntegerArrayTypeHandler extends StringTokenizerTypeHandler<Integer> {

    public IntegerArrayTypeHandler() {
        super(Integer.class);
    }

    @Override
    Integer parseString(String value) {
        return Integer.valueOf(value);
    }

}
