package com.yuzhi.fine.founder;

import java.util.ArrayList;

/**
 * 股票相关接口
 * 自选股票存储结构：market-type-code,market-type-code...
 */
public interface FZStockInterface {
    /**
     * 添加自选股
     * @param codeInfo 股票信息
     * @return 成功true、失败false
     */
    boolean addMyStock(String codeInfo);

    /**
     * 删除自选股
     * @param codeInfo 股票信息
     * @return 成功true、失败false
     */
    boolean deleteMyStock(String codeInfo);

    /**
     * 删除多个自选股
     * @param list 待删除的多个股票信息
     * @return 成功true、失败false
     */
    boolean deleteMyStocks(ArrayList<String> list);

    /**
     * 获取所有自选股
     * @return 自选股列表
     */
    ArrayList<String> getMyStockList();
}
