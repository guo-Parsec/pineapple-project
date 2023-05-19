package org.pineapple.common.access.generator;

import org.pineapple.common.access.interfaces.IdGenerator;
import org.pineapple.common.base.utils.Snowflake;

/**
 * @author gcq
 * @date 2023/5/19 11:25
 */
public class SnowflakeGenerator implements IdGenerator<Long> {
    private final Snowflake snowflake;

    public SnowflakeGenerator(Snowflake snowflake) {
        this.snowflake = snowflake;
    }

    /**
     * 获取下一个id
     *
     * @return K 下一个id
     * @author gcq
     * @date 2023/5/19 11:28
     */
    @Override
    public Long nextId() {
        return snowflake.nextId();
    }
}
