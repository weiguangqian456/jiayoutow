package com.edawtech.jiayou.config.bean

/**
 * ClassName:      OrderRefurlBean
 *
 *
 * Author:
 *
 *
 * CreateDate:      2020/9/18 16:06
 *
 *
 * Description:
 */
class OrderRefurlBean {
    var data: OrderRefurlBeanData? = null
    var code = 0
    var msg: String? = null

    class OrderRefurlBeanData {
        var orderList: List<OrderRefurlOrderList>? = null
        var total = 0
        var pages = 0
        var size = 0
        var amountPaySum: String? = null
        var litreSum: String? = null
    }

    class OrderRefurlOrderList {
        var id: String? = null
        var orderId: String? = null
        var userId: String? = null
        var username: String? = null
        var agentName: String? = null
        var gasStationId: String? = null
        var paySn: String? = null
        var phone: String? = null
        var orderTime: String? = null
        var payTime: String? = null
        var gasName: String? = null
        var province: String? = null
        var city: String? = null
        var county: String? = null
        var gunNo: String? = null
        var oilNo: String? = null
        var amountPay: String? = null
        var amountGas: String? = null
        var amountGun: String? = null
        var amountDiscounts: String? = null
        var orderStatusName: String? = null
        var couponMoney: String? = null
        var couponCode: String? = null
        var couponId: String? = null
        var litre: String? = null
        var payType: String? = null
        var payChannel: String? = null
        var priceUnit: String? = null
        var priceOfficial: String? = null
        var priceGun: String? = null
        var priceGas: String? = null
        var orderSource: String? = null
        var duduPhone: String? = null
        var qrCode4PetroChina: String? = null
        var source: String? = null
        var paymentType: String? = null
        var orderState: String? = null
        var gasUserId: String? = null
        var gasUsername: String? = null
        var gasAgentName: String? = null
        var preDepositState: String? = null
        var totalAmount: String? = null
    }
}