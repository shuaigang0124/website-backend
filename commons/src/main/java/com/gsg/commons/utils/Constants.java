package com.gsg.commons.utils;

/**
 * 常量接口
 *  存放公共常使用的常量
 */
public interface Constants {

    /*常量*/
    /**
     * 语言ID
     */

    String TYPE_0 = "0";

    /** 临时目录 */
    String TEMP = "temp";

    /** 正式目录 */
    String FORMAL = "formal";

    /** 路径前缀 */
    String PREFIX = "/gsg";

    String AVATAR_PATH = "avatarPath";

    /** 空白符 */
    String BLANK = "";

    /** 父级ID */
    String P_ID = "pId";

    /** 等级 */
    String LEVEL = "level";

    /**
     * @Description 是否失效
     **/
    String IS_EXPIRED = "isExpired";

    /** 校验返回结果码 */
    String UNIQUE = "0";
    String NOT_UNIQUE = "1";

    /** 标志符 */
    String FLAG = "Y";

    /** 分隔符1 */
    String DELIMITER_1 = "~";

    /** 分隔符2 */
    String DELIMITER_2 = ",";

    /** 分隔符3 */
    String DELIMITER_3 = "##_##";

    /**
     * http请求
     */
    String HTTP = "http://";

    /**
     * https请求
     */
    String HTTPS = "https://";

    /** 点赞操作URL */
    String USER_KUDOS_URL = "http://shuaigang-server/v1/userKudos/confirmAndCancelKudos?userId={1}&serviceId={2}&clickNum={3}";

    /** 发送中奖信息到邮箱 */
    String PRIZE_SEND_EMAIL = "http://customer-server/v1/email/sendMailPrizeInfo?email={1}&prizeName={2}";

    /** 服务名 */
    String SERVER_NAME = "serverName";

    /**
     * 总条数
     */
    String TOTAL_COUNT = "totalCount";

    /**
     * 当前页数
     */
    String CURRENT_PAGE = "currentPage";

    /**
     * 当前页条数
     */
    String PAGE_COUNT = "pageCount";

    /**
     * 分页集合
     */
    String PAGINATION_LIST = "paginationList";

    /**
     * 是否锁定 枚举
     * @author gaoshenggang
     * @date 2021/8/15 11:13
     * @param
     * @return
     */
    enum IsEnabledEnum {
        IS_ENABLED_N(0, "已锁定"),
        IS_ENABLED_Y(1, "未锁定");

        private Integer key;

        private String desc;

        IsEnabledEnum(Integer key, String desc) {
            this.key = key;
            this.desc = desc;
        }

        public Integer getKey() {
            return key;
        }

        public String getDesc() {
            return desc;
        }
    }


}
