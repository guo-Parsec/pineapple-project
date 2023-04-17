package org.pineapple.common.base.fault;

import org.pineapple.common.base.response.BizResponseStatus;

/**
 * <p>不支持的操作错误类</p>
 *
 * @author pinea
 * @version 1.0
 * @project pineapple-project
 * @date 2023/4/17
 */
public class UnsupportedOperationFault extends BizFault {
    private static final long serialVersionUID = -5772361662225972026L;

    public UnsupportedOperationFault() {
        super(BizResponseStatus.UNSUPPORTED_OPERATION_ERROR);
    }

    public static void main(String[] args) {
//        Logger logger = LoggerFactory.getLogger(Errors.class);
//        Scanner scanner = new Scanner(System.in);
//        String word = null;
//        while (true) {
//            System.out.println("please input your words:");
//            word = scanner.next();
//            if (word != null && word.equals("break")) {
//                throw BizFaultHelper.record(logger, "your words is [{}], application will break!", word);
//            }
//            logger.debug("your words is [{}], application will go on!", word);
//        }
    }
}
