package org.pineapple.common.base.utils;

import org.pineapple.common.base.fault.BizFaultHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p></p>
 *
 * @author pinea
 * @version 1.0
 * @project pineapple-project
 * @date 2023/4/17
 */
public class Snowflake {
    private static final Logger log = LoggerFactory.getLogger(Snowflake.class);

    private static volatile Snowflake instance;

    // 起始的时间戳
    private final static long START_TIMESTAMP = 1480166465631L;

    // 每一部分占用的位数
    private final static long SEQUENCE_BIT = 12; // 序列号占用的位数
    private final static long MACHINE_BIT = 5;   // 机器标识占用的位数
    private final static long DATACENTER_BIT = 5;// 数据中心占用的位数

    // 每一部分的最大值
    private final static long MAX_DATACENTER_NUM = ~(-1L << DATACENTER_BIT);
    private final static long MAX_MACHINE_NUM = ~(-1L << MACHINE_BIT);
    private final static long MAX_SEQUENCE = ~(-1L << SEQUENCE_BIT);

    // 每一部分向左的位移
    private final static long MACHINE_LEFT = SEQUENCE_BIT;
    private final static long DATACENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    private final static long TIMESTAMP_LEFT = DATACENTER_LEFT + DATACENTER_BIT;

    /**
     * 数据中心
     */
    private final long datacenterId;

    /**
     * 机器标识
     */
    private final long machineId;

    /**
     * 序列号
     */
    private long sequence = 0L;

    /**
     * 上一次时间戳
     */
    private long lastTimestamp = -1L;

    private Snowflake(long datacenterId, long machineId) {
        if (datacenterId > MAX_DATACENTER_NUM || datacenterId < 0) {
            throw BizFaultHelper.record(log, "datacenterId[{}] can't be greater than [{}] or less than 0", datacenterId, MAX_DATACENTER_NUM);
        }
        if (machineId > MAX_MACHINE_NUM || machineId < 0) {
            throw BizFaultHelper.record(log, "machineId[{}] can't be greater than [{}] or less than 0", machineId, MAX_MACHINE_NUM);
        }
        this.datacenterId = datacenterId;
        this.machineId = machineId;
    }

    public static Snowflake getInstance(long datacenterId, long machineId) {
        if (instance == null) {
            synchronized (Snowflake.class) {
                if (instance == null) {
                    instance = new Snowflake(datacenterId, machineId);
                }
            }
        }
        return instance;
    }

    /**
     * <p>生成id</p>
     *
     * @return long
     * @author pinea
     * @date 2023/4/17 19:39:02
     */
    public synchronized long nextId() {
        long timestamp = timeGen();

        // 如果当前时间小于上一次时间戳，则说明系统时钟回退过，不能生成ID
        if (timestamp < lastTimestamp) {
            throw BizFaultHelper.record(log, "Clock moved backwards. Refusing to generate id");
        }

        // 如果是同一时间生成的，则进行序列号累加
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & MAX_SEQUENCE;
            // 序列号已经达到最大值，需要等待下一个时间戳
            if (sequence == 0L) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            // 时间戳改变，重置序列号
            sequence = 0L;
        }

        // 保存上次生成ID的时间戳
        lastTimestamp = timestamp;

        // 返回ID
        return ((timestamp - START_TIMESTAMP) << TIMESTAMP_LEFT)
                | (datacenterId << DATACENTER_LEFT)
                | (machineId << MACHINE_LEFT)
                | sequence;
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }
}

