import httpClient from "../http-common";

const getAll = () => {
    return httpClient.get('/discounts');
}

const applyCoupon = (repairId, brand) => {
    return httpClient.post(`/discounts/${repairId}/apply-coupon`, null, {
        params: { brand }
    });
}

const getCouponQuantity = (brand) => {
    return httpClient.get('/discounts/quantity', {
        params: { brand }
    });
}

export default { getAll, applyCoupon, getCouponQuantity };