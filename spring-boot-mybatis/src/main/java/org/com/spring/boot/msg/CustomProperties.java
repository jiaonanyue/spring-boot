package org.com.spring.boot.msg;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * <ul>
 * <li>文件包名 : com.acjt.aycx.cache</li>
 * <li>创建时间 : 2016/11/8 17:47</li>
 * <li>修改记录 : 无</li>
 * </ul>
 * 类说明：
 *
 * @author chenlin
 * @version 2.0.0
 */
@Component
public class CustomProperties {

    @Value("#{bzcode}")
    private Properties bzcodeSettings;

    @Value("#{bzmsg}")
    private Properties bzmsgSettings;

    public  Properties getBzcodeSettings() {
        return bzcodeSettings;
    }

    public Properties getBzmsgSettings() {
        return bzmsgSettings;
    }
}
