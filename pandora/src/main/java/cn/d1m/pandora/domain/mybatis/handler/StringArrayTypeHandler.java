package cn.d1m.pandora.domain.mybatis.handler;

/**
 * Created by jone.wang on 2018/8/10.
 * Description:
 */
public class StringArrayTypeHandler extends StringTokenizerTypeHandler<String> {

    public StringArrayTypeHandler() {
        super(String.class);
    }

    @Override
    String parseString(String value) {
        return value;
    }

}
